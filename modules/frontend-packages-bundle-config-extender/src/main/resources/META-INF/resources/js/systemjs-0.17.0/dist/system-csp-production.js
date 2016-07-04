/*
 * SystemJS v0.16.11
 */
!function(){function e(){!function(e){function t(e,t){var n;if(e instanceof Error){var n=new Error(e.message,e.fileName,e.lineNumber);n.message=e.message+"\n	"+t,n.stack=e.stack}else n=e+"\n	"+t;return n}function n(e,n,r){try{new Function(e).call(r)}catch(a){throw t(a,"Evaluating "+n)}}function r(){}function a(){this._loader={loaderObj:this,loads:[],modules:{},importPromises:{},moduleRecords:{}},b(this,"global",{get:function(){return e}})}function o(){a.call(this),this.paths={}}function i(e,t){var n,r="",a=0;for(var o in e.paths){var i=o.split("*");if(i.length>2)throw new TypeError("Only one wildcard in a path is permitted");if(1==i.length){if(t==o){r=o;break}}else{var s=o.split("/").length;s>=a&&t.substr(0,i[0].length)==i[0]&&t.substr(t.length-i[1].length)==i[1]&&(a=s,r=o,n=t.substr(i[0].length,t.length-i[1].length-i[0].length))}}var l=e.paths[r]||t;return n&&(l=l.replace("*",n)),l}function s(){}function l(){o.call(this),k.call(this)}function d(){}function u(e,t){l.prototype[e]=t(l.prototype[e])}function c(e){k=e(k||function(){})}function f(e){for(var t=[],n=0,r=e.length;r>n;n++)-1==x.call(t,e[n])&&t.push(e[n]);return t}function m(e,t,n){for(var r in t)n&&r in e||(e[r]=t[r])}function p(e,t){for(var n=e.split(".");n.length;)t=t[n.shift()];return t}function h(){if(D[this.baseURL])return D[this.baseURL];"/"!=this.baseURL[this.baseURL.length-1]&&(this.baseURL+="/");var e=new E(this.baseURL,w);return this.baseURL=e.href,D[this.baseURL]=e}var v="undefined"==typeof window&&"undefined"!=typeof self&&"undefined"!=typeof importScripts,g="undefined"!=typeof window&&"undefined"!=typeof document,y="undefined"!=typeof process&&!!process.platform.match(/^win/);e.console||(e.console={assert:function(){}});var b,x=Array.prototype.indexOf||function(e){for(var t=0,n=this.length;n>t;t++)if(this[t]===e)return t;return-1};!function(){try{Object.defineProperty({},"a",{})&&(b=Object.defineProperty)}catch(e){b=function(e,t,n){try{e[t]=n.value||n.get.call(e)}catch(r){}}}}();var w;if("undefined"!=typeof document&&document.getElementsByTagName){if(w=document.baseURI,!w){var S=document.getElementsByTagName("base");w=S[0]&&S[0].href||window.location.href}w=w.split("#")[0].split("?")[0],w=w.substr(0,w.lastIndexOf("/")+1)}else if("undefined"!=typeof process&&process.cwd)w="file://"+(y?"/":"")+process.cwd()+"/",y&&(w=w.replace(/\\/g,"/"));else{if("undefined"==typeof location)throw new TypeError("No environment baseURI");w=e.location.href}var E="function"==typeof e.URL&&e.URL||URLPolyfill;!function(){function o(e){return{status:"loading",name:e,linkSets:[],dependencies:[],metadata:{}}}function i(e,t,n){return new Promise(c({step:n.address?"fetch":"locate",loader:e,moduleName:t,moduleMetadata:n&&n.metadata||{},moduleSource:n.source,moduleAddress:n.address}))}function s(e,t,n,r){return new Promise(function(a){a(e.loaderObj.normalize(t,n,r))}).then(function(t){var n;if(e.modules[t])return n=o(t),n.status="linked",n.module=e.modules[t],n;for(var r=0,a=e.loads.length;a>r;r++)if(n=e.loads[r],n.name==t)return n;return n=o(t),e.loads.push(n),l(e,n),n})}function l(e,t){d(e,t,Promise.resolve().then(function(){return e.loaderObj.locate({name:t.name,metadata:t.metadata})}))}function d(e,t,n){u(e,t,n.then(function(n){return"loading"==t.status?(t.address=n,e.loaderObj.fetch({name:t.name,metadata:t.metadata,address:n})):void 0}))}function u(t,r,a){a.then(function(a){return"loading"==r.status?Promise.resolve(t.loaderObj.translate({name:r.name,metadata:r.metadata,address:r.address,source:a})).then(function(e){return r.source=e,t.loaderObj.instantiate({name:r.name,metadata:r.metadata,address:r.address,source:e})}).then(function(a){if(void 0===a)return r.address=r.address||"<Anonymous Module "+ ++k+">",r.isDeclarative=!0,transpile.call(t.loaderObj,r).then(function(t){var a=e.System,o=a.register;a.register=function(e,t,n){"string"!=typeof e&&(n=t,t=e),r.declare=n,r.depsList=t},n(t,r.address,{}),a.register=o});if("object"!=typeof a)throw TypeError("Invalid instantiate return value");r.depsList=a.deps||[],r.execute=a.execute,r.isDeclarative=!1}).then(function(){r.dependencies=[];for(var e=r.depsList,n=[],a=0,o=e.length;o>a;a++)(function(e,a){n.push(s(t,e,r.name,r.address).then(function(t){if(r.dependencies[a]={key:e,value:t.name},"linked"!=t.status)for(var n=r.linkSets.concat([]),o=0,i=n.length;i>o;o++)m(n[o],t)}))})(e[a],a);return Promise.all(n)}).then(function(){r.status="loaded";for(var e=r.linkSets.concat([]),t=0,n=e.length;n>t;t++)h(e[t],r)}):void 0})["catch"](function(e){r.status="failed",r.exception=e;for(var t=r.linkSets.concat([]),n=0,a=t.length;a>n;n++)v(t[n],r,e)})}function c(e){return function(t){var n=e.loader,r=e.moduleName,a=e.step;if(n.modules[r])throw new TypeError('"'+r+'" already exists in the module table');for(var i,s=0,c=n.loads.length;c>s;s++)if(n.loads[s].name==r)return i=n.loads[s],"translate"!=a||i.source||(i.address=e.moduleAddress,u(n,i,Promise.resolve(e.moduleSource))),i.linkSets[0].done.then(function(){t(i)});var m=o(r);m.metadata=e.moduleMetadata;var p=f(n,m);n.loads.push(m),t(p.done),"locate"==a?l(n,m):"fetch"==a?d(n,m,Promise.resolve(e.moduleAddress)):(m.address=e.moduleAddress,u(n,m,Promise.resolve(e.moduleSource)))}}function f(e,t){var n={loader:e,loads:[],startingLoad:t,loadingCount:0};return n.done=new Promise(function(e,t){n.resolve=e,n.reject=t}),m(n,t),n}function m(e,t){for(var n=0,r=e.loads.length;r>n;n++)if(e.loads[n]==t)return;e.loads.push(t),t.linkSets.push(e),"loaded"!=t.status&&e.loadingCount++;for(var a=e.loader,n=0,r=t.dependencies.length;r>n;n++){var o=t.dependencies[n].value;if(!a.modules[o])for(var i=0,s=a.loads.length;s>i;i++)if(a.loads[i].name==o){m(e,a.loads[i]);break}}}function p(e){var t=!1;try{S(e,function(n,r){v(e,n,r),t=!0})}catch(n){v(e,null,n),t=!0}return t}function h(e,t){if(e.loadingCount--,!(e.loadingCount>0)){var n=e.startingLoad;if(e.loader.loaderObj.execute===!1){for(var r=[].concat(e.loads),a=0,o=r.length;o>a;a++){var t=r[a];t.module=t.isDeclarative?{name:t.name,module:O({}),evaluated:!0}:{module:O({})},t.status="linked",g(e.loader,t)}return e.resolve(n)}var i=p(e);i||e.resolve(n)}}function v(e,n,r){var a=e.loader;n?(n&&e.loads[0].name!=n.name&&(r=t(r,"Error loading "+n.name+" from "+e.loads[0].name)),n&&(r=t(r,"Error loading "+n.name))):r=t(r,"Error linking "+e.loads[0].name);for(var o=e.loads.concat([]),i=0,s=o.length;s>i;i++){var n=o[i];a.loaderObj.failed=a.loaderObj.failed||[],-1==x.call(a.loaderObj.failed,n)&&a.loaderObj.failed.push(n);var l=x.call(n.linkSets,e);if(n.linkSets.splice(l,1),0==n.linkSets.length){var d=x.call(e.loader.loads,n);-1!=d&&e.loader.loads.splice(d,1)}}e.reject(r)}function g(e,t){if(e.loaderObj.trace){e.loaderObj.loads||(e.loaderObj.loads={});var n={};t.dependencies.forEach(function(e){n[e.key]=e.value}),e.loaderObj.loads[t.name]={name:t.name,deps:t.dependencies.map(function(e){return e.key}),depMap:n,address:t.address,metadata:t.metadata,source:t.source,kind:t.isDeclarative?"declarative":"dynamic"}}t.name&&(e.modules[t.name]=t.module);var r=x.call(e.loads,t);-1!=r&&e.loads.splice(r,1);for(var a=0,o=t.linkSets.length;o>a;a++)r=x.call(t.linkSets[a].loads,t),-1!=r&&t.linkSets[a].loads.splice(r,1);t.linkSets.splice(0,t.linkSets.length)}function y(e,t,n){try{var a=t.execute()}catch(o){return void n(t,o)}return a&&a instanceof r?a:void n(t,new TypeError("Execution must define a Module instance"))}function w(e,t,n){var r=e._loader.importPromises;return r[t]=n.then(function(e){return r[t]=void 0,e},function(e){throw r[t]=void 0,e})}function S(e,t){var n=e.loader;if(e.loads.length)for(var r=e.loads.concat([]),a=0;a<r.length;a++){var o=r[a],i=y(e,o,t);if(!i)return;o.module={name:o.name,module:i},o.status="linked",g(n,o)}}function E(e,t){return t.module.module}function _(){}var k=0;a.prototype={constructor:a,define:function(e,t,n){if(this._loader.importPromises[e])throw new TypeError("Module is already loading.");return w(this,e,new Promise(c({step:"translate",loader:this._loader,moduleName:e,moduleMetadata:n&&n.metadata||{},moduleSource:t,moduleAddress:n&&n.address})))},"delete":function(e){var t=this._loader;return delete t.importPromises[e],delete t.moduleRecords[e],t.modules[e]?delete t.modules[e]:!1},get:function(e){return this._loader.modules[e]?(_(this._loader.modules[e],[],this),this._loader.modules[e].module):void 0},has:function(e){return!!this._loader.modules[e]},"import":function(e,t){"object"==typeof t&&(t=t.name);var n=this;return Promise.resolve(n.normalize(e,t)).then(function(e){var t=n._loader;return t.modules[e]?(_(t.modules[e],[],t._loader),t.modules[e].module):t.importPromises[e]||w(n,e,i(t,e,{}).then(function(n){return delete t.importPromises[e],E(t,n)}))})},load:function(e){return this._loader.modules[e]?(_(this._loader.modules[e],[],this._loader),Promise.resolve(this._loader.modules[e].module)):this._loader.importPromises[e]||w(this,e,i(this._loader,e,{}))},module:function(e,t){var n=o();n.address=t&&t.address;var r=f(this._loader,n),a=Promise.resolve(e),i=this._loader,s=r.done.then(function(){return E(i,n)});return u(i,n,a),s},newModule:function(e){if("object"!=typeof e)throw new TypeError("Expected object");var t,n=new r;if(Object.getOwnPropertyNames&&null!=e)t=Object.getOwnPropertyNames(e);else{t=[];for(var a in e)t.push(a)}for(var o=0;o<t.length;o++)(function(t){b(n,t,{configurable:!1,enumerable:!0,get:function(){return e[t]}})})(t[o]);return Object.preventExtensions&&Object.preventExtensions(n),n},set:function(e,t){if(!(t instanceof r))throw new TypeError("Loader.set("+e+", module) must be a module");this._loader.modules[e]={module:t}},normalize:function(e){return e},locate:function(e){return e.name},fetch:function(){},translate:function(e){return e.source},instantiate:function(){}};var O=a.prototype.newModule}();var _;s.prototype=a.prototype,o.prototype=new s,d.prototype=o.prototype,l.prototype=new d;var k,O=/^[^\/]+:\/\//,D={},j=new E(w);!function(){c(function(e){return function(){e.call(this),this.baseURL=w.substr(0,w.lastIndexOf("/")+1),this.set("@empty",this.newModule({}))}}),u("normalize",function(){return function(e,t){return"."==e[0]||"/"==e[0]?new E(e,t||j).href:e}}),u("import",function(e){return function(t,n,r){return e.call(this,t,n,r).then(function(e){return e.__useDefault?e["default"]:e})}}),l.prototype.config=function(e){for(var t in e){var n=e[t],r=!1;if("object"!=typeof n||n instanceof Array)this[t]=n;else{this[t]=this[t]||{},("packages"==t||"meta"==t||"depCache"==t)&&(r=!0);for(var a in n)if("map"==t&&"string"!=typeof n[a]){var o=this.normalizeSync(a);this.defaultJSExtensions&&(o=o.substr(0,o.length-3));var i="";for(var s in this.packages)o.substr(0,s.length)==s&&(!o[s.length]||"/"==o[s.length])&&i.split("/").length<s.split("/").length&&(i=s);i&&this.packages[i].main&&(o=o.substr(0,o.length-this.packages[i].main.length-1));var s=this.packages[o]=this.packages[o]||{};s.map=n[a]}else if("bundles"==t){for(var l=[],d=0;d<e[t][a].length;d++)l.push(this.normalizeSync(e[t][a][d]));this[t][a]=l}else r?this[t][this.normalizeSync(a)]=n[a]:this[t][a]=n[a]}}e.baseURL&&h.call(this)}}(),function(){function t(e,t){return new Promise(function(n,r){try{importScripts(t.address)}catch(a){r(a)}e.onScriptLoad(t),t.metadata.registered||r(t.address+" did not call System.register or AMD define"),n("")})}if("undefined"!=typeof document)var n=document.getElementsByTagName("head")[0];var r;l.prototype.onScriptLoad=function(){e.System=r},u("fetch",function(a){return function(o){var i=this;return o.metadata.scriptLoad&&(g||v)?v?t(i,o):new Promise(function(t,a){function s(){u.readyState&&"loaded"!=u.readyState&&"complete"!=u.readyState||(d(),i.onScriptLoad(o),o.metadata.registered||a(o.address+" did not call System.register or AMD define"),t(""))}function l(){d(),a(new Error("Unable to load script "+o.address))}function d(){u.detachEvent?u.detachEvent("onreadystatechange",s):(u.removeEventListener("load",s,!1),u.removeEventListener("error",l,!1)),n.removeChild(u)}var u=document.createElement("script");u.async=!0,u.attachEvent?u.attachEvent("onreadystatechange",s):(u.addEventListener("load",s,!1),u.addEventListener("error",l,!1)),r=e.System,e.System=i,u.src=o.address,n.appendChild(u)}):a.call(this,o)}})}(),u("fetch",function(e){return function(t){return t.metadata.scriptLoad=!0,e.call(this,t)}}),function(){function t(e,t,n){if(p=!0,t)t=e.normalizeSync(t),n.name=t,t in e.defined||(e.defined[t]=n);else if(n.declarative){if(m)throw new TypeError("Multiple anonymous System.register calls in the same module file.");m=n}}function n(e,t,r){if(r[e.groupIndex]=r[e.groupIndex]||[],-1==x.call(r[e.groupIndex],e)){r[e.groupIndex].push(e);for(var a=0,o=e.normalizedDeps.length;o>a;a++){var i=e.normalizedDeps[a],s=t.defined[i];if(s&&!s.evaluated){var l=e.groupIndex+(s.declarative!=e.declarative);if(void 0===s.groupIndex||s.groupIndex<l){if(void 0!==s.groupIndex&&(r[s.groupIndex].splice(x.call(r[s.groupIndex],s),1),0==r[s.groupIndex].length))throw new TypeError("Mixed dependency cycle detected");s.groupIndex=l}n(s,t,r)}}}}function r(e,t){var r=t.defined[e];if(!r.module){r.groupIndex=0;var a=[];n(r,t,a);for(var i=!!r.declarative==a.length%2,l=a.length-1;l>=0;l--){for(var d=a[l],u=0;u<d.length;u++){var c=d[u];i?o(c,t):s(c,t)}i=!i}}}function a(e,t){return t[e]||(t[e]={name:e,dependencies:[],exports:{},importers:[]})}function o(t,n){if(!t.module){var r=n._loader.moduleRecords,i=t.module=a(t.name,r),s=t.module.exports,l=t.declare.call(e,function(e,t){i.locked=!0,s[e]=t;for(var n=0,r=i.importers.length;r>n;n++){var a=i.importers[n];if(!a.locked){var o=x.call(a.dependencies,i);a.setters[o](s)}}return i.locked=!1,t});if(i.setters=l.setters,i.execute=l.execute,!i.setters||!i.execute)throw new TypeError("Invalid System.register form for "+t.name);for(var d=0,u=t.normalizedDeps.length;u>d;d++){var c,f=t.normalizedDeps[d],m=n.defined[f],p=r[f];p?c=p.exports:m&&!m.declarative?c=m.esModule:m?(o(m,n),p=m.module,c=p.exports):c=n.get(f),p&&p.importers?(p.importers.push(i),i.dependencies.push(p)):i.dependencies.push(null),i.setters[d]&&i.setters[d](c)}}}function i(e,t){var n,r=t.defined[e];if(r)r.declarative?d(e,[],t):r.evaluated||s(r,t),n=r.module.exports;else if(n=t.get(e),!n)throw new Error("Unable to load dependency "+e+".");return(!r||r.declarative)&&n&&n.__useDefault?n["default"]:n}function s(t,n){if(!t.module){var r={},a=t.module={exports:r,id:t.name};if(!t.executingRequire)for(var o=0,l=t.normalizedDeps.length;l>o;o++){var d=t.normalizedDeps[o],u=n.defined[d];u&&s(u,n)}t.evaluated=!0;var c=t.execute.call(e,function(e){for(var r=0,a=t.deps.length;a>r;r++)if(t.deps[r]==e)return i(t.normalizedDeps[r],n);throw new TypeError("Module "+e+" not declared as a dependency.")},r,a);if(c&&(a.exports=c),r=a.exports,r&&r.__esModule)t.esModule=r;else{var f=r&&r.hasOwnProperty;t.esModule={};for(var m in r)(!f||r.hasOwnProperty(m))&&(t.esModule[m]=r[m]);t.esModule["default"]=r,t.esModule.__useDefault=!0}}}function d(t,n,r){var a=r.defined[t];if(a&&!a.evaluated&&a.declarative){n.push(t);for(var o=0,i=a.normalizedDeps.length;i>o;o++){var s=a.normalizedDeps[o];-1==x.call(n,s)&&(r.defined[s]?d(s,n,r):r.get(s))}a.evaluated||(a.evaluated=!0,a.module.execute.call(e))}}var m,p;l.prototype.register=function(e,n,r){return"string"!=typeof e&&(r=n,n=e,e=null),"boolean"==typeof r?this.registerDynamic.apply(this,arguments):void t(this,e,{declarative:!0,deps:n,declare:r})},l.prototype.registerDynamic=function(e,n,r,a){"string"!=typeof e&&(a=r,r=n,n=e,e=null),t(this,e,{declarative:!1,deps:n,execute:a,executingRequire:r})},c(function(e){return function(){e.call(this),this.defined={},this._loader.moduleRecords={}}}),u("onScriptLoad",function(e){return function(t){e.call(this,t),m&&(t.metadata.entry=m),p&&(t.metadata.format=t.metadata.format||"register",t.metadata.registered=!0,p=!1,m=null)}}),u("delete",function(e){return function(t){return delete this._loader.moduleRecords[t],delete this.defined[t],e.call(this,t)}});var h=/^\s*(\/\*.*\*\/\s*|\/\/[^\n]*\s*)*System\.register(Dyanmic)?\s*\(/;u("fetch",function(e){return function(t){return this.defined[t.name]?(t.metadata.format="defined",""):(m=null,p=!1,"register"==t.metadata.format&&(t.metadata.scriptLoad=!0),e.call(this,t))}}),u("translate",function(e){return function(t){return Promise.resolve(e.call(this,t)).then(function(e){return"string"==typeof t.metadata.deps&&(t.metadata.deps=t.metadata.deps.split(",")),t.metadata.deps=t.metadata.deps||[],("register"==t.metadata.format||!t.metadata.format&&t.source.match(h))&&(t.metadata.format="register"),e})}}),u("instantiate",function(e){return function(t){var n,a=this;if(a.defined[t.name])n=a.defined[t.name],n.deps=n.deps.concat(t.metadata.deps);else if(t.metadata.entry)n=t.metadata.entry;else if(t.metadata.execute)n={declarative:!1,deps:t.metadata.deps||[],execute:t.metadata.execute,executingRequire:t.metadata.executingRequire};else if(!("register"!=t.metadata.format&&"esm"!=t.metadata.format&&"es6"!=t.metadata.format||(m=null,p=!1,__exec.call(a,t),m?n=m:t.metadata.bundle=!0,!n&&a.defined[t.name]&&(n=a.defined[t.name]),p||t.metadata.registered)))throw new TypeError(t.name+" detected as System.register but didn't execute.");if(!n)return{deps:t.metadata.deps,execute:function(){return a.newModule({})}};if(!n)return e.call(a,t);a.defined[t.name]=n,n.deps=f(n.deps),n.name=t.name;for(var o=[],i=0,s=n.deps.length;s>i;i++)o.push(Promise.resolve(a.normalize(n.deps[i],t.name)));return Promise.all(o).then(function(e){return n.normalizedDeps=e,{deps:n.deps,execute:function(){return r(t.name,a),d(t.name,[],a),a.defined[t.name]=void 0,a.newModule(n.declarative?n.module.exports:n.esModule)}}})}})}(),c(function(t){return function(){function n(t){if(Object.keys)Object.keys(e).forEach(t);else for(var n in e)i.call(e,n)&&t(n)}function r(t){n(function(n){if(-1==x.call(s,n)){try{var r=e[n]}catch(a){s.push(n)}t(n,r)}})}var a=this;t.call(a);var o,i=Object.prototype.hasOwnProperty,s=["_g","sessionStorage","localStorage","clipboardData","frames","external"];a.set("@@global-helpers",a.newModule({prepareGlobal:function(t,n,a){var i;if(a){i={};for(var s in a)i[s]=a[s],e[s]=a[s]}return n||(o={},r(function(e,t){o[e]=t})),function(){var t;if(n)t=p(n,e);else{var a,s,l={};r(function(e,t){o[e]!==t&&"undefined"!=typeof t&&(l[e]=t,"undefined"!=typeof a?s||a===t||(s=!0):a=t)}),t=s?l:a}if(i)for(var d in i)e[d]=i[d];return t}}}))}}),c(function(t){return function(){function n(e,t){e=e.replace(s,"");var n=e.match(u),r=(n[1].split(",")[t]||"require").replace(c,""),a=f[r]||(f[r]=new RegExp(l+r+d,"g"));a.lastIndex=0;for(var o,i=[];o=a.exec(e);)i.push(o[2]||o[3]);return i}function r(e,t,n,a){if("object"==typeof e&&!(e instanceof Array))return r.apply(null,Array.prototype.splice.call(arguments,1,arguments.length-1));if("string"==typeof e&&"function"==typeof t&&(e=[e]),!(e instanceof Array)){if("string"==typeof e){var o=i.get(e);return o.__useDefault?o["default"]:o}throw new TypeError("Invalid require")}for(var s=[],l=0;l<e.length;l++)s.push(i["import"](e[l],a));Promise.all(s).then(function(e){t&&t.apply(null,e)},n)}function a(t,a,o){"string"!=typeof t&&(o=a,a=t,t=null),a instanceof Array||(o=a,a=["require","exports","module"].splice(0,o.length)),"function"!=typeof o&&(o=function(e){return function(){return e}}(o)),void 0===a[a.length-1]&&a.pop();var s,l,d;-1!=(s=x.call(a,"require"))&&(a.splice(s,1),t||(a=a.concat(n(o.toString(),s)))),-1!=(l=x.call(a,"exports"))&&a.splice(l,1),-1!=(d=x.call(a,"module"))&&a.splice(d,1);var u={name:t,deps:a,execute:function(t,n,u){for(var c=[],f=0;f<a.length;f++)c.push(t(a[f]));u.uri=i.baseURL+("/"==u.id[0]?u.id:"/"+u.id),u.config=function(){},-1!=d&&c.splice(d,0,u),-1!=l&&c.splice(l,0,n),-1!=s&&c.splice(s,0,function(e,n,a){return"string"==typeof e&&"function"!=typeof n?t(e):r.call(i,e,n,a,u.id)});var m=e.require;e.require=r;var p=o.apply(-1==l?e:n,c);return e.require=m,"undefined"==typeof p&&u&&(p=u.exports),"undefined"!=typeof p?p:void 0}};if(t)0!=a.length||m.anonDefine||m.isBundle?(m.anonDefine&&m.anonDefine.name&&i.registerDynamic(m.anonDefine.name,m.anonDefine.deps,!1,m.anonDefine.execute),m.anonDefine=null):m.anonDefine=u,m.isBundle=!0,i.registerDynamic(t,u.deps,!1,u.execute);else{if(m.anonDefine)throw new TypeError("Multiple defines for anonymous module");m.anonDefine=u}}function o(){m.anonDefine=null,m.isBundle=!1;var t=e.module,n=e.exports,r=e.define;return e.module=void 0,e.exports=void 0,e.define=a,function(){e.define=r,e.module=t,e.exports=n}}var i=this;t.call(this);var s=/(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/gm,l="(?:^|[^$_a-zA-Z\\xA0-\\uFFFF.])",d="\\s*\\(\\s*(\"([^\"]+)\"|'([^']+)')\\s*\\)",u=/\(([^\)]*)\)/,c=/^\s+|\s+$/g,f={};a.amd={};var m={isBundle:!1,anonDefine:null};i.set("@@amd-helpers",i.newModule({createDefine:o,require:r,define:a,lastModule:m})),i.amdDefine=a,i.amdRequire=r}}),c(function(e){return function(){e.call(this),this.map={}}}),u("normalize",function(e){return function(t,n,r){if("."!=t.substr(0,1)&&"/"!=t.substr(0,1)&&!t.match(O)){var a,o=0;for(var i in this.map)if(t.substr(0,i.length)==i&&(t.length==i.length||"/"==t[i.length])){var s=i.split("/").length;if(o>=s)continue;a=i,o=s}a&&(t=this.map[a]+t.substr(a.length))}return e.call(this,t,n,r)}}),u("normalize",function(e){return function(t,n){var r=e.call(this,t,n);return this.has(r)?r:(this.defaultJSExtensions&&".js"!=r.substr(r.length-3,3)&&(r+=".js"),r.match(O)?r:(r=i(this,r)||r,"."==r[0]||"/"==r[0]?new E(r,j).href:new E(r,h.call(this)).href))}}),function(){function e(e){for(var t in this.packages)if(e.substr(0,t.length)===t&&(e.length===t.length||"/"===e[t.length]))return t}function t(e,t){var n,r=0;for(var a in e)if(t.substr(0,a.length)==a&&(t.length==a.length||"/"==t[a.length])){var o=a.split("/").length;if(r>=o)continue;n=a,r=o}return n?e[n]+t.substr(n.length):void 0}c(function(e){return function(){e.call(this),this.packages={}}}),u("normalize",function(n){return function(r,a){if(a)var o=e.call(this,a);if(o&&"."!==r[0]){var i=this.packages[o].map;i&&(r=t(i,r)||r,"."===r[0]&&(a=o+"/"))}var s=n.call(this,r,a),l=e.call(this,s);if(l){var d=this.packages[l];if(l===s){if(!d.main)return s;s+="/"+("./"==d.main.substr(0,2)?d.main.substr(2):d.main)}var u;d.defaultExtension&&-1==s.split("/").pop().indexOf(".")&&(u="."+d.defaultExtension);var c="."+s.substr(l.length),f=t(d.map,c)||u&&t(d.map,c+u);f?s="./"==f.substr(0,2)?l+f.substr(1):f:!u||d.meta&&d.meta[c.substr(2)]||(s+=u)}return s}}),u("locate",function(t){return function(n){var r=this;return Promise.resolve(t.call(this,n)).then(function(t){var a=e.call(r,n.name);if(a){var o=r.packages[a];if(o.format&&(n.metadata.format=n.metadata.format||o.format),o.loader&&(n.metadata.loader=n.metadata.loader||o.loader),o.meta){var i,s={},l=0;for(var d in o.meta)if(i=d.indexOf("*"),-1!==i&&d.substr(0,i)===n.name.substr(0,i)&&d.substr(i+1)===n.name.substr(n.name.length-d.length+i+1)){var u=d.split("/").length;u>l&&(bestDetph=u),m(s,o.meta[d],l!=u)}var c=o.meta[n.name.substr(a.length+1)];c&&m(s,c),s.alias&&"./"==s.alias.substr(0,2)&&(s.alias=a+s.alias.substr(1)),s.loader&&"./"==s.loader.substr(0,2)&&(s.loader=a+s.loader.substr(1)),m(n.metadata,s)}}return t})}})}(),function(){u("normalize",function(e){return function(t,n){var r,a=this;n&&-1!=(r=n.indexOf("!"))&&(n=n.substr(0,r));var o=t.lastIndexOf("!");if(-1!=o){var i=t.substr(0,o),s=t.substr(o+1)||i.substr(i.lastIndexOf(".")+1);return i=a.normalizeSync(i,n),a.defaultJSExtensions&&".js"==i.substr(i.length-3,3)&&(i=i.substr(0,i.length-3)),i+"!"+a.normalizeSync(s,n)}return e.call(a,t,n)}}),l.prototype.normalizeSync=l.prototype.normalize,u("locate",function(e){return function(t){var n=this,r=t.name,a=r.lastIndexOf("!");return-1!=a&&(t.metadata.loader=r.substr(a+1),t.name=r.substr(0,a)),e.call(n,t).then(function(e){var a=t.metadata.loader;if(!a)return e;if(n.defined&&n.defined[r])return e;var o=n.pluginLoader||n;return o["import"](a).then(function(a){return t.metadata.loaderModule=a,t.metadata.loaderArgument=r,t.address=e,a.locate?a.locate.call(n,t):e})})}}),u("fetch",function(e){return function(t){var n=this;return t.metadata.loaderModule&&t.metadata.loaderModule.fetch?(t.metadata.scriptLoad=!1,t.metadata.loaderModule.fetch.call(n,t,function(t){return e.call(n,t)})):e.call(n,t)}}),u("translate",function(e){return function(t){var n=this;return t.metadata.loaderModule&&t.metadata.loaderModule.translate?Promise.resolve(t.metadata.loaderModule.translate.call(n,t)).then(function(r){return"string"==typeof r&&(t.source=r),e.call(n,t)}):e.call(n,t)}}),u("instantiate",function(e){return function(t){var n=this;return t.metadata.loaderModule&&t.metadata.loaderModule.instantiate?Promise.resolve(t.metadata.loaderModule.instantiate.call(n,t)).then(function(r){return t.metadata.format="defined",t.metadata.execute=function(){return r},e.call(n,t)}):e.call(n,t)}})}(),function(){u("fetch",function(e){return function(t){var n=t.metadata.alias;return n?(t.metadata.format="defined",this.defined[t.name]={declarative:!0,deps:[n],declare:function(e){return{setters:[function(t){for(var n in t)e(n,t[n])}],execute:function(){}}}},""):e.call(this,t)}})}(),function(){function e(e,t,n){for(var r,a=t.split(".");a.length>1;)r=a.shift(),e=e[r]=e[r]||{};r=a.shift(),r in e||(e[r]=n)}c(function(e){return function(){this.meta={},e.call(this)}}),u("locate",function(e){return function(t){var n,r=this.meta,a=t.name,o=0;for(var i in r)if(n=x.call(i,"*"),-1!==n&&i.substr(0,n)===a.substr(0,n)&&i.substr(n+1)===a.substr(a.length-i.length+n+1)){var s=i.split("/").length;s>o&&(bestDetph=s),m(t.metadata,r[i],o!=s)}return r[a]&&m(t.metadata,r[a]),e.call(this,t)}});var t=/^(\s*\/\*.*\*\/|\s*\/\/[^\n]*|\s*"[^"]+"\s*;?|\s*'[^']+'\s*;?)+/,n=/\/\*.*\*\/|\/\/[^\n]*|"[^"]+"\s*;?|'[^']+'\s*;?/g;u("translate",function(r){return function(a){var o=a.source.match(t);if(o)for(var i=o[0].match(n),s=0;s<i.length;s++){var l=i[s],d=l.length,u=l.substr(0,1);if(";"==l.substr(d-1,1)&&d--,'"'==u||"'"==u){var c=l.substr(1,l.length-3),f=c.substr(0,c.indexOf(" "));if(f){var m=c.substr(f.length+1,c.length-f.length-1);"[]"==f.substr(f.length-2,2)&&(f=f.substr(0,f.length-2),a.metadata[f]=a.metadata[f]||[]),a.metadata[f]instanceof Array?a.metadata[f].push(m):e(a.metadata,f,m)}}}return r.call(this,a)}})}(),function(){function e(e,t){return Promise.resolve(e.normalize(t)).then(function(n){return e.loadedBundles_[n]=!0,e.bundles[n]=e.bundles[n]||e.bundles[t],e.load(n)}).then(function(){return""})}c(function(e){return function(){e.call(this),this.bundles={},this.loadedBundles_={}}}),u("locate",function(e){return function(t){return(t.name in this.loadedBundles_||t.name in this.bundles)&&(t.metadata.bundle=!0),e.call(this,t)}}),u("fetch",function(t){return function(n){var r=this;if(r.trace)return t.call(r,n);if(n.name in r.defined)return"";for(var a in r.loadedBundles_)if(-1!=x.call(r.bundles[a],n.name))return e(r,a);for(var a in r.bundles)if(-1!=x.call(r.bundles[a],n.name))return e(r,a);return t.call(r,n)}})}(),function(){c(function(e){return function(){e.call(this),this.depCache={}}}),u("locate",function(e){return function(t){var n=this,r=n.depCache[t.name];if(r)for(var a=0;a<r.length;a++)n["import"](r[a]);return e.call(n,t)}})}(),function(){var e=/#\{[^\}]+\}|#\?.+$/;u("normalize",function(t){return function(n,r,a){var o=this,i=n.match(e);if(i){var s="?"!=i[0][1],l=s?i[0].substr(2,i[0].length-3):i[0].substr(2);if("."==l[0]||-1!=l.indexOf("/"))throw new TypeError("Invalid condition "+i[0]+"\n	Condition modules cannot contain . or / in the name.");var d="default",u=l.indexOf(".");-1!=u&&(d=l.substr(u+1),l=l.substr(0,u));var c=!s&&"~"==l[0];return c&&(l=l.substr(1)),o["import"](l,r,a).then(function(l){var u=p(d,l);if(s){if("string"!=typeof u)throw new TypeError("The condition value for "+i[0]+" doesn't resolving to a string.");n=n.replace(e,u)}else{if("boolean"!=typeof u)throw new TypeError("The condition value for "+i[0]+" isn't resolving to a boolean.");c&&(u=!u),u||(n="@empty")}return t.call(o,n,r,a)})}return Promise.resolve(t.call(o,n,r,a))}})}(),_=new l,_.constructor=l,"object"==typeof exports&&(module.exports=a),e.Reflect=e.Reflect||{},e.Reflect.Loader=e.Reflect.Loader||a,e.Reflect.global=e.Reflect.global||e,e.LoaderPolyfill=a,_||(_=new o,_.constructor=o),"object"==typeof exports&&(module.exports=_),e.System=_}("undefined"!=typeof self?self:global)}if("undefined"==typeof Promise||"function"!=typeof URL)if("undefined"!=typeof document){var t=document.getElementsByTagName("script");$__curScript=t[t.length-1];var n=$__curScript.src,r=n.substr(0,n.lastIndexOf("/")+1);window.systemJSBootstrap=e,document.write('<script type="text/javascript" src="'+r+'system-polyfills.js"></script>')}else if("undefined"!=typeof importScripts){var r="";try{throw new Error("_")}catch(a){a.stack.replace(/(?:at|@).*(http.+):[\d]+:[\d]+/,function(e,t){r=t.replace(/\/[^\/]*$/,"/")})}importScripts(r+"system-polyfills.js"),e()}else e();else e()}();
//# sourceMappingURL=system-csp-production.js.map