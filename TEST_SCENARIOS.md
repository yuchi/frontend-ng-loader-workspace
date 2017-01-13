This document collects some test scenarios that should be verified in order to consider the work at task done and satisfactory.

## Using lodash in a module

A developer is creating a portlet and wants to use [lodash endsWith](https://lodash.com/docs/4.17.4#endsWith). To do so, he expects to:
- run `npm install --save lodash` <sup>[1]</sup>
- import the desired functionality in the `main[.es].js` file
    - `import {endsWith} from 'lodash'`
    - `import endsWith from 'lodash/endsWith'`
- use it as `endsWith(fooString, 'bar')`

## Creating a React App

A developer is creating a portlet and wants to use [react](https://facebook.github.io/react/) to write its UI. To do so, he expects to:
- run `npm install --save react react-dom`
- import and use it in the `main.jsx` file <sup>[2]</sup>

```javascript
import React from 'react';
import ReactDOM from 'react-dom';

class Welcome extends React.Component {
    render() {
        return <h1>Hello, {this.props.name}</h1>
    }
} 

ReactDOM.render(
    <Welcome name="Alice">
    document.getElementById('root');
);
```

<sup>[1]</sup>Should the system know which resources to bundle and how?. A contract change might be needed for devs to specify their app entry point (main.js* by default). We could include webpack/rollup for this.

<sup>[2]</sup>This would probably involve some build configuration step