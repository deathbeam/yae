non.files = {}

local Gdx = NON_GDX
  
function non.files.getExternalPath()
  return Gdx.files:getExternalStoragePath()
end
  
function non.files.getLocalPath()
  return Gdx.files:getLocalStoragePath()
end
  
function non.files.open(path, type)
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
  
function non.files.exists(path, type)
  return non.files.open(path, type):exists()
end
  
function non.files.isDirectory(path, type)
  return non.files.open(path, type):isDirectory()
end
  
function non.files.isFile(path, type)
  return not non.files.isDirectory(path, type)
end
  
function non.files.read(path, type)
  return non.files.open(path, type):readString()
end
  
function non.files.write(path, text, type)
  return non.files.open(path, type):writeString(text, false)
end
  
function non.files.append(path, text, type)
  return non.files.open(path, type):writeString(text, true)
end
  
function non.files.remove(path, type)
  return non.files.open(path, type):delete()
end
  
function non.files.size(path, type)
  return non.files.open(path, type):length()
end
  
function non.files.last_modified(path, type)
  return non.files.open(path, type):lastModified()
end
  
function non.files.list(path, type)
  return non.files.open(path, type):list()
end
  
function non.files.mkdir(path, type)
  return non.files.open(path, type):mkdirs()
end