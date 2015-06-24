Source = require "non.objects.Source"

sources = {}
globalvolume = 1

{
  get_num_sources: ->
    #sources

  get_volume: ->
    globalvolume

  new_source: (filename, audiotype, filetype) ->
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

  set_volume: (volume) ->
    globalvolume = volume

    for i, source in ipairs sources
      source\setVolume globalvolume

  stop: (source) ->
    source\stop!

  stop_all: ->
    for i, source in ipairs sources
      source\stop!
      source\free!
}