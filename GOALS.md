One of the main Lines of Work for the Frontend Infrastructure team in 2017 is to *Simplify Usage
of Modern Frameworks and Workflows when Developing Applications for Liferay Digital Enterprise*. As
the internal document collects, some of its goals are:
- Provide a modern platform to build great apps with ease by:
    - Efficiently integrating modern frontend development workflows such as npm within the platform.
    - Clarifying the usage of front-end frameworks such as Angular and React in combination with Liferay Portal / Digital Enterprise
    - Facilitate modern development of the new cloud-based applications for DXP
- Allow frontend developers with a skillset different than that of the usual portal-aware developer to be able to develop for our DXP products with state of the art tooling.

This document is intended to extend and clarify the goals pertaining the **Efficiently integrating modern frontend development workflows such as npm within the platform** task.

## Goals

- Provide a **seamless integration** with frontend development worfklows using npm
- Create an environment that **meets developer expectations** about installing and using modules
- **Preserve the performance** for the portal use case where different applications may share a common set of modules
- Be aligned with EcmaScript module compatibility

## References

- [Making transpiled ES modules more spec-compliant](http://www.2ality.com/2017/01/babel-esm-spec-mode.html)
- [JavaScript Loader Standard](https://github.com/whatwg/loader)
- [SystemJS 0.20 - Aligning with browser modules](http://guybedford.com/systemjs-alignment)
- [dotJS 2016 - Guy Bedford - Unbundling the Future Web Runtime](https://www.youtube.com/watch?v=8AvKRFhwOkk)