// { "framework": "Vue" }
"use weex:vue";

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/pages/index/index.vue?entry=true");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/components/image.vue":
/*!*****************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/components/image.vue ***!
  \*****************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n//\n//\n//\n//\n//\n\nexports.default = {\n  data: function data() {\n    return {\n      logo: 'https://gw.alicdn.com/tfs/TB1bomekFOWBuNjy0FiXXXFxVXa-517-153.png'\n    };\n  }\n};\n\n//# sourceURL=webpack:///./src/components/image.vue?./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/button.vue":
/*!*******************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/button.vue ***!
  \*******************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _index = __webpack_require__(/*! ../../utils/index */ \"./src/utils/index.js\");\n\nvar storage = weex.requireModule('storage'); //\n//\n//\n//\n//\n\nvar navigator = weex.requireModule('navigator');\nvar titleBar = weex.requireModule('titleBar');\n\nexports.default = {\n  data: function data() {\n    return {\n      buttons: [{\n        color: '#FF6138',\n        name: 'Button hello'\n      }, {\n        color: '#79BD8F',\n        name: 'Button2'\n      }, {\n        color: '#F7B32D',\n        name: 'Button3'\n      }]\n    };\n  },\n\n  methods: {\n    handleClickBtn: function handleClickBtn(button) {\n      storage.setItem('clickedBtnName', button.name, function (event) {});\n      storage.setItem('clickedBtnColor', button.color, function (event) {});\n      this.jump('detail');\n    },\n    jump: function jump(pageName) {\n      if (WXEnvironment.platform === 'Web') {\n        location.href = location.origin + (0, _index.createLink)(pageName);\n      } else {\n        navigator.push({\n          url: (0, _index.createLink)(pageName),\n          animated: 'true'\n        });\n      }\n    }\n  },\n  created: function created() {\n    titleBar && titleBar.setTitle('页面跳转demo');\n  }\n};\n\n//# sourceURL=webpack:///./src/pages/index/button.vue?./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/index.vue":
/*!******************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/index.vue ***!
  \******************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _image = __webpack_require__(/*! ../../components/image.vue */ \"./src/components/image.vue\");\n\nvar _image2 = _interopRequireDefault(_image);\n\nvar _button = __webpack_require__(/*! ./button.vue */ \"./src/pages/index/button.vue\");\n\nvar _button2 = _interopRequireDefault(_button);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\n//\n//\n//\n//\n//\n//\n//\n//\n//\n\nexports.default = {\n  components: {\n    ImageCom: _image2.default,\n    ButtonCom: _button2.default\n  },\n  data: function data() {\n    return {\n      logo: 'https://gw.alicdn.com/tfs/TB1yopEdgoQMeJjy1XaXXcSsFXa-640-302.png'\n    };\n  }\n};\n\n//# sourceURL=webpack:///./src/pages/index/index.vue?./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/index.vue":
