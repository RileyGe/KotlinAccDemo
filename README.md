# KotlinAccDemo
自己有一个Java版的Android项目，想将其主体内容改为Kotlin版的，但在更改过程中遇到了一个访问权限的问题。在我具体问题如下：

# 背景

我在自己的项目中用了一个[junkchen/BleLib](https://github.com/junkchen/BleLib)库。库里面写了很多**包内部**的接口。今天要讨论的是他的一个接口BleListener.OnDataAvailableListener。其定义为：[BleLib/BleListener.java at master · junkchen/BleLib](https://github.com/junkchen/BleLib/blob/master/blelib/src/main/java/com/junkchen/blelib/BleListener.java)。从代码中可以看出他的访问权限为包内访问。

包里面有一个类叫MultipleBleService(访问权限：公开)他实现了BleListener这个接口。[BleLib/MultipleBleService.java at master · junkchen/BleLib](https://github.com/junkchen/BleLib/blob/master/blelib/src/main/java/com/junkchen/blelib/MultipleBleService.java)，在源码中请注意他的设置监听的函数(源码中第756行)：
```
public void setOnDataAvailableListener(OnDataAvailableListener l) {
    mOnDataAvailableListener = l;
}
```
问题就出在这里。

# Java中的调用方法

我建立一个Java的Demo项目，我的源码的[javaaccdemo](https://github.com/RileyGe/KotlinAccDemo/tree/master/javaaccdemo)文件夹中。在Java中调用在文件[KotlinAccDemo/MainActivity.java at master](https://github.com/RileyGe/KotlinAccDemo/blob/master/javaaccdemo/src/main/java/tson/com/javaaccdemo/MainActivity.java)第43行。可以正常调用。

*此Demo工程没有做任何事情，只是一个Demo* 

# Kotlin中调用方法

我也建立了一个Kotlin的测试项目，调用在文件[KotlinAccDemo/MainActivity.kt at master](https://github.com/RileyGe/KotlinAccDemo/blob/master/app/src/main/java/tson/com/accdemo/MainActivity.kt)中的第45行。

**在Kotlin中要求setOnDataAvailableListener的参数必须是BleListener.OnDataAvailableListener，如果参数类型为MultipleBleService.OnDataAvailableListener 由提示参数类型不符**，但在包外BleListener.OnDataAvailableListener不可见，所以导致在Kotlin中MultipleBleService的setOnDataAvailableListener方法不可用。
 
# 想要解决的问题

现在想要解决的问题是，在不更改包[junkchen/BleLib](https://github.com/junkchen/BleLib)的前提下，可以只用Kotlin可以调用MultipleBleService的setOnDataAvailableListener方法吗？

