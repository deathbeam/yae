Gdx = java.require "com.badlogic.gdx.Gdx"
Peripheral = java.require("com.badlogic.Gdx.Input.Peripheral")

c = require "non.internal.constants"

{
  is_down: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      keycode = c.keys[args[i]]
      found = found or Gdx.input\isKeyPressed keycode

    return found

  is_visible: ->
    return Gdx.input\isPeripheralAvailable Peripheral.OnscreenKeyboard

  set_visible: (visible) ->
    Gdx.input\setOnscreenKeyboardVisible visible
}