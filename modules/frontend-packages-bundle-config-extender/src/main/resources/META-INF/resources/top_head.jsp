
<%@ include file="/init.jsp" %>

<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNDynamicResourcesHost() + application.getContextPath() + systemJSPath)) %>" type="text/javascript"></script>
<script src="<%= HtmlUtil.escape(themeDisplay.getCDNDynamicResourcesHost() + PortalUtil.getPathModule() + systemJSConfigPath) %>" type="text/javascript"></script>
