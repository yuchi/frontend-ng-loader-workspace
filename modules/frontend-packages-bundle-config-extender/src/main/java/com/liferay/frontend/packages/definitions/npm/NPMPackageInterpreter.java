package com.liferay.frontend.packages.definitions.npm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.felix.utils.log.Logger;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.github.zafarkhaja.semver.ParseException;
import com.liferay.frontend.packages.definitions.ModuleAlias;
import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.frontend.packages.definitions.PackageIdentifier;
import com.liferay.frontend.packages.definitions.PackageInterpreter;
import com.liferay.frontend.packages.definitions.PackagesBundleConfig;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, service = PackageInterpreter.class)
public class NPMPackageInterpreter implements PackageInterpreter {

	public static final String BROWSER = "browser";
	public static final String DEPENDENCIES = "dependencies";
	public static final String DEPENDENCIES_OPTIONAL = "optionalDependencies";
	public static final String DEPENDENCIES_PEER = "peerDependencies";
	public static final String NAME = "name";
	public static final String MAIN = "main";
	public static final String PACKAGES = "packages";
	public static final String TYPE = "npm";
	public static final String VERSION = "version";

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_logger = new Logger(componentContext.getBundleContext());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public Optional<PackagesBundleConfig> interpret(
		ServletContext servletContext, String location) {

		return Optional.ofNullable(doInterpret(servletContext, location));
	}

	protected PackagesBundleConfig doInterpret(
		ServletContext servletContext, String location) {

		try {
			PackagesBundleConfig pkgsBundleConfig = new PackagesBundleConfig(
				servletContext);

			parsePackage(servletContext, pkgsBundleConfig, location);

			return pkgsBundleConfig;
		}
		catch (InvalidPackageException ipe) {
			return null;
		}
	}

	protected PackageConfig parsePackage(
			ServletContext servletContext,
			PackagesBundleConfig pkgsBundleConfig, String location)
		throws InvalidPackageException {

		if (!location.endsWith("/package.json")) {
			throw new InvalidPackageException();
		}

		JSONObject jsonObject = _read(servletContext, location);

		if (jsonObject == null) {
			return null;
		}

		String name = jsonObject.getString(NAME);
		String version = jsonObject.getString(VERSION);
		String main = jsonObject.getString(MAIN);

		PackageIdentifier pkgIdentifier = new NPMPackageIdentifier(name);

		List<PackageDependency> dependencies =
			new ArrayList<PackageDependency>();

		dependencies.addAll(parsePackageDependencies(
			pkgsBundleConfig, location, jsonObject, DEPENDENCIES));
		dependencies.addAll(parsePackageDependencies(
			pkgsBundleConfig, location, jsonObject, DEPENDENCIES_OPTIONAL));
		dependencies.addAll(parsePackageDependencies(
			pkgsBundleConfig, location, jsonObject, DEPENDENCIES_PEER));

		List<ModuleAlias> moduleAliases = new ArrayList<ModuleAlias>();

		JSONObject browserAliases = jsonObject.getJSONObject(BROWSER);

		if (browserAliases != null) {
			Iterator<String> itr = browserAliases.keys();

			while (itr.hasNext()) {
				String aliasedName = itr.next();
				Object source = browserAliases.get(aliasedName);

				_logger.log(Logger.LOG_DEBUG, "-- " + aliasedName + " >> " + source);

				if (source == null) {
					continue;
				}
				else if (source instanceof Boolean) {
					boolean value = GetterUtil.getBoolean(source);

					if (!value) {
						_logger.log(
							Logger.LOG_WARNING,
							"Skipped modules are not supported (skipped " +
								aliasedName + " in " + name + "@" + version +
									")");
					}
					else {
						continue;
					}
				}
				else if (source instanceof String) {
					String sourceName = (String)source;
					
					ModuleAlias moduleAlias = new ModuleAlias(
						sourceName, aliasedName);

					moduleAliases.add(moduleAlias);
				}
			}
		}

		String dirLocation = location.substring(0, location.length() - 13);

		String servletPackagePath = dirLocation;

		if (servletPackagePath.startsWith("/META-INF/resources")) {
			servletPackagePath = servletPackagePath.substring(19);
		}

		PackageConfig pkgConfig = new PackageConfig(
			name, pkgIdentifier, version, main, dependencies, moduleAliases,
			servletPackagePath, pkgsBundleConfig);

		pkgsBundleConfig.addPackageConfig(pkgConfig);

		for (PackageDependency dependency : dependencies) {
			PackageConfig found = null;

			while (true) {
				StringBundler sb = new StringBundler();

				sb.append(dirLocation);
				sb.append("/node_modules/");
				sb.append(dependency.getName());
				sb.append("/package.json");

				String dependencyLocation = sb.toString();

				found = parsePackage(
					servletContext, pkgsBundleConfig, dependencyLocation);

				if (found != null) {
					break;
				}

				int pos = dirLocation.lastIndexOf(CharPool.SLASH);

				if (pos < 0) {
					break;
				}

				dirLocation = dirLocation.substring(0, pos);

				if (Validator.isNull(dirLocation)) {
					break;
				}
			}
		}

		return pkgConfig;
	}

	protected List<PackageDependency> parsePackageDependencies(
		PackagesBundleConfig pkgsBundleConfig, String location,
		JSONObject jsonObject, String property) {

		List<PackageDependency> dependencies =
			new ArrayList<PackageDependency>();

		JSONObject deps = jsonObject.getJSONObject(property);

		if (deps == null) {
			return dependencies;
		}

		Iterator<String> itr = deps.keys();

		while (itr.hasNext()) {
			String name = itr.next();
			String value = deps.getString(name);

			try {
				dependencies.add(new NPMPackageDependency(name, value));
			}
			catch (ParseException pe) {
				_logger.log(Logger.LOG_ERROR, "Cannot parse dependency", pe);
			}
		}

		return dependencies;
	}

	private static JSONObject _read(
		ServletContext servletContext, String location) {

		try {
			URL url = servletContext.getResource(location);

			if (url == null) {
				return null;
			}

			String json = StringUtil.read(url.openStream());
	
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);
	
			return jsonObject;
		}
		catch (MalformedURLException murle) {
			return null;
		}
		catch (IOException ioe) {
			return null;
		}
		catch (JSONException jsone) {
			return null;
		}
	}

	private static class InvalidPackageException extends Exception {

		private static final long serialVersionUID = 123233863902880765L;

	}

	private Logger _logger;
}
