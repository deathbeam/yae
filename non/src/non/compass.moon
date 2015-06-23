gdx = require("non.internal.common").gdx

{
  getAzimuth: ->
    gdx.input\getAzimuth!

  getPitch: ->
    gdx.input\getPitch!

  getRoll: ->
    gdx.input\getRoll!

  getOrientation: ->
    @getAzimuth!, @getPitch!, @getRoll!
}