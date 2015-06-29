Font = require "non.objects.Font"
Image = require "non.objects.Image"
Transform = require "non.objects.Transform"
Quad = require "non.objects.Quad"
c = require "non.internal.constants"
Color = java.require "com.badlogic.gdx.graphics.Color"
Gdx = java.require "com.badlogic.gdx.Gdx"
GL20 = java.require "com.badlogic.gdx.graphics.GL20"
NonVM = java.require "non.NonVM"
OrthographicCamera = java.require "com.badlogic.gdx.graphics.OrthographicCamera"
ShapeRender = java.require "com.badlogic.gdx.graphics.glutils.ShapeRenderer"
SpriteBatch = java.require "com.badlogic.gdx.graphics.g2d.SpriteBatch"

shader = SpriteBatch\createDefaultShader!
batch = java.new SpriteBatch, 1000, shader
shapes = java.new ShapeRender
font = Font!
shapes\setAutoShapeType true
transform = Transform!
color = java.new Color, 1, 1, 1, 1
background = java.new Color, 0.4, 0.3, 0.4, 1
blending = "alpha"
batch\setColor color
shapes\setColor color
font.font\setColor color
camera = java.new OrthographicCamera
camera\setToOrtho true
batch\setProjectionMatrix camera.combined
shapes\setProjectionMatrix camera.combined
matrixDirty = false

check = (textureBased) ->
  if textureBased
    if matrixDirty
      batch\setTransformMatrix transform.matrix
      matrixDirty = false

    if shapes\isDrawing! then NonVM.util\endShapes shapes
    if not batch\isDrawing! then batch\begin!
  else
    if matrixDirty
      shapes\setTransformMatrix transform.matrix
      matrixDirty = false

    if batch\isDrawing! then NonVM.util\endBatch batch
    if not shapes\isDrawing! then shapes\begin!


arc = (mode, x, y, radius, angle1, angle2) ->
  check false
  shapes\set c.shapetype[mode]
  shapes\arc x, y, radius, math.deg(angle1), math.deg(angle2)

circle = (mode, x, y, radius) ->
  check false
  shapes\set c.shapetype[mode]
  shapes\circle x, y, radius

clear = ->
  Gdx.gl\glClearColor background.r, background.g, background.b, background.a
  Gdx.gl\glClear GL20.GL_COLOR_BUFFER_BIT

draw = (image, x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true

  w = image\getWidth!
  h = image\getHeight!
  srcX = 0
  srcY = 0
  srcW = w
  srcH = h
  x -= ox
  y -= oy

  batch\draw image.texture, x, y, ox, oy, w, h, sx, sy, r, srcX, srcY, srcW, srcH, false, true

drawq = (image, quad, x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true

  w = quad.width
  h = quad.height
  srcX = quad.x
  srcY = quad.y
  srcW = quad.sw
  srcH = quad.sh
  x -= ox
  y -= oy

  batch\draw image.texture, x, y, ox, oy, w, h, sx, sy, r, srcX, srcY, srcW, srcH, false, true

ellipse = (mode, x, y, width, height) ->
  check false
  shapes\set c.shapetype[mode]
  shapes\ellipse x, y, width, height

getBackgroundColor = ->
  return background.r * 255, background.g * 255, background.b * 255, background.a * 255

getBlendMode = ->
  return blending

getColor = ->
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255

getFont = ->
  return font

line = (x1, y1, x2, y2) ->
  check false
  shapes\line x1, y1, x2, y2

newFont = (filename, size, filetype) ->
  return Font filename, size, filetype

newImage = (filename, format, filetype) ->
  return Image filename, format, filetype

newQuad = (x, y, width, height, sw, sh) ->
  return Quad x, y, width, height, sw, sh

origin = ->
  transform\identity!
  matrixDirty = true

point = (x, y) ->
  check false
  shapes\point x, y, 0

polygon = (mode, ...) ->
  check false
  shapes\set c.shapetype[mode]
  args = table.pack ...

  if type args[1] == "table"
    shapes\polygon args[1]
  else
    shapes\polygon args

present = ->
  if shapes\isDrawing! then NonVM.util\endShapes shapes
  if batch\isDrawing! then NonVM.util\endBatch batch

print = (text, x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true
  tmp = nil

  if r != 0
    tmp = batch\getTransformMatrix!
    transform\translate(x, y)\rotate(r)\translate(-x, -y) 
    batch\setTransformMatrix transform.matrix

  font.font\getData!\setScale sx, -sy
  font.font\draw batch, text, x - ox * sx, y - oy * sy

  if r != 0
    transform\translate(x, y)\rotate(-r)\translate(-x, -y)
    batch\setTransformMatrix tmp

printf = (text, width=0, align="left", x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true
  tmp = nil

  if r != 0
    tmp = batch\getTransformMatrix!
    transform\translate(x, y)\rotate(r)\translate(-x, -y) 
    batch\setTransformMatrix transform.matrix

  font.font\getData!\setScale sx, -sy
  font.font\draw batch, text, x - ox * sx, y - oy * sy, width, c.aligns[align], true

  if r != 0
    transform\translate(x, y)\rotate(-r)\translate(-x, -y)
    batch\setTransformMatrix tmp

rectangle = (mode, x, y, width, height) ->
  check false
  shapes\set c.shapetype[mode]

  shapes\rect x, y, width, height

reset = ->
  shader = SpriteBatch\createDefaultShader!
  batch\setShader shader

  background\set 0.4, 0.3, 0.4, 1
  color\set 1, 1, 1, 1

  batch\setColor color
  shapes\setColor color
  font.font\setColor color
  transform\identity!
  matrixDirty = true

rotate = (radians) ->
  transform\rotate radians
  matrixDirty = true

scale = (sx, sy) ->
  transform\scale sx, sy
  matrixDirty = true

setBackgroundColor = (r, g, b, a=255) ->
  background\set r / 255, g / 255, b / 255, a / 255

setBlendMode = (mode) ->
  blending = mode
  blendmode = c.blendmodes[blending]
  batch\setBlendFunction blendmode[1], blendmode[2]

setColor =  (r, g, b, a=255) ->
  color\set r / 255, g / 255, b / 255, a / 255
  batch\setColor color
  shapes\setColor color
  font.font\setColor color

setFont = (newFont) ->
  font = newFont

setNewFont = (filename, size, filetype) ->
  font = newFont filename, size, filetype

translate = (tx, ty) ->
  transform\translate tx, ty
  matrixDirty = true

{
  :arc
  :circle
  :clear
  :draw
  :drawq
  :ellipse
  :getBackgroundColor
  :getBlendMode
  :getColor
  :getFont
  :line
  :newFont
  :newImage
  :newQuad
  :origin
  :point
  :polygon
  :present
  :print
  :printf
  :rectangle
  :reset
  :rotate
  :scale
  :setBackgroundColor
  :setBlendMode
  :setColor
  :setFont
  :setNewFont
  :translate
}