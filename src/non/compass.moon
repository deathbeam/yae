Gdx = java.require "com.badlogic.gdx.Gdx"

{
  get_azimuth: ->
    Gdx.input\getAzimuth!

  get_pitch: ->
    Gdx.input\getPitch!

  get_roll: ->
    Gdx.input\getRoll!

  get_orientation: ->
    @get_azimuth!, @get_pitch!, @get_roll!
}