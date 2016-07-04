
<%@ include file="/init.jsp" %>

<aui:script position="inline" require="frontend-js-spa-web/liferay/init.es">

	Liferay.SPA.app.on('beforeNavigate', function (event) {
		var hasOwnProperty = Object.prototype.hasOwnProperty;

		if (window.System != null) {
			window.System.defined = {};
			for (var key in window.System._loader.modules) {
				if (hasOwnProperty.call(window.System._loader.modules, key)) {
					if (key.slice(0, 2) !== '@@') {
						delete window.System._loader.modules[key];
					}
				}
			}
		}
	});

</aui:script>
