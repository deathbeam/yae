![No Nonsense](https://raw.githubusercontent.com/codeindie/non/master/.non/gen/res/loading.png)

[JavaScript](http://www.ecmascript.org/) `.js` | [CoffeeScript](http://coffeescript.org/) `.coffee` | [TypeScript](http://typescriptlang.org/) `.ts`

* **cross-platform** (Windows, Linux, Mac, Android)
* image/text **rendering** and manipulation
* loading and rendering of **tiled maps**
* audio engine
* keyboard, touch and mouse **input handlers**
* **modules** (check below example configuration)
* TCP-based **networking**
* **physics** engine
* **simple** lights

## quickstart

```batch
cd Your/Working/Dir
git clone git@github.com:codeindie/non.git
non gen js
```

## config `non.cfg`

```json
{
    "name":    "non",
    "version": "1.0.0",
    "main":    "main.js",

    "plugins": [ "Graphics", "Audio" ],

    "desktop": {
        "resolution": [ 800, 600 ],
        "fullscreen": true
    },
    
    "android": {
        "sign": {
            "storeFile": "../keystore", 
            "storePassword": "123456",
            "keyAlias": "non",
            "keyPassword": "123456"
        }
    }
}
```

* `title` - Application title
* `version` - Application version
* `main` - Main script file. Extension of this file also defines what type of scripting engine will be used.

## events

* `non.ready` - Initialization logic, loading of game assets. Triggered once, at game load.
* `non.update` - Update logic, handling input and timed events. Trigered every frame.
* `non.draw` - Display logic, handling rendering of loaded assets. Trigered every frame right after `non.update`.
* `non.close` - Triggered on game close, unloading of game assets.
* `non.resize` - Triggered on game window resize
* `non.pause` - Triggered on game pause (mobile-only)
* `non.resume` - Triggered on game resume (mobile-only)