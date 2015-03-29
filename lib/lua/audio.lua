--[[--------
Handles creating, playing and manipulating sound and music instances.

Sound effects are small audio samples, usually no longer than a few seconds, that are played back on specific game events such as a character jumping or shooting a gun.

Sound effects can be stored in various formats. NÖN supports MP3, OGG and WAV files.
iOS currently does not support OGG files.

***note:*** On Android, a Sound instance can not be over 1mb in size.

For any sound that's longer than a few seconds it is preferable to stream it from disk instead of fully loading it into RAM. NÖN provides a Music interface that lets you do that.

@module non.audio
]]

non.audio = {}

----------
-- Create a sound instance from specified resource
-- @tparam string path path to sound file
-- @tparam string type type of file path (default is "internal")
-- @treturn Sound a sound instance
-- @see non.files
-- @usage source = non.audio.sound("sound.ogg", "local")
function non.audio.sound(path, type)
  return NON.gdx.audio:newSound(non.files.open(path, type))
end

----------
-- Create a music instance from specified resource
-- @tparam string path path to sound file
-- @tparam string type type of file path (default is "internal")
-- @treturn Music a music instance
-- @see non.files
-- @usage source = non.audio.music("music.mp3", "external")
function non.audio.music(path, type)
  return NON.gdx.audio:newMusic(non.files.open(path, type))
end

----------
-- Play music or sound instance
-- @param source music or sound instance to be played
-- @tparam number volume volume of audio to be played (from 0 to 100), default is 100
-- @usage non.audio.play(source, 80)
function non.audio.play(source, volume)
  if volume == nil then volume = 100 end
  source:setVolume(volume / 100)
  source:play()
end

----------
-- Play and loop music or sound instance
-- @param source music or sound instance to be looped
-- @tparam number volume volume of audio to be played (from 0 to 100), default is 100
-- @usage non.audio.loop(source, 70)
function non.audio.loop(source, volume)
  if volume == nil then volume = 100 end
  source:setVolume(volume / 100)
  source:loop()
end

----------
-- Pause music or sound instance
-- @param source music or sound instance to be paused
-- @usage non.audio.pause(source)
function non.audio.pause(source)
  source:pause()
end

----------
-- Resume paused music or sound instance
-- @param source music or sound instance to be resumed
-- @usage non.audio.resume(source)
function non.audio.resume(source)
  source:resume()
end

----------
-- Stop playing of music or sound instance
-- @param source music or sound instance to be played
-- @usage non.audio.stop(source)
function non.audio.stop(source)
  source:stop()
end