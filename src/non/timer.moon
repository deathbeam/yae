TimeUtils = java.require "com.badlogic.gdx.utils.TimeUtils"
Thread = java.require "java.lang.Thread"
Gdx = java.require "com.badlogic.gdx.Gdx"

{
  get_delta: ->
    Gdx.graphics\getDeltaTime!

  get_fps: ->
    Gdx.graphics\getFramesPerSecond!

  get_time: ->
    TimeUtils\millis!

  sleep: (seconds) ->
    Thread\sleep seconds * 1000
}