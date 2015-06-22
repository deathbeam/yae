time = java.require "com.badlogic.gdx.utils.TimeUtils"
thread = java.require "java.lang.Thread"
gdx = require("non.common").gdx

{
  getDelta: ->
    gdx.graphics\getDeltaTime!

  getFPS: ->
    gdx.graphics\getFramesPerSecond!

  getTime: ->
    time\millis!

  sleep: (seconds) ->
    thread\sleep seconds * 1000
}