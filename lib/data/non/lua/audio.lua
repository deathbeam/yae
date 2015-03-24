non.audio = {}

local Gdx = NON_GDX
  
function non.audio.sound(path, type)
  return Gdx.audio:newSound(non.files.open(path, type))
end
  
function non.audio.music(path, type)
  return Gdx.audio:newMusic(non.files.open(path, type))
end
  
function non.audio.play(source)
  source:play()
end
  
function non.audio.pause(source)
  source:pause()
end
  
function non.audio.resume(source)
  source:resume()
end
  
function non.audio.stop(source)
  source:stop()
end
  
function non.audio.loop(source)
  source:loop()
end