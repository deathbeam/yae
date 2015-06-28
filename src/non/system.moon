-------------------------------------------------------------------------------
-- Provides many usefull methods for managing your current device.
-------------------------------------------------------------------------------
-- @module non.system

Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  ---
  -- Gets text from the clipboard.
  -- @treturn string The text currently held in the system's clipboard.
  -- @usage
  -- text = non.system.getClipboardText!
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

  ---
  -- Get info about current memory usage
  -- @treturn number memory heap
  -- @usage
  -- heap = non.system.getMemoryInfo!
  getMemoryInfo: ->
    return Gdx.app\getJavaHeap!

  ---
  -- Opens a URL with the user's web or file browser. 
  -- @tparam string url The URL to open. Must be formatted as a proper URL.
  -- @treturn boolean Whether the URL was opened successfully.
  -- @usage
  -- success = non.system.openURL url
  openURL: (url) ->
    return Gdx.net\openURI url

  ---
  -- Puts text in the clipboard. 
  -- @tparam string text The new text to hold in the system's clipboard.
  -- @usage
  -- non.system.setClipboardText text
  setClipboardText: (text) ->
    clipboard = Gdx.app\getClipboard!
    clipboard\setContents text

  ---
  -- Vibrates for the given amount of time.
  -- @tparam number seconds the number of seconds to vibrate
  -- @usage
  -- non.system.vibrate seconds
  vibrate: (seconds) ->
    Gdx.input\vibrate seconds * 1000

  ---
  -- Terminate the application
  -- @usage
  -- non.system.quit! if something
  quit: ->
    Gdx.app\exit!
}