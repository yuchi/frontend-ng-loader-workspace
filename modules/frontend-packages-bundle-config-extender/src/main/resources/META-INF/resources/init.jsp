
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
String systemJSPathExtension = themeDisplay.isThemeJsFastLoad() ? ".js" : ".src.js";
String systemJSPath = "/js/systemjs-0.19.31/dist/system" + systemJSPathExtension;
String systemJSConfigPath = "/system_js_config";
%>