/*!*********************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/index.vue ***!
  \*********************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports = {\n  \"wrapper\": {\n    \"backgroundColor\": \"#eeeeee\",\n    \"display\": \"flex\",\n    \"justifyContent\": \"center\"\n  },\n  \"img-wrapper\": {\n    \"alignItems\": \"center\",\n    \"paddingTop\": \"80\",\n    \"paddingBottom\": \"50\"\n  },\n  \"logo\": {\n    \"width\": \"424\",\n    \"height\": \"200\"\n  },\n  \"greeting\": {\n    \"textAlign\": \"center\",\n    \"fontSize\": \"50\",\n    \"color\": \"#41b883\"\n  },\n  \"text\": {\n    \"marginTop\": \"50\",\n    \"marginRight\": \"20\",\n    \"marginBottom\": 0,\n    \"marginLeft\": \"20\",\n    \"paddingTop\": \"20\",\n    \"paddingRight\": \"20\",\n    \"paddingBottom\": \"20\",\n    \"paddingLeft\": \"20\",\n    \"borderRadius\": \"20\",\n    \"color\": \"#666666\",\n    \"backgroundColor\": \"#ffffff\",\n    \"lineHeight\": \"40\"\n  },\n  \"title\": {\n    \"marginTop\": \"50\",\n    \"marginBottom\": \"20\",\n    \"color\": \"#41b883\",\n    \"textAlign\": \"center\"\n  }\n}\n\n//# sourceURL=webpack:///./src/pages/index/index.vue?./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/components/image.vue":
/*!********************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/components/image.vue ***!
  \********************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports = {\n  \"wrapper\": {\n    \"justifyContent\": \"center\",\n    \"alignItems\": \"center\"\n  },\n  \"logo\": {\n    \"width\": \"517\",\n    \"height\": \"153\"\n  }\n}\n\n//# sourceURL=webpack:///./src/components/image.vue?./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/button.vue":
/*!**********************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/button.vue ***!
  \**********************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports = {\n  \"text\": {\n    \"marginTop\": \"50\",\n    \"marginRight\": \"20\",\n    \"marginBottom\": 0,\n    \"marginLeft\": \"20\",\n    \"paddingTop\": \"20\",\n    \"paddingRight\": \"20\",\n    \"paddingBottom\": \"20\",\n    \"paddingLeft\": \"20\",\n    \"borderRadius\": \"20\",\n    \"color\": \"#666666\",\n    \"backgroundColor\": \"#ffffff\",\n    \"lineHeight\": \"40\"\n  },\n  \"button\": {\n    \"marginTop\": 0,\n    \"marginRight\": \"200\",\n    \"marginBottom\": \"10\",\n    \"marginLeft\": \"200\",\n    \"paddingTop\": \"20\",\n    \"paddingRight\": \"20\",\n    \"paddingBottom\": \"20\",\n    \"paddingLeft\": \"20\",\n    \"borderRadius\": \"10\",\n    \"color\": \"#ffffff\",\n    \"textAlign\": \"center\"\n  },\n  \"button-wrapper\": {\n    \"paddingBottom\": \"50\"\n  }\n}\n\n//# sourceURL=webpack:///./src/pages/index/button.vue?./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/index.vue":
/*!***********************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/index.vue ***!
  \***********************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;\n  return _c('div', {\n    staticClass: [\"wrapper\"]\n  }, [_c('div', {\n    staticClass: [\"img-wrapper\"]\n  }, [_c('ImageCom')], 1), _c('text', {\n    staticClass: [\"greeting\"]\n  }, [_vm._v(\"Welcome to use weex.\")]), _c('text', {\n    staticClass: [\"title\"]\n  }, [_vm._v(\"点击下方按钮，实现页面跳转及传参：\")]), _c('ButtonCom')], 1)\n},staticRenderFns: []}\nmodule.exports.render._withStripped = true\n\n//# sourceURL=webpack:///./src/pages/index/index.vue?./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/components/image.vue":
/*!**********************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/components/image.vue ***!
  \**********************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;\n  return _c('div', {\n    staticClass: [\"wrapper\"]\n  }, [_c('image', {\n    staticClass: [\"logo\"],\n    attrs: {\n      \"src\": _vm.logo\n    }\n  })])\n},staticRenderFns: []}\nmodule.exports.render._withStripped = true\n\n//# sourceURL=webpack:///./src/components/image.vue?./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0");

/***/ }),

/***/ "./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/button.vue":
/*!************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/button.vue ***!
  \************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;\n  return _c('div', {\n    staticClass: [\"button-wrapper\"]\n  }, _vm._l((_vm.buttons), function(button) {\n    return _c('text', {\n      key: button.color,\n      staticClass: [\"button\"],\n      style: {\n        backgroundColor: button.color\n      },\n      on: {\n        \"click\": function($event) {\n          _vm.handleClickBtn(button)\n        }\n      }\n    }, [_vm._v(_vm._s(button.name))])\n  }))\n},staticRenderFns: []}\nmodule.exports.render._withStripped = true\n\n//# sourceURL=webpack:///./src/pages/index/button.vue?./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0");

/***/ }),

