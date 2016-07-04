package com.liferay.frontend.packages.bundle.config.extender;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.log.Logger;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.frontend.packages.definitions.PackageConfig;
import com.liferay.frontend.packages.definitions.PackageDependency;
import com.liferay.frontend.packages.definitions.PackagesBundleConfig;
import com.liferay.portal.kernel.util.ContentTypes;

@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=Packages Bundle Config Servlet",
		"osgi.http.whiteboard.servlet.pattern=/pkgs_bundle_config",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {PackagesBundleConfigServlet.class, Servlet.class}
)
public class PackagesBundleConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1118113813024561105L;

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_logger = new Logger(componentContext.getBundleContext());
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType(ContentTypes.TEXT);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		Collection<PackagesBundleConfig> pkgsBundleConfigs =
			_packagesBundleConfigTracker.getPackagesBundleConfigs();

		printWriter.println("Length: " + pkgsBundleConfigs.size());

		for (PackagesBundleConfig pkgsBundleConfig : pkgsBundleConfigs) {
			List<PackageConfig> pkgConfigs =
				pkgsBundleConfig.getPackageConfigs();

			printWriter.println("");
			printWriter.println(
				"- " + pkgsBundleConfig.getServletContextPath() + " " +
					pkgConfigs.size());

			for (PackageConfig pkgConfig : pkgConfigs) {
				printWriter.println(
					"  -> " + pkgConfig.getName() + '@' +
						pkgConfig.getVersion());

				List<PackageDependency> dependencies =
					pkgConfig.getDependencies();

				for (PackageDependency dependency : dependencies) {
					PackageConfig pkg = _packageRegistry.resolve(dependency);

					if (pkg != null) {
						printWriter.println("     +- " + dependency + " (" + pkg.getVersion() + ")");
					}
					else {
						printWriter.println("     +- " + dependency + " (unmet)");
					}
				}
			}
		}

		printWriter.close();
	}

	@Reference(unbind = "-")
	protected void setPackageRegistry(PackageRegistry packageRegistry) {
		_packageRegistry = packageRegistry;
	}

	@Reference(unbind = "-")
	protected void setPackagesBundleConfigTracker(
		PackagesBundleConfigTracker packagesBundleConfigTracker) {

		_packagesBundleConfigTracker = packagesBundleConfigTracker;
	}

	private PackageRegistry _packageRegistry;
	private PackagesBundleConfigTracker _packagesBundleConfigTracker;
	private Logger _logger;

}
