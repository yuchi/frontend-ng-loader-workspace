package com.liferay.frontend.packages.definitions.npm;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.Parser;
import com.github.zafarkhaja.semver.expr.Expression;
import com.github.zafarkhaja.semver.expr.ExpressionParser;
import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.frontend.packages.definitions.PackageIdentifier;
import com.liferay.portal.kernel.util.StringUtil;

public class NPMPackageDependency implements PackageDependency {

	public NPMPackageDependency(String name, String value)
		throws ParseException {

		Parser<Expression> parser = ExpressionParser.newInstance();

		value = StringUtil.replace(value, new String [] {
			"||", "&&", "-0"
		}, new String[] {
			"|", "&", ""
		});

		_rangeExpression = parser.parse(value);
		_pkgIdentifier = new NPMPackageIdentifier(name);
		_name = name;
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
			_rangeExpression.interpret(pkgConfig.getVersion())) {

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

	private String _name = null;
	private Expression _rangeExpression = null;
	private PackageIdentifier _pkgIdentifier = null;

}
