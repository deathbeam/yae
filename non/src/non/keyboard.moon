c = require("non.internal.constants")
gdx = require("non.internal.common").gdx
Peripheral = java.require("com.badlogic.gdx.Input.Peripheral")

{
  isDown: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      keycode = c.keys[args[i]]
      found = found or gdx.input\isKeyPressed keycode

    return found

  isVisible: ->
    return gdx.input\isPeripheralAvailable Peripheral.OnscreenKeyboard

  setVisible: (visible) ->
    gdx.input\setOnscreenKeyboardVisible visible
}