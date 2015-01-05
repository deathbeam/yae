![Non Logo](https://raw.githubusercontent.com/codeindie/non/master/src/gen/res/loading.png)

Non is a cross-platform game and visualization development engine. It currently supports Windows, Linux, Mac OS X, Android and iOS as target platforms.

Non allows you to write your code once and deploy it to multiple platforms without modification. Instead of waiting for your latest modifications to be deployed to your device, you can benefit from an extremely fast iteration cycle by coding your application mainly in a desktop environment. You can choose your favorite scripting language from supported ones to be as productive as you can be.

Non lets you go as low-level as you want, using our plugin system you can use full power of Java and Libgdx, giving you direct access to file systems, input devices, audio devices and OpenGL. Also, Non don't force you to use his pre-built features, as they are all simply plugins, so you can replace, change or remove them without any errors.

Non is something between engine and framework, as it looks like library but Non provides it's own CLI and packager. Only thing required when using Non is to have operating system and Java installed. Non will take care of rest. Isn't that awesome?

### Features ###

* **cross-platform** (Windows, Linux, Mac, Android, IOS)
* both **JavaScript** and **Lua** language support
* rendering engine with support for blending, matrices, shaders, image batching, text rendering...
* audio engine with support for most music and sound formats
* keyboard, touch, accelerometer and mouse support
* fast and stable **networking** engine
* physics and lights engine based on Box2D
* **extensibility** (plugins and custom languages)

### Quickstart ###

```shell
git clone git@github.com:codeindie/non.git
cd non
javac src/launcher/Main.java
jar cf non.jar -C src .
non gen:hello
non desktop:update
non desktop:dist
```

### Downloads
You can get Non from the [IndieDB](http://indiedb.com/engines/no-nonsense) or here from GitHub [releases](http://github.com/codeindie/non/releases) page.

### Documentation ###

The [Wiki](https://github.com/codeindie/non/wiki) contains all the information you'll need to write a 
Non game. You can contribute to the Wiki directly here on GitHub!

### Reporting Issues ###

Use the [issue tracker](https://github.com/codeindie/non/issues) here on GitHub to report issues.