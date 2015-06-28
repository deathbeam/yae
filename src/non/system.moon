-------------------------------------------------------------------------------
-- Provides many usefull methods for managing your current device.
-------------------------------------------------------------------------------
-- @module non.system

Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  getClipboardText: ->
    clipboard = Gdx.app\getClipboard!
    return clipboard\getContents!

  ---
  -- Get current platform. Can return "desktop", "android", "ios" or "unknown"
  -- @treturn string current platform
  -- @usage
  -- platform = non.system.getOS!
  getOS: ->
    return NonVM.util\getOS!

  getMemoryInfo: ->
    return Gdx.app\getJavaHeap!

  openURL: (url) ->
    return Gdx.net\openURI url

  setClipboardText: (text) ->
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