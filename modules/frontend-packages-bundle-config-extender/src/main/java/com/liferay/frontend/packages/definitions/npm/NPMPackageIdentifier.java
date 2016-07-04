package com.liferay.frontend.packages.definitions.npm;

import com.liferay.frontend.packages.definitions.PackageIdentifier;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

public class NPMPackageIdentifier implements PackageIdentifier {

	public static final String NPM_PREFIX = "npm";

	private static final long serialVersionUID = -7618303306992427416L;

	public NPMPackageIdentifier(String name) {
		if (name.startsWith(StringPool.AT)) {
			String[] parts = StringUtil.split(
				name.substring(1), CharPool.SLASH);

			if ((parts != null) && (parts.length == 2)) {
				_scope = parts[0];
				_name = parts[1];
			}
			else {
				_scope = null;
				_name = name;
			}
		}
		else {
			_scope = null;
			_name = name;
		}
	}

	public NPMPackageIdentifier(String scope, String name) {
		_scope = scope;
		_name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof NPMPackageIdentifier)) {
			return false;
		}

		NPMPackageIdentifier other = (NPMPackageIdentifier)obj;

		if (StringUtil.equalsIgnoreCase(getScope(), other.getScope()) &&
			StringUtil.equalsIgnoreCase(getName(), other.getName())) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public String getScope() {
		return _scope;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append(NPM_PREFIX);
		sb.append(StringPool.COLON);

		if (Validator.isNotNull(getScope())) {
			sb.append(getScope());
			sb.append(StringPool.SLASH);
		}

		sb.append(getName());

		return sb.toString();
	}

	private final String _name;
	private final String _scope;

}
