package com.liferay.frontend.packages.definitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.github.zafarkhaja.semver.Version;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

public class PackageConfig {

	public PackageConfig(
		String name, PackageIdentifier pkgIdentifier, String version,
		String main, List<PackageDependency> dependencies,
		List<ModuleAlias> moduleAliases, String servletPackagePath,
		PackagesBundleConfig pkgsBundleConfig) {

		_name = name;
		_version = Version.valueOf(version);
		_main = main;
		_moduleAliases = moduleAliases;
		_dependencies = dependencies;
		_pkgIdentifier = pkgIdentifier;
		_pkgsBundleConfig = pkgsBundleConfig;
		_servletPackagePath = servletPackagePath;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof PackageConfig)) {
			return false;
		}

		PackageConfig other = (PackageConfig)obj;

		if (StringUtil.equalsIgnoreCase(getName(), other.getName()) &&
			getVersion().equals(other.getVersion())) {

			return true;
		}

		return false;
	}

	public List<PackageDependency> getDependencies() {
		return _dependencies;
	}

	public String getIdentifier() {
		StringBundler sb = new StringBundler();

		sb.append(getName());
		sb.append(StringPool.SLASH);
		sb.append(getVersion());

		return sb.toString();
	}

	public String getMain() {
		if (Validator.isNull(_main)) {
			return "index.js";
		}
		else {
			return _main;
		}
	}

	public List<ModuleAlias> getModuleAliases() {
		return _moduleAliases;
	}

	public String getName() {
		return _name;
	}

	public PackageIdentifier getPackageIdentifier() {
		return _pkgIdentifier;
	}

	public String getPath() {
		/*StringBundler sb = new StringBundler();

		sb.append(PortalUtil.getPathContext());
		sb.append(getServletContextPath());
		sb.append(_servletPackagePath);

		return sb.toString();*/
		StringBundler sb = new StringBundler();

		sb.append(PortalUtil.getPathContext());
		sb.append("/o/pkg/");
		sb.append(getIdentifier());
		sb.append("/~");

		return sb.toString();
	}

	public URL getResource(String location) throws MalformedURLException {
		return _pkgsBundleConfig.getResource(_servletPackagePath + location);
	}

	public String getServletContextPath() {
		return _pkgsBundleConfig.getServletContextPath();
	}

	public Version getVersion() {
		return _version;
	}

	private String _servletPackagePath;
	private PackageIdentifier _pkgIdentifier;
	private List<PackageDependency> _dependencies;
	private String _name;
	private String _main;
	private List<ModuleAlias> _moduleAliases;
	private PackagesBundleConfig _pkgsBundleConfig;
	private Version _version;

}