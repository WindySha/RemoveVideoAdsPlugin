![](https://upload-images.jianshu.io/upload_images/1639238-d39849bfa8575dca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/280)
## 简介
这是一个Xposed插件（apk），目前有两个功能：
1. 去除腾讯视频App播放视频前的广告
2. 去除爱奇艺App播放视频前的广告

后面会增加更多视频App的支持。

## APK下载
进入Release页面下载
或者[点我下载](https://github.com/WindySha/RemoveVideoAdsPlugin/releases/download/v1.0/no_ads_v1.0.apk)

## 注意事项
目前对爱奇艺去广告的支持还不是太好，第一次播放视频依旧有广告。
需要打开某视频播放页，再退出播放页，再进入播放页，则不会播放广告。

## 使用方法
### Root环境:
   接入Xposed框架，安装此apk，然后在Xposed插件管理器中开启这个插件即可。
### 非Root环境:
   使用本人开源[Xpatch](https://github.com/WindySha/Xpatch)工具对腾讯视频App或爱奇艺App重打包，卸载原来的app，安装重打包后的app，再安装此插件，启动腾讯视频或者爱奇艺，即可实现视频去广告。(需要在安装了java环境的PC上操作)

Xpatch源码：
https://github.com/WindySha/Xpatch
Xpatch工具下载目录：
https://github.com/WindySha/Xpatch/releases/tag/v1.3

## Xpatch用法
Xpatch工具对APK进行重打包，重打包后的APK可以自动加载已安装的Xposed插件，从而实现对App的随意篡改。

先下载Xpatch Jar包
在PC上在命令行中运行如下命令：
```
$ java -jar ../xpatch.jar ../tencent_video.apk
```
这条命令之后，在原apk文件(tencent_video.apk)相同的文件夹中，会生成一个名称为tencent_video-xposed-signed.apk的新apk，这就是重打包之后的支持xposed插件的apk，卸载原腾讯视频app，安装此apk以及插件apk后，就可以实现腾讯视频去广告。

另外:
也可以将此插件打包到腾讯视频apk中，这样，此apk就自带去广告的功能，就不用再安装插件apkl了。命令如下：
```
$ java -jar ../xpatch.jar ../tencent_video.apk -xm ../xposed_plugin.apk
```

## 关注更多内容
欢迎关注个人技术公众号**Android葵花宝典**，获取更多的android逆向知识。  
![](https://upload-images.jianshu.io/upload_images/1639238-ab6e0fceabfffdda.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/180)
## 声明
**本工程的内容仅能用于学习和研究，请勿用于任何商业用途，否则由此带来的法律责任由操作者自己承担，和本人无关。**
