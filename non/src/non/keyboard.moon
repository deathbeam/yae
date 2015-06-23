c = require("non.internal.constants")
Gdx = java.require "com.badlogic.gdx.Gdx"
Peripheral = java.require("com.badlogic.Gdx.Input.Peripheral")

{
  isDown: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      keycode = c.keys[args[i]]
      found = found or Gdx.input\isKeyPressed keycode

    return found

  isVisible: ->
    return Gdx.input\isPeripheralAvailable Peripheral.OnscreenKeyboard

  setVisible: (visible) ->
    Gdx.input\setOnscreenKeyboardVisible visible
}