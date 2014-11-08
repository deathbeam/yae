```
   __                     __   
  / /  _ __   ___  _ __   \ \  
 | |  | '_ \ / _ \| '_ \   | | 
< <   | | | | (_) | | | |   > >
 | |  |_| |_|\___/|_| |_|  | | 
  \_\    game framework   /_/  
  
```

## Features

* Super simple image and text rendering and manipulation
* TMX map rendering
* Audio engine (using libGDX again, but with simple usage... again)
* Input engine (not finished yet)
* Modules system (check below example configuration)
* Game packages (supports classic game directory or zipped game data)
* Simple TCP networking (later I will add also support for UDP)

## Supported languages

* JavaScript `.js`
* [CoffeeScript](http://coffeescript.org/) `.coffee`
* [TypeScript](http://www.typescriptlang.org/) `.ts`
* [Lua](http://lua.org/) `.lua`
* [Ruby](https://www.ruby-lang.org) `.rb` (experimental)
* [Python](https://www.python.org/) `.py` (experimental)

## Configuration

Configuration is done by json configuration file `non.cfg`. You can define window title, resolution, main class and add modules through it. Example configuration file:
```json
{
    title: "No Nonsense Game",
    resolution: [800, 600],
    main: "main.js",
    modules: [audio, graphics, keyboard, mouse, touch, math, tiled, network]
}
```

## Main class

Your game main logic should be divided into 3 functions:
* `non.ready` - Initialization logic, loading of game assets
* `non.update` - Update logic, handling input and timed events
* `non.draw` - Display logic, handling rendering of loaded assets

#### Examples

In JavaScript:
```javascript
non.ready = function() {
	// insert initialization logic here
};

non.update = function() {
	// insert game logic here
};

non.draw = function() {
	// insert drawing logic here
};
```

In CoffeeScript:
```coffeescript
non.ready = ->
	# insert initialization logic here

non.update = ->
	# insert game logic here

non.draw = ->
	# insert drawing logic here
```

In TypeScript:
```typescript
non.ready = () => {
	// insert initialization logic here
};

non.update = () => {
	// insert game logic here
};

non.draw = () => {
	// insert drawing logic here
};
```