Font = require "non.objects.Font"
Image = require "non.objects.Image"
Transform = require "non.objects.Transform"
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
      matrix_dirty = false

    if shapes\isDrawing! then NonVM.helpers\endShapes shapes
    if not batch\isDrawing! then batch\begin!
  else
    if matrix_dirty
      shapes\setTransformMatrix transform.matrix
      matrix_dirty = false

    if batch\isDrawing! then NonVM.helpers\endBatch batch
    if not shapes\isDrawing! then shapes\begin!

{
  arc: (mode, x, y, radius, angle1, angle2) ->
    check false
    shapes\set c.shapetype[mode]
    shapes\arc x, y, radius, math.deg(angle1), math.deg(angle2)

  circle: (mode, x, y, radius) ->
    check false
    shapes\set c.shapetype[mode]
    shapes\circle x, y, radius

  clear: ->
    Gdx.gl\glClearColor background.r, background.g, background.b, background.a
    Gdx.gl\glClear GL20.GL_COLOR_BUFFER_BIT

  draw: (image, quad, x=0, y=0, r=0, sx=1, sy=1, ox=0, oy=0) ->
    check true

    if type(quad) != "Quad"
      oy = ox
      ox = sy
      sy = sx
      sx = r
      y = x
      x = quad
      w = image\get_width!
      h = image\get_height!
      src_x = 0
      src_y = 0
      src_w = w
      src_h = h
    else
      w = quad.width
      h = quad.height
      src_x = quad.x
      src_y = quad.y
      src_w = quad.sw
      src_h = quad.sh

    x -= ox
    y -= oy

    batch\draw image.texture, x, y, ox, oy, w, h, sx, sy, r, src_x, src_y, src_w, src_h, false, true

  ellipse: (mode, x, y, width, height) ->
    check false
    shapes\set c.shapetype[mode]
    shapes\ellipse x, y, width, height

  get_background_color: ->
    return background.r * 255, background.g * 255, background.b * 255, background.a * 255

  get_blend_mode: ->
    return blending

  get_color: ->
    return color.r * 255, color.g * 255, color.b * 255, color.a * 255

  get_font: ->
    return font

  line: (x1, y1, x2, y2) ->
    check false
    shapes\line x1, y1, x2, y2

  new_font: (filename, size, filetype) ->
    return Font filename, size, filetype

  new_image: (filename, format, filetype) ->
    return Image filename, format, filetype

  new_quad: (x, y, width, height, sw, sh) ->
    return Quad x, y, width, height, sw, sh

  origin: ->
    transform\identity!
    matrix_dirty = true

  point: (x, y) ->
    check false
    shapes\point x, y, 0

  polygon: (mode, ...) ->
    check false
    shapes\set c.shapetype[mode]
    args = table.pack ...

    if type args[1] == "table"
      shapes\polygon args[1]
    else
      shapes\polygon args

  present: ->
    if shapes\isDrawing! then NonVM.helpers\endShapes shapes
    if batch\isDrawing! then NonVM.helpers\endBatch batch

  rectangle: (mode, x, y, width, height) ->
    check false
    shapes\set c.shapetype[mode]

    shapes\rect x, y, width, height

  reset: ->
    shader = SpriteBatch\createDefaultShader!
    batch\setShader shader

    background\set 0.4, 0.3, 0.4, 1
    color\set 1, 1, 1, 1

    batch\setColor color
    shapes\setColor color
    font.font\setColor color
    transform\identity!
    matrix_dirty = true

  rotate: (radians) ->
    transform\rotate radians
    matrix_dirty = true

  set_background_color: (r, g, b, a=255) ->
    background\set r / 255, g / 255, b / 255, a / 255

  set_blend_mode: (mode) ->
    blending = mode
    blendmode = c.blendmodes[blending]
    batch\setBlendFunction blendmode[1], blendmode[2]

  set_color:  (r, g, b, a=255) ->
    color\set r / 255, g / 255, b / 255, a / 255
    batch\setColor color
    shapes\setColor color
    font.font\setColor color

  set_font: (new_font) ->
    font = new_font

  set_new_font: (filename, size, filetype) ->
    font = @new_font filename, size, filetype

  scale: (sx, sy) ->
    transform\scale sx, sy
    matrix_dirty = true

  translate: (tx, ty) ->
    transform\translate tx, ty
    matrix_dirty = true
}