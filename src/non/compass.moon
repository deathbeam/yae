Gdx = java.require "com.badlogic.gdx.Gdx"

{
  getAzimuth: ->
    Gdx.input\getAzimuth!

  getPitch: ->
    Gdx.input\getPitch!

  getRoll: ->
    Gdx.input\getRoll!

  getOrientation: ->
    @getAzimuth!, @getPitch!, @getRoll!
}