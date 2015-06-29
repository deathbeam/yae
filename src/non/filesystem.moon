-------------------------------------------------------------------------------
-- Allows manipulation with files and directories.
-------------------------------------------------------------------------------
-- NÖN applications run on three different platforms =  
-- * desktop systems (Windows, Linux, Mac OS X)  
-- * Android  
-- * and iOS.  
--   
-- Each of these platforms handles file I/O a little differently.  
--   
-- Each NÖN file has a type which defines where the file is located.
-- The following table illustrates the availability and location of each file
-- type for each platform.  
--   
-- **Classpath**  
-- Classpath files are directly stored in your source folders. These get
-- packaged with your jars and are always *read-only*. They have their purpose,
-- but should be avoided if possible.  
--   
-- **Internal**  
-- Internal files are relative to the application’s *root* or *working*
-- directory on desktops and relative to the *assets* directory on Android.
-- These files are *read-only*. If a file can't be found on the internal storage,
-- the file module falls back to searching the file on the classpath. This file
-- type is default for NÖN files.
--   
-- **Local**  
-- Local files are stored relative to the application's *root* or *working*
-- directory on desktops and relative to the internal (private) storage of the
-- application on Android. Note that Local and internal are mostly the same on the
-- desktop.
--   
-- **External**  
-- External files paths are relative to the
-- [SD card root](http =//developer.android.com/reference/android/os/Environment.html#getExternalStorageDirectory\(\))
-- on Android and to the [home directory](http =//www.roseindia.net/java/beginners/UserHomeExample.shtml)
-- of the current user on desktop systems.
--   
-- **Absolute**  
-- Absolute files need to have their fully qualified paths specified. For the
-- sake of portability, this option must be used only when absolutely necessary.
--
-- @module non.filesystem

File = require "non.objects.File"
Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

append = (filename, text, filetype) ->
  file = newFile filename, filetype
  file\append text
  return true

copy = (from_filename, to_filename, from_filetype, to_filetype) ->
  from_file = newFile from_filename, from_filetype
  to_file = newFile from_filename, from_filetype
  from_file\copy to_file
  return true

createDirectory = (filename, filetype) ->
  file = newFile filename, filetype
  file\create_directory!
  return true

exists = (filename, filetype) ->
  file = newFile filename, filetype
  return file\exists!

getDirectoryItems = (filename, filetype) ->
  file = newFile filename, filetype
  return file\list!

getExternalPath = ->
  return Gdx.files\getExternalStoragePath!

getLocalPath = ->
  return Gdx.files\getLocalStoragePath!

getWorkingPath = ->
  file = newFile(".")\file!
  return file\getAbsolutePath!

getLastModified = (filename, filetype) ->
  file = newFile filename, filetype
  return file\last_modified!

getSize = (filename, filetype) ->
  file = newFile filename, filetype
  return file\size!

newFile = (filename, filetype) ->
  return File filename, filetype

isDirectory = (filename, filetype) ->
  file = newFile filename, filetype
  return file\is_directory!

isFile = (filename, filetype) ->
  file = newFile filename, filetype
  return file\is_file!

load = (filename, filetype) ->
  file = newFile filename, filetype
  return NonVM.lua\load file\reader!, filename

move = (from_filename, to_filename, from_filetype, to_filetype) ->
  from_file = newFile from_filename, from_filetype
  to_file = newFile from_filename, from_filetype
  from_file\move to_file
  return true

read = (filename, filetype) ->
  file = newFile filename, filetype
  return file\read!

remove = (filename, filetype) ->
  file = newFile filename, filetype
  file\remove!
  return true

write = (filename, text, filetype) ->
  file = newFile filename, filetype
  file\write text
  return true

{
  :append
  :copy
  :createDirectory
  :exists
  :getDirectoryItems
  :getExternalPath
  :getLocalPath
  :getWorkingPath
  :getLastModified
  :getSize
  :newFile
  :isDirectory
  :isFile
  :load
  :move
  :read
  :remove
  :write
}