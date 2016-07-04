package com.liferay.frontend.packages.definitions;

public interface PackageDependency {

	/**
	 * @return the contextual name of this dependency
	 */
	public String getName();

	/**
	 * @return the identifier of the resolved dependency
	 */
	public PackageIdentifier getPackageIdentifier();

	public boolean matches(PackageConfig pkgConfig);

	public boolean matches(PackageIdentifier pkgIdentifier);

	/*public PackageDependency(String name, String value) {
		_name = name;
		_location = value;

		try {
			Parser<Expression> parser = ExpressionParser.newInstance();

			_expression = parser.parse(value);
			_type = PackageDependencyType.NPM_VERSION_RANGE;
		}
		catch (ParseException pe) {
			_type = PackageDependencyType.LOCATION;
		}
	}

	public boolean matches(PackageConfig pkgConfig) {
		if ((_type == PackageDependencyType.NPM_VERSION_RANGE) &&
			StringUtil.equalsIgnoreCase(pkgConfig.getName(), _name)) {

			return _expression.interpret(pkgConfig.getVersion());
		}
		else {
			return false;
		}
	}

	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		return _name + '@' + _location;
	}

	private Expression _expression;
	private String _location;
	private String _name;
	private PackageDependencyType _type;*/

}