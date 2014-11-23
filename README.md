![No Nonsense](https://raw.githubusercontent.com/deathbeam/non/master/wrapper/gen/res/loading.png)

non is experimental game framework created for rapid game development and so it is perfect for game jams, but also for serious projects. non can run on almost any platform, including Windows, Mac and Linux. I am working on support for Android, web and IOS.

## Features

* Super simple image and text rendering and manipulation
* TMX map loading and rendering
* Audio engine
* Keyboard, Touch and Mouse input handlers
* Modules system (check below example configuration)
* Simple TCP network manager
* Physics engine based on Box2D
* Simple light engine based on Box2DLights

## Supported languages

* JavaScript `.js`
* [CoffeeScript](http://coffeescript.org/) `.coffee`
* [TypeScript](http://typescriptlang.org/) `.ts`
* [Lua](http://lua.org/) `.lua`
* [Ruby](https://ruby-lang.org) `.rb` (experimental)
* [Python](https://python.org/) `.py` (experimental)
* [Groovy](http://groovy-lang.org/) `.groovy` (experimental)

## Configuration

Configuration is done by json configuration file `non.cfg`. You can define window title, resolution, main class and add modules through it. Example configuration file:
```json
{
    "title": "No Nonsense Game",
    "resolution": [800, 600],
    "main": "main.js",
    "sdk": "Path/To/Your/Android/Sdk",
    "modules": [ 
        "audio",
        "graphics",
        "tiled",
        "network",
        "math",
        "physics",
        "lights",
        "mouse",
        "touch",
        "keyboard"
    ]
}
```

## Main class

Your game logic should be divided into 3 events:
* `non.ready` - Initialization logic, loading of game assets
* `non.update` - Update logic, handling input and timed events
* `non.draw` - Display logic, handling rendering of loaded assets

Additional usefull events
* `non.close` - Triggered on game close, unloading of game assets
* `non.resize` - Triggered on game window resize
