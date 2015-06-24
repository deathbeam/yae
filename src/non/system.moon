Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  getClipboardText: ->
    clipboard = Gdx.app\getClipboard!
    return clipboard\getContents!

  getOS: ->
    return NonVM.util\getOS!

  getMemoryUse: ->
    return Gdx.app\getJavaHeap!

  openURL: (url) ->
    return Gdx.net\openURI url

  setClipboardText: (text) ->
    clipboard = Gdx.app\getClipboard!
    clipboard\setContents text

  vibrate: (seconds) ->
    Gdx.input\vibrate seconds * 1000

  quit: ->
    Gdx.app\exit!
}