TimeUtils = java.require "com.badlogic.Gdx.utils.TimeUtils"
Thread = java.require "java.lang.Thread"
Gdx = java.require "com.badlogic.gdx.Gdx"

{
  getDelta: ->
    Gdx.graphics\getDeltaTime!

  getFPS: ->
    Gdx.graphics\getFramesPerSecond!

  getTime: ->
    TimeUtils\millis!

  sleep: (seconds) ->
    Thread\sleep seconds * 1000
}