package com.liferay.frontend.packages.definitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.liferay.portal.kernel.json.JSONObject;

public class PackagesBundleConfig {

	public PackagesBundleConfig(ServletContext servletContext) {
		_pkgConfigs = new ArrayList<PackageConfig>();
		_servletContext = servletContext;
	}

	public void addPackageConfig(PackageConfig pkgConfig) {
		_pkgConfigs.add(pkgConfig);
	}

	public List<PackageConfig> getPackageConfigs() {
		return _pkgConfigs;
	}

	public URL getResource(String path) throws MalformedURLException {
		URL url = _servletContext.getResource(path);
		
		if (url != null) {
			return url;
		}
		else {
			return _servletContext.getResource(path + ".js");
		}
	}

	public String getServletContextPath() {
		return _servletContext.getContextPath();
	}

	private void _setup() {
		/*JSONArray packages = _jsonObject.getJSONArray(PACKAGES);

		_pkgConfigs = new ArrayList<PackageConfig>();

		if (packages == null) {
			return;
		}

		for (int i = 0; i < packages.length(); ++i) {
			JSONObject pkg = packages.getJSONObject(i);

			String name = pkg.getString(NAME);
			String version = pkg.getString(VERSION);
			List<PackageDependency> dependencies =
				new ArrayList<PackageDependency>();

			JSONObject pkgDeps = pkg.getJSONObject(DEPENDENCIES);

			if (pkgDeps != null) {
				Iterator<String> iterator = pkgDeps.keys();

				while (iterator.hasNext()) {
					String depName = iterator.next();
					String depValue = pkgDeps.getString(depName);

					PackageDependency dependency = new PackageDependency(
						depName, depValue);

					dependencies.add(dependency);
				}
			}

			PackageConfig pkgConfig = new PackageConfig(
				name, version, dependencies, this);

			_pkgConfigs.add(pkgConfig);
		}*/
	}

	private ServletContext _servletContext;
	private JSONObject _jsonObject;
	private List<PackageConfig> _pkgConfigs;
	//private Version _mainPackageVersion;

}
