![Non Logo](https://raw.githubusercontent.com/codeindie/non/master/src/gen/res/loading.png)

Non is a cross-platform game and visualization development engine. It currently supports Windows, Linux, Mac OS X, Android and iOS as target platforms. 

Non allows you to write your code once and deploy it to multiple platforms without modification. Instead of waiting for your latest modifications to be deployed to your device, you can benefit from an extremely fast iteration cycle by coding your application mainly in a desktop environment. You can choose your favorite scripting language from supported ones to be as productive as you can be.

Non lets you go as low-level as you want, using our plugin system you can use full power of Java and Libgdx, giving you direct access to file systems, input devices, audio devices and OpenGL. Also, Non don't force you to use his pre-built features, as they are all simply plugins, so you can replace, change or remove them without any errors.

Non is something between engine and framework, as it looks like library but Non provides it's own CLI and packager. Only thing required when using Non is to have operating system and Java installed. Non will take care of rest. Isn't that awesome?

## features

* **cross-platform** (Windows, Linux, Mac, Android, IOS)
* both **JavaScript** and **Lua** language support
* rendering engine with support for blending, matrices, shaders, image batching, text rendering...
* audio engine with support for most music and sound formats
* keyboard, touch, accelerometer and mouse support
* fast and stable **networking** engine
* physics and lights engine based on Box2D

## quickstart

```shell
git clone git@github.com:codeindie/non.git
javac src/launcher/Main.java
jar cf non.jar -C src .
non gen:javascript
non desktop:update
non desktop:run
```

## config `non.cfg`

```json
{
    "name":    "non",
    "version": "1.0.0",
    "main":    "main.js",

    "plugins": [ 
        "Audio",
        "Graphics",
        "Input"
    ],
    
    "libraries": [
        "org.mozilla:rhino:1.7R4",
        "org.luaj:luaj-jse:3.0"
    ]
}
```

* `name`      - Application title.
* `version`   - Application version.
* `main`      - Main script file. Also defines what scripting language will be used.
* `plugins`   - Array of plugins what will be loaded and used in your project.
* `libraries` - Gradke dependencies, can be used when making plugins.

## license

```
The MIT License (MIT)

Copyright (c) 2014 Thomas Slusny

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```