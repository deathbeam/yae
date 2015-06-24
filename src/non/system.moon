Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  get_clipboard: ->
    clipboard = Gdx.app\getClipboard!
    return clipboard\getContents!

  get_os: ->
    return NonVM.util\getOS!

  get_heap: ->
    return Gdx.app\getJavaHeap!

  open_url: (url) ->
    return Gdx.net\openURI url

  set_clipboard: (text) ->
    clipboard = Gdx.app\getClipboard!
    clipboard\setContents text

  vibrate: (seconds) ->
    Gdx.input\vibrate seconds * 1000

  quit: ->
    Gdx.app\exit!
}