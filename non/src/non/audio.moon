Source = require("non.objects.Source")

sources = {}

{
  volume: 1

  getNumSources: ->
    #sources

  getVolume: ->
    @volume

  newSource: (filename, audiotype, filetype) ->
    source = Source filename, audiotype, filetype
    table.insert sources, source
    return source

  pause: (source) ->
    source\pause!

  play: (source) ->
    source\setVolume @volume
    source\play!

  resume: (source) ->
    source\resume!

  setVolume: (volume) ->
    @volume = volume

    for i, source in ipairs sources
      source\setVolume @volume

  stop: (source) ->
    source\stop!

  stopAll: ->
    for i, source in ipairs sources
      source\stop!
      source\free!
}