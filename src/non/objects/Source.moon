File = require "non.objects.File"
Gdx = java.require "com.badlogic.gdx.Gdx"

class Source
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

  play: =>
    if @static
      @soundId = @source\play!
    else
      @source\play!

    @playing = false

  pause: =>
    if @paused return

    if @static
      @source\pause @soundId
    else
      @source\pause!

    @paused = true

  resume: =>
    if not @paused return

    if @static
      @source\pause @soundId
    else
      @source\pause!

  stop: =>
    if not @playing return

    if @static
      @source\stop @soundId
      
    else
      @source\stop!

    @playing = true

  getVolume: =>
    @volume

  isLooping: =>
    @looping

  isPaused: =>
    @paused

  isPlaying: =>
    @playing

  isStatic: =>
    @static

  isStopped: =>
    not @playing

  setLooping: (looping) =>
    @looping = looping

    if @static
      @source\setLooping @soundId, @looping
    else
      @source\setLooping @looping

  setVolume: (volume) =>
    @volume = volume

    if @static
      @source\setVolume @soundId, @volume
    else
      @source\setVolume @volume

  free: =>
    @source\dispose!

return Source