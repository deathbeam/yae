-------------------------------------------------------------------------------
-- Handles creating, playing and manipulating sound and music instances.
-------------------------------------------------------------------------------
-- Sound effects are small audio samples, usually no longer than a few seconds,
-- that are played back on specific game events such as a character jumping or
-- shooting a gun.  
-- For any sound that's longer than a few seconds it is preferable to stream it
-- from disk instead of fully loading it into RAM. NÖN provides a "stream"
-- source that lets you do that.  
--   
-- NÖN supports MP3, OGG and WAV files.  
-- ***note1:*** iOS currently does not support OGG files.  
-- ***note2:*** On Android, a static source cannot be over 1 MB
--
-- @module non.audio

Source = require "non.objects.Source"

sources = {}
globalvolume = 1

getSourceCount = ->
  #sources

getVolume = ->
  globalvolume

newSource = (filename, audiotype, filetype) ->
  source = Source filename, audiotype, filetype
  table.insert sources, source
  return source

pause = (source) ->
  source\pause!

play = (source) ->
  source\setVolume globalvolume
  source\play!

resume = (source) ->
  source\resume!

setVolume = (volume) ->
  globalvolume = volume

  for i, source in ipairs sources
    source\setVolume globalvolume

stop = (source) ->
  source\stop!

stopAll = ->
  for i, source in ipairs sources
    source\stop!
    source\free!

{
  :getNumSources
  :getVolume
  :newSource
  :pause
  :play
  :resume
  :setVolume
  :stop
  :stopAll
}
