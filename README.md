![No Nonsense](https://raw.githubusercontent.com/deathbeam/non/master/wrapper/gen/res/loading.png)

non is experimental game framework created for rapid game development and so it is perfect for game jams, but also for serious projects. non can run on almost any platform, including Windows, Mac and Linux. I am working on support for Android, web and IOS.

some awesomeness for you (the game developer) includes:

* super simple image and text **rendering** and manipulation
* loading and rendering for **tiled maps**
* audio engine
* keyboard, touch and mouse **input handlers**
* **modules** (check below example configuration)
* TCP **network**ing
* **physics** engine
* **simple** lights

## quickstart

```batch
cd Your/Working/Dir
git clone git@github.com:deathbeam/non.git
non gen
```

## languages

* JavaScript `.js` 
* [CoffeeScript](http://coffeescript.org/) `.coffee`
* [TypeScript](http://typescriptlang.org/) `.ts`
* [Lua](http://lua.org/) `.lua`
* [Ruby](https://ruby-lang.org) `.rb` (experimental)
* [Python](https://python.org/) `.py` (experimental)
* [Groovy](http://groovy-lang.org/) `.groovy` (experimental)

## config

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

## main script

Your game logic should be divided into 3 events:
* `non.ready` - Initialization logic, loading of game assets
* `non.update` - Update logic, handling input and timed events
* `non.draw` - Display logic, handling rendering of loaded assets

Additional usefull events
* `non.close` - Triggered on game close, unloading of game assets
* `non.resize` - Triggered on game window resize
