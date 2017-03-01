# OSBB WebUI partial
This is UI partial of https://github.com/ViktoriyaRyazhska/myosbb

This partial based on Angular2 Webpack Starter https://github.com/AngularClass/angular2-webpack-starter

## File Structure
```
angular2-webpack-starter/
 ├──config/                        * our configuration
 |   ├──helpers.js                 * helper functions for our configuration files
 |   ├──spec-bundle.js             * ignore this magic that sets up our angular 2 testing environment
 |   ├──karma.conf.js              * karma config for our unit tests
 |   ├──protractor.conf.js         * protractor config for our end-to-end tests
 │   ├──webpack.dev.js             * our development webpack config
 │   ├──webpack.prod.js            * our production webpack config
 │   └──webpack.test.js            * our testing webpack config
 │
 ├──src/                           * our source files that will be compiled to javascript
 |   ├──main.browser.ts            * our entry file for our browser environment
 │   │
 |   ├──index.html                 * Index.html: where we generate our index page
 │   │
 |   ├──polyfills.ts               * our polyfills file
 │   │
 │   ├──app/                       * WebApp: folder
 │   │   ├──app.component.spec.ts  * a simple test of components in app.component.ts
 │   │   ├──app.e2e.ts             * a simple end-to-end test for /
 │   │   └──app.component.ts       * a simple version of our App component components
 │   │
 │   └──assets/                    * static assets are served here
 │       ├──icon/                  * our list of icons from www.favicon-generator.org
 │       ├──service-worker.js      * ignore this. Web App service worker that's not complete yet
 │       ├──robots.txt             * for search engines to crawl your website
 │       └──humans.txt             * for humans to know who the developers are
 │
 │
 ├──tslint.json                    * typescript lint config
 ├──typedoc.json                   * typescript documentation generator
 ├──tsconfig.json                  * typescript config used outside webpack
 ├──tsconfig.webpack.json          * config that webpack uses for typescript
 ├──package.json                   * what npm uses to manage it's dependencies
 └──webpack.config.js              * webpack main configuration file
```

## Install

**npm install**

**npm run build:prod**

(more scripts are in package.json)


**PUBLIC_PATH**
This variable let you specify the base path for all the assets on your application. If it isn't defined will use value '/myosbb/'.

**BASE_URL**
You can use this variable to define **href** value for **<base>**. If it isn't defined will use value '/myosbb/'.

**BUILD_PATH**
You can use this variable to define where Webpack will build application. If it isn't defined will use value '../resources/public'

**API_SERVER**
You can use this variable to define where Angular sends requests for the API-functions. If it isn't defined will use value '/myobb'.


## Lv-214.NodeJS

Bohdan Muranets'

Bohdan Yuzyfyshyn

Ihor Kokotko

Ivanna Savchuk

Roman Stadnyk

Stanislav Kostin

Volodymyr Nadolskyi
