c = require("non.internal.common")
gdx = c.gdx
helpers = c.helpers

{
  getClipboardText: ->
    clipboard = gdx.app\getClipboard!
    return clipboard\getContents!

  getOS: ->
    return helpers\getOS!

  getMemoryUse: ->
    return gdx.app\getJavaHeap!

  openURL: (url) ->
    return gdx.net\openURI url

  setClipboardText: (text) ->
    clipboard = gdx.app\getClipboard!
    clipboard\setContents text

  vibrate: (seconds) ->
    gdx.input\vibrate seconds * 1000

  quit: ->
    gdx.app\exit!
}