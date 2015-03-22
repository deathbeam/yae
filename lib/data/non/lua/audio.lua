NON.audio = {}

local Gdx = NON_GDX
  
function NON.audio.sound(path, type)
  return Gdx.audio:newSound(NON.files.open(path, type))
end
  
function NON.audio.music(path, type)
  return Gdx.audio:newMusic(NON.files.open(path, type))
end
  
function NON.audio.play(source)
  source:play()
end
  
function NON.audio.pause(source)
  source:pause()
end
  
function NON.audio.resume(source)
  source:resume()
end
  
function NON.audio.stop(source)
  source:stop()
end
  
function NON.audio.loop(source)
  source:loop()
end