package com.liferay.frontend.packages.bundle.config.extender;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=Serve Package Servlet",
		"osgi.http.whiteboard.servlet.pattern=/pkg/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {ServePackageServlet.class, Servlet.class}
)
public class ServePackageServlet extends HttpServlet {

	private static final long serialVersionUID = -2683080595698939805L;

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

		String identifier = request.getPathInfo();
		String path = null;

		_logger.log(Logger.LOG_ERROR, "... " + identifier);

		if (Validator.isNull(identifier)) {
			_logger.log(Logger.LOG_ERROR, "Missing identifier");

			return;
		}

		if (identifier.startsWith(StringPool.SLASH)) {
			identifier = identifier.substring(1);
		}

		int pos = identifier.indexOf("/~/");

		if (pos >= 0) {
			path = identifier.substring(pos + 2);
			identifier = identifier.substring(0, pos);
		}

		if (Validator.isNull(path)) {
			_logger.log(Logger.LOG_ERROR, "No path sepcified in " + identifier);

			return;
		}

		PackageConfig pkgConfig = _packageRegistry.resolve(identifier);

		if (pkgConfig == null) {
			_logger.log(Logger.LOG_ERROR, "Unable to resolve " + identifier);

			return;
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		URL url = pkgConfig.getResource(path);

		if (url == null) {
			_logger.log(Logger.LOG_ERROR, "Unable to open resource " + path);

			return;
		}

		try (InputStream inputStream = url.openStream()) {
			StreamUtil.transfer(
				inputStream, servletOutputStream, false);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, "Unable to open resource", e);
		}
	}

	@Reference(unbind = "-")
	protected void setPackageRegistry(PackageRegistry packageRegistry) {
		_packageRegistry = packageRegistry;
	}

	private Logger _logger;
	private PackageRegistry _packageRegistry;

}
