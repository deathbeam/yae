![No Nonsense](https://raw.githubusercontent.com/deathbeam/non/master/wrapper/gen/res/loading.png)

[JavaScript](http://www.ecmascript.org/) `.js` | [CoffeeScript](http://coffeescript.org/) `.coffee` | [TypeScript](http://typescriptlang.org/) `.ts` | [Lua](http://lua.org/) `.lua` | [Ruby](https://ruby-lang.org) `.rb` | [Python](https://python.org/) `.py` | [Groovy](http://groovy-lang.org/) `.groovy`

* image and text **rendering** and manipulation
* loading and rendering for **tiled maps**
* audio engine
* keyboard, touch and mouse **input handlers**
* **modules** (check below example configuration)
* TCP-based **networking**
* **physics** engine
* **simple** lights

## quickstart

```batch
cd Your/Working/Dir
git clone git@github.com:deathbeam/non.git
non gen
```

## config `non.cfg`

```json
{
    "title": "No Nonsense Game",
    "resolution": [800, 600],
    "main": "main.js",
    "sdk": "Path/To/Your/Android/Sdk"
}
```

* `title` - Application title
* `resolution` - Resolution (desktop and html only)
* `main` - Main script file. Extension of this file also defines what type of scripting engine will be used.
* `sdk` - Path to your Android SDK

## events

* `non.ready` - Initialization logic, loading of game assets. Triggered once, at game load.
* `non.update` - Update logic, handling input and timed events. Trigered every frame.
* `non.draw` - Display logic, handling rendering of loaded assets. Trigered every frame right after `non.update`.
* `non.close` - Triggered on game close, unloading of game assets.
* `non.resize` - Triggered on game window resize
* `non.pause` - Triggered on game pause (mobile-only)
* `non.resume` - Triggered on game resume (mobile-only)
