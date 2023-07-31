

##### **Shadow**(重点)

原理可以查看码牛b站

以上插件化方案需要反射+hook，需要做版本兼容，比较麻烦，所以shadow现在唯一的插件化方案

![image-20230722110546139](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20230722110546139.png)

| module称号                   | module编译产品    | 终究产品方式      | 是否动态加载 | 代码运转所在进程     | 首要职责                                             |
| ---------------------------- | ----------------- | ----------------- | ------------ | -------------------- | ---------------------------------------------------- |
| sample-host                  | 可独立运转的apk   | 可独立运转的apk   | 否           | 主进程和插件进程均有 | 是对外发布的app                                      |
| sample-manager               | pluginmanager.apk | pluginmanager.apk | 是           | 主进程               | 装置、办理及加载插件                                 |
| sample-plugin/sample-app     | app-plugin.apk    | plugin.zip        | 是           | 插件进程             | 事务逻辑                                             |
| sample-plugin/sample-base    | base-plugin.apk   | plugin.zip        | 是           | 插件进程             | 事务逻辑，被app以compileOnly的办法依靠               |
| sample-plugin/sample-loader  | loader.apk        | plugin.zip        | 是           | 插件进程             | 插件的加载                                           |
| sample-plugin/sample-runtime | runtime.apk       | plugin.zip        | 是           | 插件进程             | 插件运转时的署理组件，如container activity（见下文） |

**资源 ID 抵触问题**

那么下一个问题，便是插件中必定也会有对资源的拜访。一般状况下，资源拜访会是相似下面的这样的方式：

```c
textView.setText(R.string.main_activity_info);
```

咱们对资源的拜访经过一个int值，而这个值是在apk的打包期间，由脚本生成的。这个值与详细的资源之间存在一一对应的关系。

由于插件和宿主工程是独立编译的，假如不修正分区，两者的资源或许存在抵触，这个时分就不知道应该去哪里加载资源了。

为了处理这个问题，Shadow修正了插件资源的id的分区。修正资源id并不杂乱，只需求一行代码就能够处理：

```arduino
additionalParameters "--package-id", "0x7E", "--allow-reserved-package-id"
```

反编译打包完结的apk，也很容易就能够发现，同一个资源的分区是不同的。宿主工程的是7f开头，而插件则是7e。



**Replugin的思路：**

Hack宿主的ClassLoader，使得体系收到加载ContainerActivity的恳求时，回来的是PluginActivity类。

由于PluginActivity本质上也是一个承继了android.app.Activity的类，经过向上转型为activity去运用，理论上不会存在什么问题。

Replugin的这个计划的问题之一，是需求在宿主apk中，为每一个插件的事务Activity注册一个对应的坑位Activity、。关于这点，咱们先看下ClassLoader load办法的签名：

```arduino
public abstract class ClassLoader {
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        ......
    }
}
```

能够看到，ClassLoader在loadClass的时分，收到的参数只要一个类名。这就导致，关于每个事务插件中的Activity，都需求一个ContainerActivity与之对应。在宿主apk中，咱们需求注册许多的坑位Activity。

别的，Replugin hack了加载class的进程，后边也不得不持续用Hack手段处理体系看到了未装置的Activity的问题。比如体系为插件Activity[初始化](https://www.6hu.cc/archives/tag/初始化)的Context是以宿主的apk初始化的，插件结构就不得不再去Hack修复。

**Shadow的思路**

Shadow则运用了另一种思路。已然对体系而言，ContainerActivity是一个实在注册过的存在的activity，那么就让这个activity发动起来。

一同，让ContainerActivity持有PluginActivity的实例。ContainerActivity将自己的各类办法，顺次转发给PluginActivity去完结，如onCreate等生命周期的办法。

Shadow在这儿所采用的计划，本质上是一种署理的思路。在这种思路中，事实上，PluginActivity并不需求实在承继Activity，它只需求承继一个与Activity有着相似的办法的接口就能够了。

Shadow的这个思路，一个ContainerActivity能够对应多个PluginActivity，咱们只需求在宿主中注册有限个有必要的activity即可。

而且，后续插件假如想要新增一个activity，也不是有必要要修正宿主工程。只要事务上答应，完全能够复用已有的ContainerActivity。



**Shadow是如何加载插件中的dex的**

```scss
new DexClassLoader(apkFile.getAbsolutePath(), oDexDir.getAbsolutePath(), null, ODexBloc.class.getClassLoader());
```

**Shadow是如何加载资源包的**

```
val packageManager = hostAppContext.packageManager
        packageArchiveInfo.applicationInfo.publicSourceDir = archiveFilePath
        packageArchiveInfo.applicationInfo.sourceDir = archiveFilePath
        packageArchiveInfo.applicationInfo.sharedLibraryFiles = hostAppContext.applicationInfo.sharedLibraryFiles
        try {
            return packageManager.getResourcesForApplication(packageArchiveInfo.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
```

也是通过正常的packageManager生成的。但是是生成一个新的Resources，区别于宿主中正常使用的Resources。由于插件和宿主中是不通的Resources，所以不会出现资源ID冲突的问题。



参考文章

https://www.6hu.cc/archives/181080.html













