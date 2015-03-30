--[[--------
Provides many usefull methods for getting informations about user device.

@module non.system
]]

non.system = {}

--[[----------
Get current FPS (frames per second)
@treturn number fps
@usage
-- lua -------------------------------------------------------------------------------------
fps = non.system.getFPS()
-- moonscript ------------------------------------------------------------------------------
fps = non.system.getFPS!
]]
function non.system.getFPS()
  return NON.gdx.graphics:getFramesPerSecond()
end
  
--[[----------
Get current delta (time elapsed since last frame)
@treturn number delta
@usage
-- lua -------------------------------------------------------------------------------------
delta = non.system.getDelta()
-- moonscript ------------------------------------------------------------------------------
delta = non.system.getDelta!
]]
function non.system.getDelta()
  return NON.gdx.graphics:getDeltaTime()
end

--[[----------
Obtain preferences instance. Preferences are a simple way to store small data for your application, e.g. user settings, small game state saves and so on. Preferences work like a hash map, using strings as keys, and various primitive types as values.
@tparam string name unique identifier of your preferences
@treturn Preferences preferences
@usage
-- lua -------------------------------------------------------------------------------------
prefs = non.system.getPreferences("My Preferences")
prefs:putBoolean("soundOn", true)
prefs:putInteger("highscore", 10)
prefs:putString("name", "Donald Duck")
name = prefs:getString("name", "No name stored")
prefs:flush()
-- moonscript ------------------------------------------------------------------------------
prefs = non.system.getPreferences "My Preferences"
prefs\putBoolean "soundOn", true
prefs\putInteger "highscore", 10
prefs\putString "name", "Donald Duck"
name = prefs\getString "name", "No name stored"
prefs\flush!
]]
function non.system.getPreferences(name)
  return NON.gdx.graphics:getPreferences(name)
end

--[[----------
Obtain config.yml configuration as map
@treturn Map configuration map
@usage
-- lua -------------------------------------------------------------------------------------
config = non.system.getConfig()
name = config:get("name")
package = config:get("package")
version = config:get("version")
-- moonscript ------------------------------------------------------------------------------
config = non.system.getConfig!
name = config\get "name"
package = config\get "package"
version = config\get "version"
]]
function non.system.getConfig()
  return NON:getConfig()
end

--[[----------
Get contents of device clipboard
@treturn string contents of the clipboard
@usage
-- lua -------------------------------------------------------------------------------------
clipboardContents = non.system.getClipboard()
-- moonscript ------------------------------------------------------------------------------
clipboardContents = non.system.getClipboard!
]]
function non.system.getClipboard()
  return NON.gdx.app:getClipboard():getContents()
end

--[[----------
Set contents of device clipboard
@tparam string text text to set to clipboard
@usage
-- lua -------------------------------------------------------------------------------------
non.system.setClipboard("it is just text")
-- moonscript ------------------------------------------------------------------------------
non.system.setClipboard "it is just text"
]]
function non.system.setClipboard(text)
  NON.gdx.app:getClipboard():setContents(text)
end

--[[----------
Get operating system version (only for Android and iOS)
@treturn number operating system version
@usage
-- lua -------------------------------------------------------------------------------------
osVersion = non.system.getVersion()
-- moonscript ------------------------------------------------------------------------------
osVersion = non.system.getVersion!
]]
function non.system.getVersion()
  return NON.gdx.app:getVersion()
end

--[[----------
Get current platform. Can return "desktop", "android", "ios" or "unknown"
@treturn string current platform
@usage
-- lua -------------------------------------------------------------------------------------
platform = non.system.getPlatform()
-- moonscript ------------------------------------------------------------------------------
platform = non.system.getPlatform!
]]
function non.system.getPlatform()
  return NON:getPlatform()
end

--[[----------
Get device width (in pixels)
@treturn number width of the device
@usage
-- lua -------------------------------------------------------------------------------------
width = non.system.getWidth()
-- moonscript ------------------------------------------------------------------------------
width = non.system.getWidth!
]]
function non.system.getWidth()
  return NON.gdx.graphics:getWidth()
end

--[[----------
Get device height (in pixels)
@treturn number height of the device
@usage
-- lua -------------------------------------------------------------------------------------
height = non.system.getHeight()
-- moonscript ------------------------------------------------------------------------------
height = non.system.getHeight!
]]
function non.system.getHeight()
  return NON.gdx.graphics:getHeight()
end

--[[----------
Get device size (in pixels)
@treturn table width and height of the device
@usage
-- lua -------------------------------------------------------------------------------------
width, height = non.system.getSize()
-- moonscript ------------------------------------------------------------------------------
width, height = non.system.getSize!
]]
function non.system.getSize()
  return non.system.getWidth(), non.system.getHeight()
end

--[[----------
Log text to console using "log" level
@tparam string tag message tag
@tparam string msg your message
@usage
-- lua -------------------------------------------------------------------------------------
non.system.log("mytag", "my informative message")
-- moonscript ------------------------------------------------------------------------------
non.system.log "mytag", "my informative message"
]]
function non.system.log(tag, msg)
  NON.gdx.app:log(tag, msg)
end

--[[----------
Log text to console using "debug" level
@tparam string tag message tag
@tparam string msg your message
@usage
-- lua -------------------------------------------------------------------------------------
non.system.debug("mytag", "my debug message")
-- moonscript ------------------------------------------------------------------------------
non.system.debug "mytag", "my debug message"
]]
function non.system.debug(tag, msg)
  NON.gdx.app:debug(tag, msg)
end

--[[----------
Log text to console using "error" level
@tparam string tag message tag
@tparam string msg your message
@usage
-- lua -------------------------------------------------------------------------------------
non.system.error("mytag", "my error message")
-- moonscript ------------------------------------------------------------------------------
non.system.error "mytag", "my error message"
]]
function non.system.error(tag, msg)
  NON.gdx.app:error(tag, msg)
end

--[[----------
Terminate the application
@usage
-- lua -------------------------------------------------------------------------------------
if something then
  non.system.quit()
end
-- moonscript ------------------------------------------------------------------------------
non.system.quit! if something
]]
function non.system.quit()
  NON.gdx.app:exit()
end