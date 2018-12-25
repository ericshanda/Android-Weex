## 跨平台研发

本demo工程仅供参考

assets文件夹中会根据您在平台上选定的首页结构自动生成weex-container.json文件，例如：

```json
{
  "TabSize": "5",
  "JsSource": [
    "http://page1.js",
    "http://page2.js",
    "http://page3.js",
    "http://page4.js",
    "http://page5.js"
  ]
}
```

* TabSize = 0 将进入原生首页，启动Activity为WelcomeActivity.java，里面是一些诸如mtop、高可用等相关SDK的使用demo
* TabSize > 1 将进入多tab的跨平台首页（weex），每个tab所渲染的是JsSource下面的具体的路径所示js bundle
* TabSize > = 同上，将进入单页面跨平台首页

> 以上逻辑可参考SplashActivity.java

## H5开发

SDK接入请参考 https://yuque.antfin-inc.com/sp3clu/ktt6f1/hpl7rg




## apk 构建

具体参考 `buildApk.sh`

大致步骤如下：

1. 先发布bundle version1 到仓库
2. app 依赖 bundle versin1， 执行 ./gradlew assembleDebug
3. 安装

