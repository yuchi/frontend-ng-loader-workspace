package com.liferay.frontend.packages.definitions;

public class Builtin {

	public Builtin(String name, PackageDependency dependency, String path) {
		_dependency = dependency;
		_name = name;
		_path = path;
	}

	public PackageDependency getDependency() {
		return _dependency;
	}

	public String getName() {
		return _name;
	}

	public String getPath() {
		return _path;
	}

	private PackageDependency _dependency;
	private String _name;
	private String _path;

}
