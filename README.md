![No Nonsense](https://raw.githubusercontent.com/codeindie/non/master/src/gen/res/loading.png)

[Lua](http://lua.org/) `.lua` | [JavaScript](http://www.ecmascript.org/) `.js`

* **cross-platform** (Windows, Linux, Mac, Android, IOS)
* make games in **JavaScript** and **Lua**
* image/text **rendering** and manipulation
* loading and rendering of **tiled maps**
* audio engine
* keyboard, touch and mouse **input handlers**
* **plugins** (check below example configuration)
* TCP-based **networking**
* **physics** engine
* simple **lights**

## quickstart

```batch
cd Your/Working/Dir
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

    "plugins": [ "Graphics", "Audio" ],
}
```

* `name` - Application title
* `version` - Application version
* `main` - Main script file. Extension of this file also defines what type of scripting engine will be used.
* `plugins` - Array of plugins what will be loaded and used in your project.

## events

* `non.ready` - Initialization logic, loading of game assets. Triggered once, at game load.
* `non.update` - Update logic, handling input and timed events. Triggered every frame.
* `non.draw` - Display logic, handling rendering of loaded assets. Triggered every frame right after `non.update`.
* `non.close` - Triggered on game close, unloading of game assets.
* `non.resize` - Triggered on game window resize
* `non.pause` - Triggered on game pause (mobile-only)
* `non.resume` - Triggered on game resume (mobile-only)