package com.liferay.frontend.packages.bundle.config.extender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.log.Logger;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.frontend.packages.definitions.Builtin;
import com.liferay.frontend.packages.definitions.ModuleAlias;
import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StreamUtil;

@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=SystemJS Config Servlet",
		"osgi.http.whiteboard.servlet.pattern=/system_js_config",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {SystemJSConfigServlet.class, Servlet.class}
)
public class SystemJSConfigServlet extends HttpServlet {

	private static final long serialVersionUID = -3461930018668829152L;

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_logger = new Logger(componentContext.getBundleContext());
	}

	protected String clearExtension(String path) {
		if (path.endsWith(".js")) {
			return path.substring(0, path.length() - 3);
		}
		else {
			return path;
		}
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		servletOutputStream.println("System.config(");

		JSONObject cfg = JSONFactoryUtil.createJSONObject();

		JSONObject packages = JSONFactoryUtil.createJSONObject();
		JSONObject map = JSONFactoryUtil.createJSONObject();
		JSONObject paths = JSONFactoryUtil.createJSONObject();

		for (PackageConfig pkgConfig : _packageRegistry.getPackageConfigs()) {
			JSONObject pkg = JSONFactoryUtil.createJSONObject();
			JSONObject pkgMap = JSONFactoryUtil.createJSONObject();

			for (PackageDependency dependency : pkgConfig.getDependencies()) {
				PackageConfig resolved = _packageRegistry.resolve(dependency);

				if (resolved != null) {
					pkgMap.put(dependency.getName(), resolved.getPath());
				}
			}

			for (ModuleAlias moduleAlias : pkgConfig.getModuleAliases()) {
				String aliasedName = moduleAlias.getAliasedName();
				String sourceName = moduleAlias.getSourceName();

				aliasedName = clearExtension(aliasedName);
				sourceName = clearExtension(sourceName);

				pkgMap.put(aliasedName, sourceName);
			}

			pkg.put("main", pkgConfig.getMain());

			// TODO
			pkg.put("format", "cjs");

			pkg.put("map", pkgMap);

			map.put(pkgConfig.getIdentifier(), pkgConfig.getPath());
			paths.put(
				pkgConfig.getIdentifier() + "*",
				pkgConfig.getPath() + "*");

			packages.put(pkgConfig.getIdentifier(), pkg);
		}

		for (Builtin builtin : _packageRegistry.getBuiltins()) {
			PackageConfig resolved = _packageRegistry.resolve(
				builtin.getDependency());

			if (resolved != null) {
				paths.put(
					builtin.getName() + "*", resolved.getPath() + "*");
			}
			else {
				_logger.log(
					Logger.LOG_ERROR,
					"Cannot find builtin implementation for " +
						builtin.getName());
			}
		}

		cfg.put("packages", packages);
		/*cfg.put("map", map);*/
		cfg.put("paths", paths);

		try {
			servletOutputStream.println(cfg.toString(2));
		}
		catch (JSONException jsone) {
			_logger.log(Logger.LOG_ERROR, "Cannot write config", jsone);
		}

		servletOutputStream.println(");");
	}

	@Reference(unbind = "-")
	protected void setPackageRegistry(PackageRegistry packageRegistry) {
		_packageRegistry = packageRegistry;
	}

	@Reference(unbind = "-")
	protected void setPackagesBundleConfigTracker(
		PackagesBundleConfigTracker packagesBundleConfigTracker) {

		_packagesBundleConfigTracker = packagesBundleConfigTracker;
	}

	private void _write(
			ServletContext servletContext, String path,
			OutputStream outputStream, boolean cleanup)
		throws IOException {

		InputStream inputStream = servletContext.getResourceAsStream(path);

		StreamUtil.transfer(inputStream, outputStream, cleanup);
	}

	private PackageRegistry _packageRegistry;
	private PackagesBundleConfigTracker _packagesBundleConfigTracker;
	private Logger _logger;

}
