Matrix4 = java.require "com.badlogic.gdx.math.Matrix4"

class Transform
  new: =>
    @matrix = java.new Matrix4

  identity: =>
    @matrix\idt!

  rotate: (radians) =>
    degrees = math.deg radians
    @matrix\rotate 0, 0, 1, degrees

  scale: (x, y) =>
    @matrix\scale x, y, 1

  translate: (x, y) =>
    @matrix\translate x, y, 0

return Transform