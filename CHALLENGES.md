This document collects some of the challenges that can be foreseen when approaching a more thorough package-aware integration in the context of Liferay DXP.

## Challenges

* Developer expectations (when compared to a standalone frontend project) may be hard to meet.
    * One such expectation might be that everything installable should be runnable, which may not be 100% true for node.js packages unless the proper environment is provided.
* Heterogeneous ecosystem. Not all modules out there are built/exported/released in a uniform way, so finding a common solution and covering edge cases is a huge work.
* Portal dynamic environment. A portal presents a variety of dynamic scenarios hardly ever found in regular projects, making it hard to:
    * Unkown page setup beforehand. It's not known which combination of apps/modules are required in a page.
    * HTTP-Request optimization. On a non-http2 scenario, it might be important to still be able to generate combo requests or split common bundles to be reused
* Existing code should continue to work or have a clear and simple upgrade path.