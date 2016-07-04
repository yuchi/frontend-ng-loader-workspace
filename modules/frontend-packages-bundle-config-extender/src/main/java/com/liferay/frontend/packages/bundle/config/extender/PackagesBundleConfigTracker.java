package com.liferay.frontend.packages.bundle.config.extender;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletContext;

import org.apache.felix.utils.log.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.liferay.frontend.packages.definitions.PackageInterpreter;
import com.liferay.frontend.packages.definitions.PackagesBundleConfig;
import com.liferay.osgi.util.ServiceTrackerFactory;

@Component(immediate = true, service = PackagesBundleConfigTracker.class)
public class PackagesBundleConfigTracker
	implements
		ServiceTrackerCustomizer<
			ServletContext, ServiceReference<ServletContext>> {

	@Activate
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		_bundleContext = componentContext.getBundleContext();

		_logger = new Logger(_bundleContext);

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			this);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders();

		String pkgsConfig = headers.get("Liferay-Package-Config");

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		if (pkgsConfig != null) {
			for (PackageInterpreter interpreter : _interpreters) {
				Optional<PackagesBundleConfig> pkgsBundleConfig =
					interpreter.interpret(servletContext, pkgsConfig);

				if (pkgsBundleConfig.isPresent()) {
					_pkgsBundleConfigs.put(
						serviceReference, pkgsBundleConfig.get());

					return serviceReference;
				}
			}
		}

		return null;
	}

	public Collection<PackagesBundleConfig> getPackagesBundleConfigs() {
		return _pkgsBundleConfigs.values();
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		removedService(serviceReference, trackedServiceReference);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		PackagesBundleConfig pkgBundleConfig = _pkgsBundleConfigs.remove(
			serviceReference);

		if (pkgBundleConfig != null) {
			_bundleContext.ungetService(serviceReference);
		}
	}

	public void bindInterpreter(PackageInterpreter interpreter) {
		_interpreters.add(interpreter);
	}

	public void unbindInterpreter(PackageInterpreter interpreter) {
		_interpreters.remove(interpreter);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		bind = "bindInterpreter", unbind = "unbindInterpreter"
	)
	private List<PackageInterpreter> _interpreters =
		new ArrayList<PackageInterpreter>();

	private BundleContext _bundleContext;
	private final Map<ServiceReference<ServletContext>, PackagesBundleConfig>
		_pkgsBundleConfigs = new ConcurrentSkipListMap<>();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;
	private Logger _logger;

}
