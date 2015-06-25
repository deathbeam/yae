Font = require "non.objects.Font"
Image = require "non.objects.Image"
Transform = require "non.objects.Transform"
Color = java.require "com.badlogic.gdx.graphics.Color"
OrthographicCamera = java.require "com.badlogic.gdx.graphics.OrthographicCamera"
ShapeRender = java.require "com.badlogic.gdx.graphics.glutils.ShapeRenderer"
SpriteBatch = java.require "com.badlogic.gdx.graphics.g2d.SpriteBatch"

c = require "non.internal.constants"

shader = SpriteBatch\createDefaultShader!
batch = java.new SpriteBatch, 1000, shader
shapes = java.new ShapeRender
font = Font!
shapes\setAutoShapeType true
transform = Transform!
color = java.new Color 1, 1, 1, 1
background = java.new Color 0.4, 0.3, 0.4, 1
blending = "alpha"
batch\setColor color
shapes\setColor color
font.font\setColor color
camera = java.new OrthographicCamera
camera\setToOrtho true
batch\setProjectionMatrix camera.combined
shapes\setProjectionMatrix camera.combined
matrix_dirty = false

check = (texture_based) ->
  if texture_based
    if matrix_dirty
      batch\setTransformMatrix transform.matrix
      matrix_dirty = false;

    if shapes\isDrawing! shapes\end!
    if not batch\isDrawing! batch\begin!
  else
    if matrix_dirty
      shapes\setTransformMatrix transform.matrix
      matrix_dirty = false

    if batch\isDrawing! batch\end!
    if not shapes\isDrawing! shapes\begin!

{
  arc: (mode, x, y, radius, angle1, angle2) ->
    check false
    shapes\set c.shapetype[mode]
    shapes\arc x, y, radius, math.deg(angle1), math.deg(angle2)

  circle: (mode, x, y, radius) ->
    check false
    shapes\set c.shapetype[mode]
    shapes\circle x, y, radius
}