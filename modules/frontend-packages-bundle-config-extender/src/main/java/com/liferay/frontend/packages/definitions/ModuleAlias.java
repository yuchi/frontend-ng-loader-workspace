package com.liferay.frontend.packages.definitions;

public class ModuleAlias {

	public ModuleAlias(String sourceName, String aliasedName) {
		_aliasedName = aliasedName;
		_sourceName = sourceName;
	}

	public String getAliasedName() {
		return _aliasedName;
	}

	public String getSourceName() {
		return _sourceName;
	}

	private String _aliasedName;
	private String _sourceName;

}
