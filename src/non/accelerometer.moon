Gdx = java.require "com.badlogic.gdx.Gdx"

{
  get_x: ->
    Gdx.input\getAccelerometerX!

  get_y: ->
    Gdx.input\getAccelerometerY!

  get_z: ->
    Gdx.input\getAccelerometerZ!

  get_rotation: ->
    @get_x!, @get_y!, @get_z!
}