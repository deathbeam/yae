Source = require "non.objects.Source"

sources = {}
globalvolume = 1

{
  getNumSources: ->
    #sources

  getVolume: ->
    globalvolume

  newSource: (filename, audiotype, filetype) ->
    source = Source filename, audiotype, filetype
    table.insert sources, source
    return source

  pause: (source) ->
    source\pause!

  play: (source) ->
    source\setVolume globalvolume
    source\play!

  resume: (source) ->
    source\resume!

  setVolume: (volume) ->
    globalvolume = volume

    for i, source in ipairs sources
      source\setVolume globalvolume

  stop: (source) ->
    source\stop!

  stopAll: ->
    for i, source in ipairs sources
      source\stop!
      source\free!
}