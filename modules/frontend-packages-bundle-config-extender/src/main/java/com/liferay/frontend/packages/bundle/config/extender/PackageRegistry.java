package com.liferay.frontend.packages.bundle.config.extender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.frontend.packages.definitions.Builtin;
import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.frontend.packages.definitions.PackagesBundleConfig;
import com.liferay.frontend.packages.definitions.npm.NPMPackageDependency;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

@Component(immediate = true, service = PackageRegistry.class)
public class PackageRegistry {

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_builtins = new ArrayList<Builtin>();

		_builtins.add(new Builtin("events", new NPMPackageDependency(
			"events", StringPool.STAR), null));
		_builtins.add(new Builtin("domain", new NPMPackageDependency(
			"domain-browser", StringPool.STAR), null));
		_builtins.add(new Builtin("assert", new NPMPackageDependency(
			"assert", StringPool.STAR), null));
	}

	public List<Builtin> getBuiltins() {
		return _builtins;
	}

	public PackageConfig[] getPackageConfigs() {
		return getPackagesBundleConfigs().stream()
			.flatMap(pkgsBundleConfig ->
				pkgsBundleConfig.getPackageConfigs().stream())
			.distinct()
			.toArray(PackageConfig[]::new);
	}

	public Collection<PackagesBundleConfig> getPackagesBundleConfigs() {
		if (_packagesBundleConfigTracker != null) {
			return _packagesBundleConfigTracker.getPackagesBundleConfigs();
		}
		else {
			return Collections.emptyList();
		}
	}

	public Stream<PackageConfig> resolveAll(PackageDependency dependency) {
		return ListUtil.fromArray(getPackageConfigs()).stream()
			.filter(dependency::matches)
			.sorted((p1, p2) ->
				p2.getVersion().compareWithBuildsTo(p1.getVersion()));
	}

	public PackageConfig resolve(PackageDependency dependency) {
		return resolveAll(dependency).findFirst().orElse(null);
	}

	public PackageConfig resolve(String identifier) {
		return ListUtil.fromArray(getPackageConfigs()).stream()
			.filter(pkgConfig -> pkgConfig.getIdentifier().equals(identifier))
			.sorted((p1, p2) ->
				p2.getVersion().compareWithBuildsTo(p1.getVersion()))
			.findFirst()
			.orElse(null);
	}

	@Reference(unbind = "-")
	protected void setJSBundleConfigTracker(
		PackagesBundleConfigTracker packagesBundleConfigTracker) {

		_packagesBundleConfigTracker = packagesBundleConfigTracker;
	}

	private List<Builtin> _builtins;
	private PackagesBundleConfigTracker _packagesBundleConfigTracker;

}
