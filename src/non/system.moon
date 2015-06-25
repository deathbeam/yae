-------------------------------------------------------------------------------
-- Provides many usefull methods for managing your current device.
-------------------------------------------------------------------------------
-- @module non.system

Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  get_clipboard: ->
    clipboard = Gdx.app\getClipboard!
    return clipboard\getContents!

  ---
  -- Get current platform. Can return "desktop", "android", "ios" or "unknown"
  -- @treturn string current platform
  -- @usage
  -- platform = non.system.get_os!
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

  ---
  -- Terminate the application
  -- @usage
  -- non.system.quit! if something
  quit: ->
    Gdx.app\exit!
}