NON.files = {}

local Gdx = NON_GDX
  
function NON.files.getExternalPath()
  return Gdx.files:getExternalStoragePath()
end
  
function NON.files.getLocalPath()
  return Gdx.files:getLocalStoragePath()
end
  
function NON.files.open(path, type)
  if type == nil then type = "internal" end
  
  if type == "internal" then
    return Gdx.files:internal(path)
  elseif type == "local" then
    return NON_NON:lcal(path)
  elseif type == "external" then
    return Gdx.files:external(path)
  elseif type == "classpath" then
    return Gdx.files:classpath(path)
  elseif type == "absolute" then
    return Gdx.files:absolute(path)
  end
  
  return Gdx.files.internal(path)
end
  
function NON.files.exists(path, type)
  return NON.files.open(path, type):exists()
end
  
function NON.files.isDirectory(path, type)
  return NON.files.open(path, type):isDirectory()
end
  
function NON.files.isFile(path, type)
  return not NON.files.isDirectory(path, type)
end
  
function NON.files.read(path, type)
  return NON.files.open(path, type):readString()
end
  
function NON.files.write(path, text, type)
  return NON.files.open(path, type):writeString(text, false)
end
  
function NON.files.append(path, text, type)
  return NON.files.open(path, type):writeString(text, true)
end
  
function NON.files.remove(path, type)
  return NON.files.open(path, type):delete()
end
  
function NON.files.size(path, type)
  return NON.files.open(path, type):length()
end
  
function NON.files.last_modified(path, type)
  return NON.files.open(path, type):lastModified()
end
  
function NON.files.list(path, type)
  return NON.files.open(path, type):list()
end
  
function NON.files.mkdir(path, type)
  return NON.files.open(path, type):mkdirs()
end