/***/ "./src/components/image.vue":
/*!**********************************!*\
  !*** ./src/components/image.vue ***!
  \**********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var __vue_exports__, __vue_options__\nvar __vue_styles__ = []\n\n/* styles */\n__vue_styles__.push(__webpack_require__(/*! !../../node_modules/weex-vue-loader/lib/style-loader!../../node_modules/weex-vue-loader/lib/style-rewriter?id=data-v-42fc4ffb!../../node_modules/weex-vue-loader/lib/selector?type=styles&index=0!./image.vue */ \"./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/components/image.vue\")\n)\n\n/* script */\n__vue_exports__ = __webpack_require__(/*! !../../node_modules/weex-vue-loader/lib/script-loader!babel-loader!../../node_modules/weex-vue-loader/lib/selector?type=script&index=0!./image.vue */ \"./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/components/image.vue\")\n\n/* template */\nvar __vue_template__ = __webpack_require__(/*! !../../node_modules/weex-vue-loader/lib/template-compiler?id=data-v-42fc4ffb!../../node_modules/weex-vue-loader/lib/selector?type=template&index=0!./image.vue */ \"./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-42fc4ffb!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/components/image.vue\")\n__vue_options__ = __vue_exports__ = __vue_exports__ || {}\nif (\n  typeof __vue_exports__.default === \"object\" ||\n  typeof __vue_exports__.default === \"function\"\n) {\nif (Object.keys(__vue_exports__).some(function (key) { return key !== \"default\" && key !== \"__esModule\" })) {console.error(\"named exports are not supported in *.vue files.\")}\n__vue_options__ = __vue_exports__ = __vue_exports__.default\n}\nif (typeof __vue_options__ === \"function\") {\n  __vue_options__ = __vue_options__.options\n}\n__vue_options__.__file = \"/Users/chenluan/workspace/weex/emas-weex-demo/src/components/image.vue\"\n__vue_options__.render = __vue_template__.render\n__vue_options__.staticRenderFns = __vue_template__.staticRenderFns\n__vue_options__._scopeId = \"data-v-42fc4ffb\"\n__vue_options__.style = __vue_options__.style || {}\n__vue_styles__.forEach(function (module) {\n  for (var name in module) {\n    __vue_options__.style[name] = module[name]\n  }\n})\nif (typeof __register_static_styles__ === \"function\") {\n  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)\n}\n\nmodule.exports = __vue_exports__\n\n\n//# sourceURL=webpack:///./src/components/image.vue?");

/***/ }),

/***/ "./src/pages/index/button.vue":
/*!************************************!*\
  !*** ./src/pages/index/button.vue ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var __vue_exports__, __vue_options__\nvar __vue_styles__ = []\n\n/* styles */\n__vue_styles__.push(__webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/style-loader!../../../node_modules/weex-vue-loader/lib/style-rewriter?id=data-v-4581fe13!../../../node_modules/weex-vue-loader/lib/selector?type=styles&index=0!./button.vue */ \"./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/button.vue\")\n)\n\n/* script */\n__vue_exports__ = __webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/script-loader!babel-loader!../../../node_modules/weex-vue-loader/lib/selector?type=script&index=0!./button.vue */ \"./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/button.vue\")\n\n/* template */\nvar __vue_template__ = __webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/template-compiler?id=data-v-4581fe13!../../../node_modules/weex-vue-loader/lib/selector?type=template&index=0!./button.vue */ \"./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-4581fe13!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/button.vue\")\n__vue_options__ = __vue_exports__ = __vue_exports__ || {}\nif (\n  typeof __vue_exports__.default === \"object\" ||\n  typeof __vue_exports__.default === \"function\"\n) {\nif (Object.keys(__vue_exports__).some(function (key) { return key !== \"default\" && key !== \"__esModule\" })) {console.error(\"named exports are not supported in *.vue files.\")}\n__vue_options__ = __vue_exports__ = __vue_exports__.default\n}\nif (typeof __vue_options__ === \"function\") {\n  __vue_options__ = __vue_options__.options\n}\n__vue_options__.__file = \"/Users/chenluan/workspace/weex/emas-weex-demo/src/pages/index/button.vue\"\n__vue_options__.render = __vue_template__.render\n__vue_options__.staticRenderFns = __vue_template__.staticRenderFns\n__vue_options__._scopeId = \"data-v-4581fe13\"\n__vue_options__.style = __vue_options__.style || {}\n__vue_styles__.forEach(function (module) {\n  for (var name in module) {\n    __vue_options__.style[name] = module[name]\n  }\n})\nif (typeof __register_static_styles__ === \"function\") {\n  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)\n}\n\nmodule.exports = __vue_exports__\n\n\n//# sourceURL=webpack:///./src/pages/index/button.vue?");

/***/ }),

