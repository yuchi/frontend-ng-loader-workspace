# frontend-ng-loader-workspace

Liferay Workspace for experiments in bringing SystemJS and packages support to Liferay Portal

- - -

## Using SystemJS

Once you have installed the `frontend-packages-bundle-config-extender` module, you can start using npm as your package manager in Liferay Portal projects.

To extend support to packages that expect the presence of the Node.js built-in modules (`assert`, `util`…) you need to install the `frontend-node-compatibility-packages-contributor` module which contributes **some of those** (in a hypothetical final release most of them would be shimmed as it happens in *browserify*).

### Configuring your module/bundle

You first need to place your *package.json* file of your module in `src/main/resources/META-INF/resources` and configure it in the module’s *bnd.bdn* file:

```properties
Liferay-Package-Config: /META-INF/resources/package.json
Web-ContextPath: /my-module-name-web
```

Don‘t forget to use your module’s name as the `"name"` in the *package.json*:

```js
// my-module-name-web/src/main/resources/META-INF/resources/package.json
{
  "name": "my-module-name-web",
  "version": "1.0.0"
}
```

Install your dependencies through npm as you would do in Node.js and in *browserified/webpack’d* projects.

### Requiring dependencies inside your module/bundle

In your files you can simply require your depdendencies as you would in Node.js:

```js
// Main packages’ module
var React = require('react');
var ReactDOM = require('react-dom');

// Inner modules
var array = require('lodash/array');
```

### Requiring modules from *outside* (JSPs)

Please refer to [SistemJS’ documentation](https://github.com/systemjs/systemjs) for a full explanation. Remember that you can entirely skip the configuration of the loader since it is done automatically by the modules in this repository.

In JSPs, and more generally outside files that are treated as part of your bundle‘s package, **you need to specify the full versioned name**.

The only exclusion for this are Node.js built-in modules, in fact you can require them without an explicit version. (This is because packages don’t specify a version for them, therefore they need to be available globally as unversioned packages).

A simplified example for follows.

```js
// my-module-name-web/src/main/resources/META-INF/resources/lib/index.js
var React = require('react');
var ReactDOM = require('react');
var MyComponent = require('./MyComponent');

function render(config, wrapper) {
  return ReactDOM.render(
    React.createElement(MyComponent, config),
    document.getElementById(wrapper)
  );
}

module.exports = { render: render };
```

```jsp
<%-- my-module-name-web/src/main/resources/META-INF/resources/view.jsp --%>
<div id="<portlet:namespace />wrapper"></div>
```

Inside a `<script>` you can “import” your dependency as a Promise and use it:

```js
System
.import('my-module-name-web@1.0.0')
.then(function (MyModule) {
  MyModule.render({}, '<portlet:namespace />wrapper');
});
```

For AMD-oriented people you can also use that API:

```js
System.amdRequire([ 'my-module-name-web@1.0.0' ], function (MyModule) {
  MyModule.render({}, '<portlet:namespace />wrapper');
});
```
