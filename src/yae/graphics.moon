-------------------------------------------------------------------------------
-- Provides simple way to load, render and draw things.
-------------------------------------------------------------------------------
-- The primary responsibility for the yae.graphics module is the drawing of
-- lines, shapes, text, Images and other Drawable objects onto the screen. Its
-- secondary responsibilities include loading external files (including Images
-- and Fonts) into memory and managing screen geometry.
-- Yae's coordinate system is rooted in the upper-left corner of the screen,
-- which is at location (0, 0). The x axis is horizontal: larger values are
-- further to the right. The y axis is vertical: larger values are further
-- towards the bottom.
--
-- @module yae.graphics

import java, Font, Image, Quad from yae
Constants = require "yae.constants"
Color = java.require "com.badlogic.gdx.graphics.Color"
Gdx = java.require "com.badlogic.gdx.Gdx"
GL20 = java.require "com.badlogic.gdx.graphics.GL20"
Matrix4 = java.require "com.badlogic.gdx.math.Matrix4"
YaeVM = java.require "yae.YaeVM"
OrthographicCamera = java.require "com.badlogic.gdx.graphics.OrthographicCamera"
ShapeRender = java.require "com.badlogic.gdx.graphics.glutils.ShapeRenderer"
SpriteBatch = java.require "com.badlogic.gdx.graphics.g2d.SpriteBatch"

shader = SpriteBatch\createDefaultShader!
batch = java.new SpriteBatch, 1000, shader
shapes = java.new ShapeRender
font = Font!
shapes\setAutoShapeType true
matrix = java.new Matrix4
color = java.new Color, 1, 1, 1, 1
background = java.new Color, 0, 0, 0, 1
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
      batch\setTransformMatrix matrix
      matrixDirty = false

    if shapes\isDrawing! then YaeVM.util\endShapes shapes
    if not batch\isDrawing! then batch\begin!
  else
    if matrixDirty
      shapes\setTransformMatrix matrix
      matrixDirty = false

    if batch\isDrawing! then YaeVM.util\endBatch batch
    if not shapes\isDrawing! then shapes\begin!

---
-- Draws a filled or unfilled arc at position (x, y). The arc is drawn from
-- angle1 to angle2 in radians.
-- @string mode How to draw the arc.
-- @number x The position of the center along x-axis.
-- @number y The position of the center along y-axis.
-- @number radius Radius of the arc.
-- @number angle1 The angle at which the arc begins.
-- @number angle2 The angle at which the arc terminates.
-- @usage
-- -- Drawing half a circle
-- yae.draw = ->
--   yae.graphics.arc( "fill", 400, 300, 100, 0, math.pi )
--  
-- -- Drawing Pacman
-- pacwidth = math.pi / 6 -- size of his mouth
-- yae.draw = ->
--   yae.graphics.setColor( 255, 255, 0 ) -- pacman needs to be yellow
--   yae.graphics.arc( "fill", 400, 300, 100, pacwidth, (math.pi * 2) - pacwidth )
arc = (mode, x, y, radius, angle1, angle2) ->
  check false
  shapes\set Constants.shapetypes[mode]
  shapes\arc x, y, radius, math.deg(angle1), math.deg(angle2)

circle = (mode, x, y, radius) ->
  check false
  shapes\set Constants.shapetypes[mode]
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
  shapes\set Constants.shapetypes[mode]
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
  matrix\idt!
  matrixDirty = true

point = (x, y) ->
  check false
  shapes\point x, y, 0

polygon = (mode, ...) ->
  check false
  shapes\set Constants.shapetypes[mode]
  args = table.pack ...

  if type args[1] == "table"
    shapes\polygon args[1]
  else
    shapes\polygon args

present = ->
  if shapes\isDrawing! then YaeVM.util\endShapes shapes
  if batch\isDrawing! then YaeVM.util\endBatch batch

print = (text, x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true
  tmp = nil

  if r != 0
    tmp = batch\getTransformMatrix!
    translate x, y
    rotate r
    translate -x, -y
    batch\setTransformMatrix matrix
    matrixDirty = false

  font.font\getData!\setScale sx, -sy
  font.font\draw batch, text, x - ox * sx, y - oy * sy

  if r != 0
    translate x, y
    rotate -r
    translate -x, -y
    batch\setTransformMatrix tmp
    matrixDirty = false

printf = (text, width=0, align="left", x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
  check true
  tmp = nil

  if r != 0
    tmp = batch\getTransformMatrix!
    translate x, y
    rotate r
    translate -x, -y
    batch\setTransformMatrix matrix
    matrixDirty = false

  font.font\getData!\setScale sx, -sy
  font.font\draw batch, text, x - ox * sx, y - oy * sy, width, Constants.aligns[align], true

  if r != 0
    translate x, y
    rotate -r
    translate -x, -y
    batch\setTransformMatrix tmp
    matrixDirty = false

rectangle = (mode, x, y, width, height) ->
  check false
  shapes\set Constants.shapetypes[mode]

  shapes\rect x, y, width, height

reset = ->
  shader = SpriteBatch\createDefaultShader!
  batch\setShader shader

  background\set 0.4, 0.3, 0.4, 1
  color\set 1, 1, 1, 1

  batch\setColor color
  shapes\setColor color
  font.font\setColor color
  matrix\idt!
  matrixDirty = true

rotate = (radians) ->
  matrix\rotate 0, 0, 1, math.deg(radians)
  matrixDirty = true

scale = (sx, sy) ->
  matrix\scale sx, sy, 1
  matrixDirty = true

setBackgroundColor = (r, g, b, a=255) ->
  background\set r / 255, g / 255, b / 255, a / 255

setBlendMode = (mode) ->
  blending = mode
  blendmode = Constants.blendmodes[blending]
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
  matrix\translate tx, ty, 0
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