/***/ "./src/pages/index/index.vue?entry=true":
/*!**********************************************!*\
  !*** ./src/pages/index/index.vue?entry=true ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var __vue_exports__, __vue_options__\nvar __vue_styles__ = []\n\n/* styles */\n__vue_styles__.push(__webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/style-loader!../../../node_modules/weex-vue-loader/lib/style-rewriter?id=data-v-1badc801!../../../node_modules/weex-vue-loader/lib/selector?type=styles&index=0!./index.vue */ \"./node_modules/weex-vue-loader/lib/style-loader.js!./node_modules/weex-vue-loader/lib/style-rewriter.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=styles&index=0!./src/pages/index/index.vue\")\n)\n\n/* script */\n__vue_exports__ = __webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/script-loader!babel-loader!../../../node_modules/weex-vue-loader/lib/selector?type=script&index=0!./index.vue */ \"./node_modules/weex-vue-loader/lib/script-loader.js!./node_modules/babel-loader/lib/index.js!./node_modules/weex-vue-loader/lib/selector.js?type=script&index=0!./src/pages/index/index.vue\")\n\n/* template */\nvar __vue_template__ = __webpack_require__(/*! !../../../node_modules/weex-vue-loader/lib/template-compiler?id=data-v-1badc801!../../../node_modules/weex-vue-loader/lib/selector?type=template&index=0!./index.vue */ \"./node_modules/weex-vue-loader/lib/template-compiler.js?id=data-v-1badc801!./node_modules/weex-vue-loader/lib/selector.js?type=template&index=0!./src/pages/index/index.vue\")\n__vue_options__ = __vue_exports__ = __vue_exports__ || {}\nif (\n  typeof __vue_exports__.default === \"object\" ||\n  typeof __vue_exports__.default === \"function\"\n) {\nif (Object.keys(__vue_exports__).some(function (key) { return key !== \"default\" && key !== \"__esModule\" })) {console.error(\"named exports are not supported in *.vue files.\")}\n__vue_options__ = __vue_exports__ = __vue_exports__.default\n}\nif (typeof __vue_options__ === \"function\") {\n  __vue_options__ = __vue_options__.options\n}\n__vue_options__.__file = \"/Users/chenluan/workspace/weex/emas-weex-demo/src/pages/index/index.vue\"\n__vue_options__.render = __vue_template__.render\n__vue_options__.staticRenderFns = __vue_template__.staticRenderFns\n__vue_options__._scopeId = \"data-v-1badc801\"\n__vue_options__.style = __vue_options__.style || {}\n__vue_styles__.forEach(function (module) {\n  for (var name in module) {\n    __vue_options__.style[name] = module[name]\n  }\n})\nif (typeof __register_static_styles__ === \"function\") {\n  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)\n}\n\nmodule.exports = __vue_exports__\nmodule.exports.el = 'true'\nnew Vue(module.exports)\n\n\n//# sourceURL=webpack:///./src/pages/index/index.vue?");

/***/ }),

/***/ "./src/utils/config.js":
/*!*****************************!*\
  !*** ./src/utils/config.js ***!
  \*****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\nexports.default = {\n  CDN_URL: ''\n};\n\n//# sourceURL=webpack:///./src/utils/config.js?");

/***/ }),

/***/ "./src/utils/index.js":
/*!****************************!*\
  !*** ./src/utils/index.js ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\nexports.getCDNBaseURL = getCDNBaseURL;\nexports.createLink = createLink;\n\nvar _config = __webpack_require__(/*! ./config */ \"./src/utils/config.js\");\n\nvar _config2 = _interopRequireDefault(_config);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nvar encoder = typeof encodeURIComponent === 'function' ? encodeURIComponent : typeof encodeURI === 'function' ? encodeURI : function (x) {\n  return x;\n};\n\nfunction getCDNBaseURL() {\n  if (true) {\n    return \"http://30.8.51.165:8080\" + '/dist';\n  }\n  return _config2.default.CDN_URL;\n}\n\nfunction createLink(name) {\n  var params = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};\n\n  var args = [];\n  for (var key in params) {\n    if (typeof params[key] === 'string') {\n      args.push(encoder(key) + '=' + encoder(params[key]));\n    }\n  }\n  if (WXEnvironment.platform === 'Web') {\n    var host = location.host;\n    var defaultPage = 'http://' + host + '/dist/' + name + '.js';\n    args.unshift('_wx_tpl=' + defaultPage);\n    return '/?' + args.join('&');\n  }\n\n  // native端\n  // Step1: 首先把子页面的weex jsbundle上传到对应的cdn上（e.g. 跨平台研发-云端控制台），\n  // Step2: 然后跳转到对应的cdn地址即可\n  var baseURL = getCDNBaseURL();\n  if (!baseURL.endsWith('/')) {\n    baseURL += '/';\n  }\n  return '' + baseURL + name + '.js' + (args.length ? '?' + args.join('&') : '');\n}\n\n//# sourceURL=webpack:///./src/utils/index.js?");

/***/ })

/******/ });