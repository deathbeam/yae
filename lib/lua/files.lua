--[[--------
Allows manipulation with files and directories.

NÖN applications run on three different platforms: desktop systems (Windows, Linux, Mac OS X), Android, and iOS. Each of these platforms handles file I/O a little differently.

Each NÖN file has a type which defines where the file is located. The following table illustrates the availability and location of each file type for each platform.

  *  **classpath** - Classpath files are directly stored in your source folders. These get packaged with your jars and are always *read-only*. They have their purpose, but should be avoided if possible.
  * **internal** - Internal files are relative to the application’s *root* or *working* directory on desktops and relative to the *assets* directory on Android. These files are *read-only*. If a file can't be found on the internal storage, the file module falls back to searching the file on the classpath. This file type is default for NÖN files.
  * **local** - Local files are stored relative to the application's *root* or *working* directory on desktops and relative to the internal (private) storage of the application on Android. Note that Local and internal are mostly the same on the desktop.
  * **external** - External files paths are relative to the [SD card root](http://developer.android.com/reference/android/os/Environment.html#getExternalStorageDirectory\(\)) on Android and to the [home directory](http://www.roseindia.net/java/beginners/UserHomeExample.shtml) of the current user on desktop systems.
  * **absolute** - Absolute files need to have their fully qualified paths specified. For the sake of portability, this option must be used only when absolutely necessary.

@module non.files
]]

non.files = {}

--[[----------
Get external storage path
@treturn string absolute path to external storage location
@usage
-- lua -------------------------------------------------------------------------------------
externalPath = non.files.getExternalPath()
-- moonscript ------------------------------------------------------------------------------
externalPath = non.files.getExternalPath!
]]
function non.files.getExternalPath()
  return NON.gdx.files:getExternalStoragePath()
end

--[[----------
Get local storage path
@treturn string absolute path to local storage location
@usage
-- lua -------------------------------------------------------------------------------------
localPath = non.files.getLocalPath()
-- moonscript ------------------------------------------------------------------------------
localPath = non.files.getLocalPath!
]]
function non.files.getLocalPath()
  return NON.gdx.files:getLocalStoragePath()
end

--[[----------
Opens file as object
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn FileHandle a file instance
@usage
-- lua -------------------------------------------------------------------------------------
file = non.files.open("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
file = non.files.open "myfile.txt", "local"
]]
function non.files.open(path, type)
  if type == nil then type = "internal" end
  
  if type == "internal" then
    return NON.gdx.files:internal(path)
  elseif type == "local" then
    return NON.localFile(path)
  elseif type == "external" then
    return NON.gdx.files:external(path)
  elseif type == "classpath" then
    return NON.gdx.files:classpath(path)
  elseif type == "absolute" then
    return NON.gdx.files:absolute(path)
  end
  
  return NON.gdx.files.internal(path)
end

--[[----------
Check if file exists
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn boolean true if file exists
@usage
-- lua -------------------------------------------------------------------------------------
exists = non.files.exists("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
exists = non.files.exists "myfile.txt", "local"
]]
function non.files.exists(path, type)
  return non.files.open(path, type):exists()
end

--[[----------
Check if file is directory
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn boolean true if file is directory
@usage
-- lua -------------------------------------------------------------------------------------
isDirectory = non.files.isDirectory("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
isDirectory = non.files.isDirectory "myfile.txt", "local"
]]
function non.files.isDirectory(path, type)
  return non.files.open(path, type):isDirectory()
end

--[[----------
Check if file is not directory
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn boolean true if file is not directory
@usage
-- lua -------------------------------------------------------------------------------------
isFile = non.files.isFile("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
isFile = non.files.isFile "myfile.txt", "local"
]]
function non.files.isFile(path, type)
  return not non.files.isDirectory(path, type)
end

--[[----------
Read contents of file
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn string contents of file
@usage
-- lua -------------------------------------------------------------------------------------
fileContents = non.files.read("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
fileContents = non.files.read "myfile.txt", "local"
]]
function non.files.read(path, type)
  return non.files.open(path, type):readString()
end

--[[----------
Write text to file
@tparam string path path to file
@tparam string text text to write
@tparam string type type of file path (default is "internal")
@treturn boolean true if writing was succesfull
@usage
-- lua -------------------------------------------------------------------------------------
non.files.write("myfile.txt", "I just wrote t", "local")
-- moonscript ------------------------------------------------------------------------------
non.files.write "myfile.txt", "I just wrote t", "local"
]]
function non.files.write(path, text, type)
  return non.files.open(path, type):writeString(text, false)
end

--[[----------
Append text to file
@tparam string path path to file
@tparam string text text to append
@tparam string type type of file path (default is "internal")
@treturn boolean true if file exists
@usage
-- lua -------------------------------------------------------------------------------------
non.files.append("myfile.txt", "ext here", "local")
-- moonscript ------------------------------------------------------------------------------
non.files.append "myfile.txt", "ext here", "local"
]]
function non.files.append(path, text, type)
  return non.files.open(path, type):writeString(text, true)
end

--[[----------
Get size of file
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn number file size in bytes
@usage
-- lua -------------------------------------------------------------------------------------
size = non.files.size("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
size = non.files.size "myfile.txt", "local"
]]
function non.files.size(path, type)
  return non.files.open(path, type):length()
end

--[[----------
Get date and time when file was last modified
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn string when file was last modified
@usage
-- lua -------------------------------------------------------------------------------------
lastModified = non.files.lastModified("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
lastModified = non.files.lastModified "myfile.txt", "local"
]]
function non.files.lastModified(path, type)
  return non.files.open(path, type):lastModified()
end

--[[----------
Make directories to specified path
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn boolean true if operation was succesfull
@usage
-- lua -------------------------------------------------------------------------------------
success = non.files.mkdir("mydirectory", "external")
-- moonscript ------------------------------------------------------------------------------
success = non.files.mkdir "mydirectory", "external"
]]
function non.files.mkdir(path, type)
  return non.files.open(path, type):mkdirs()
end

--[[----------
List contents of directory
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn table list of directories
@usage
-- lua -------------------------------------------------------------------------------------
files = non.files.list("mydirectory", "external")

for file in files do
  file:delete()
end
-- moonscript ------------------------------------------------------------------------------
files = non.files.list "mydirectory", "external"

for file in files
  file\delete!
]]
function non.files.list(path, type)
  return non.files.open(path, type):list()
end

--[[----------
Rename file
@tparam string from path to file
@tparam string to new name of file
@tparam string type type of file path (default is "internal")
@treturn boolean true if operation was succesfull
@usage
-- lua -------------------------------------------------------------------------------------
success = non.files.rename("myfile.txt", "thisismyfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
success = non.files.rename "myfile.txt", "thisismyfile.txt", "local"
]]
function non.files.rename(from, to, type)
  return non.files.open(from, type):rename(to)
end

--[[----------
Copy file to specified location
@tparam string from path to source file
@tparam string to path to destination file
@tparam string type1 type of from file path (default is "internal")
@tparam string type2 type of to file path (default is "internal")
@treturn boolean true if operation was succesfull
@usage
-- lua -------------------------------------------------------------------------------------
success = non.files.copy("thisismyfile.txt", "myexternalfile.txt", "local", "external")
-- moonscript ------------------------------------------------------------------------------
success = non.files.copy "thisismyfile.txt", "myexternalfile.txt", "local", "external"
]]
function non.files.copy(from, to, type1, type2)
  return non.files.open(from, type1):copyTo(non.files.open(to, type2))
end

--[[----------
Move file to specified location
@tparam string from path to source file
@tparam string to path to destination file
@tparam string type1 type of from file path (default is "internal")
@tparam string type2 type of to file path (default is "internal")
@treturn boolean true if operation was succesfull
@usage
-- lua -------------------------------------------------------------------------------------
success = non.files.move("myexternalfile.txt", "mydirectory", "external", "external")
-- moonscript ------------------------------------------------------------------------------
success = non.files.move "myexternalfile.txt", "mydirectory", "external", "external"
]]
function non.files.move(from, to, type1, type2)
  return non.files.open(from, type1):moveTo(non.files.open(to, type2))
end

--[[----------
Get parent directory of specified file
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn FileHandle a parent file instance
@usage
-- lua -------------------------------------------------------------------------------------
parentFile = non.files.parent("mydirectory/myexternalfile.txt", "external")
-- moonscript ------------------------------------------------------------------------------
parentFile = non.files.parent "mydirectory/myexternalfile.txt", "external"
]]
function non.files.parent(path, type)
  return non.files.open(path, type):parent()
end

--[[----------
Get child file from specified directory
@tparam string path path to file
@tparam string child file to get from directory
@tparam string type type of file path (default is "internal")
@treturn FileHandle a child file instance
@usage
-- lua -------------------------------------------------------------------------------------
childFile = non.files.child("mydirectory", "myexternalfile.txt", "external")
-- moonscript ------------------------------------------------------------------------------
childFile = non.files.child "mydirectory", "myexternalfile.txt", "external"
]]
function non.files.child(path, child, type)
  return non.files.open(path, type):child(child)
end

--[[----------
Delete file
@tparam string path path to file
@tparam string type type of file path (default is "internal")
@treturn boolean true if file was deleted
@usage
-- lua -------------------------------------------------------------------------------------
success = non.files.remove("myfile.txt", "local")
-- moonscript ------------------------------------------------------------------------------
success = non.files.remove "myfile.txt", "local"
]]
function non.files.remove(path, type)
  return non.files.open(path, type):delete()
end