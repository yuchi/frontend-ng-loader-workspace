package com.liferay.frontend.packages.definitions.npm;

import com.github.yuchi.semver.Range;
import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.frontend.packages.definitions.PackageIdentifier;

public class NPMPackageDependency implements PackageDependency {

	public NPMPackageDependency(String name, String value) {
		_range = Range.from(value, true);
		_pkgIdentifier = new NPMPackageIdentifier(name);
		_name = name;
		_value = value;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public PackageIdentifier getPackageIdentifier() {
		return _pkgIdentifier;
	}

	@Override
	public boolean matches(PackageConfig pkgConfig) {
		if (matches(pkgConfig.getPackageIdentifier()) &&
			_range.test(pkgConfig.getVersion())) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean matches(PackageIdentifier pkgIdentifier) {
		return pkgIdentifier.equals(getPackageIdentifier());
	}

	@Override
	public String toString() {
		return _name + " " + _value;
	}

	private String _name = null;
	private String _value = null;
	private Range _range = null;
	private PackageIdentifier _pkgIdentifier = null;

}
