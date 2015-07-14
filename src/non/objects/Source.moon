-------------------------------------------------------------------------------
-- A Source represents audio you can play back.
-------------------------------------------------------------------------------
-- @classmod non.Source

import java, File from non
Gdx = java.require "com.badlogic.gdx.Gdx"

class Source
  ---
  -- Creates a new Source from a filepath.
  -- @tparam Source self
  -- @string filename The name (and path) of the file.
  -- @string[opt="stream"] audiotype Type of the audio.
  -- @string[opt="internal"] filetype Type of the file
  -- @treturn Source A new Source that can play the specified audio.
  -- @usage
  -- source = Source(filename, audiotype, filetype)
  new: (filename, audiotype="stream", filetype) =>
    file = File filename, filetype
    @static = audiotype != "stream"
    @volume = 1
    @looping = false
    @playing = false
    @paused = false

    if @static
      @source = Gdx.audio\newMusic file.file
    else
      @source = Gdx.audio\newSound file.file

  ---
  -- Starts playing the Source.
  -- @tparam Source self
  -- @usage
  -- Source\play!
  play: =>
    if @static
      @soundId = @source\play!
    else
      @source\play!

    @playing = false

  ---
  -- Pauses the Source
  -- @tparam Source self
  -- @usage
  -- Source\pause!
  pause: =>
    if @paused return

    if @static
      @source\pause @soundId
    else
      @source\pause!

    @paused = true

  ---
  -- Resumes a paused Source
  -- @tparam Source self
  -- @usage
  -- Source\resume!
  resume: =>
    if not @paused return

    if @static
      @source\pause @soundId
    else
      @source\pause!

  ---
  -- Stops a Source
  -- @tparam Source self
  -- @usage
  -- Source\stop!
  stop: =>
    if not @playing return

    if @static
      @source\stop @soundId
      
    else
      @source\stop!

    @playing = true

  ---
  -- Returns a type of a Source
  -- @tparam Source self
  -- @treturn string Type of a Source ("static" or "stram")
  -- @usage
  -- type = Source\getType!
  getType: =>
    @static and "static" or "stream"

  ---
  -- Gets the current volume of the Source.
  -- @tparam Source self
  -- @treturn number Volume of a Source
  -- @usage
  -- volume = Source\getVolume!
  getVolume: =>
    @volume

  ---
  -- Returns whether the Source will loop.
  -- @tparam Source self
  -- @treturn bool True if the Source will loop, false otherwise.
  -- @usage
  -- loop = Source\isLooping!
  isLooping: =>
    @looping

  ---
  -- Returns whether the Source is paused.
  -- @tparam Source self
  -- @treturn bool True if the Source is paused, false otherwise.
  -- @usage
  -- paused = Source\isPaused!
  isPaused: =>
    @paused

  ---
  -- Returns whether the Source is playing.
  -- @tparam Source self
  -- @treturn bool True if the Source is playing, false otherwise.
  -- @usage
  -- playing = Source\isPlaying!
  isPlaying: =>
    @playing

  ---
  -- Returns whether the Source is stopped.
  -- @tparam Source self
  -- @treturn bool True if the Source is stopped, false otherwise.
  -- @usage
  -- stopped = Source\isStopped!
  isStopped: =>
    not @playing

  ---
  -- Sets whether the Source should loop.
  -- @tparam File self
  -- @tparam bool looping True if the source should loop, false otherwise.
  -- @usage
  -- Source\setLooping looping
  setLooping: (looping) =>
    @looping = looping

    if @static
      @source\setLooping @soundId, @looping
    else
      @source\setLooping @looping

  ---
  -- Sets the current volume of the Source.
  -- @tparam File self
  -- @tparam number volume The volume for a Source, where 1.0 is normal volume. Volume cannot be raised above 1.0.
  -- @usage
  -- Source\setVolume volume
  setVolume: (volume) =>
    @volume = volume

    if @static
      @source\setVolume @soundId, @volume
    else
      @source\setVolume @volume

  free: =>
    @source\dispose!

return Source