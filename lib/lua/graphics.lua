--[[--------
Provides simple way to load, render and draw things.

@module non.graphics
]]

non.graphics = {}
non.graphics.module = NON:get("graphics")

----------
-- Create an image instance from specified resource
-- @tparam string path path to image file
-- @tparam string type type of file path (default is "internal")
-- @treturn Image an image instance
-- @see non.files
-- @usage image = non.graphics.image("clound.png", "external")
function non.graphics.image(path, type)
  return non.graphics.module:image(non.files.open(path, type))
end

----------
-- Create a font instance from specified resource
-- @tparam string path path to font file
-- @tparam number size size (in pixels) of font
-- @tparam string type type of file path (default is "internal")
-- @treturn Font a font instance
-- @see non.files
-- @usage font = non.graphics.font("timesnewroman.ttf", 12, "external")
function non.graphics.font(path, size, type)
  if size == nil then size = 12 end
  return non.graphics.module:font(non.files.open(path, type), size)
end

----------
-- Create a shader instance from specified resources
-- @tparam string vertex path to vertex shader file
-- @tparam string fragment path to fragment shader file
-- @tparam string type1 type of vertex shader file path (default is "internal")
-- @tparam string type2 type of fragment shader file path (default is "internal")
-- @treturn Shader a shader instance
-- @see non.files
-- @usage shader = non.graphics.shader("shader.vert", "shader.frag", "external", "external")
function non.graphics.shader(vertex, fragment, type1, type2)
  return non.graphics.module:shader(non.files.open(vertex, type1), non.files.open(fragment, type2))
end

----------
-- Transforms screen coordinates to world coordinates
-- @tparam number x screen x coordinate
-- @tparam number y screen y coordinate
-- @usage worldx, worldy = non.graphics.project(screenx, screeny)
function non.graphics.project(x, y)
  local point = non.graphics.module.camera:project(x, y, 0)
  return point.x, point.y
end

----------
-- Transforms world coordinates to screen coordinates
-- @tparam number x world x coordinate
-- @tparam number y world y coordinate
-- @usage screenx, screeny = non.graphics.project(400, 100)
function non.graphics.unproject(x, y)
  local point = non.graphics.module.camera:unproject(x, y, 0)
  return point.x, point.y
end

----------
-- Rotate the screen by given angle in degrees
-- @tparam number degrees how much the screen will rotate
-- @usage non.graphics.rotate(60)
function non.graphics.rotate(degrees)
  non.graphics.module:rotate(degrees, true)
end

----------
-- Scale the screen by given factor
-- @tparam number factor how much the screen will scale
-- @usage non.graphics.scale(2)
function non.graphics.scale(factor)
  non.graphics.module:scale(factor, true)
end

----------
-- Translate the screen position by given x and y
-- @tparam number x x coordinate
-- @tparam number y y coordinate
-- @usage non.graphics.translate(10, 10)
function non.graphics.translate(x, y)
  non.graphics.module:translate(x, y, true)
end

----------
-- Get screen width (in pixels)
-- @treturn number width of the screen
-- @usage width = non.graphics.getWidth()
function non.graphics.getWidth()
  return NON.gdx.graphics:getWidth()
end

----------
-- Get screen height (in pixels)
-- @treturn number height of the screen
-- @usage height = non.graphics.getHeight()
function non.graphics.getHeight()
  return NON.gdx.graphics:getHeight()
end

----------
-- Get screen size (in pixels)
-- @treturn table width and height of the screen
-- @usage width, height = non.graphics.getSize()
function non.graphics.getSize()
  return non.graphics.getWidth(), non.graphics.getHeight()
end

----------
-- Changes graphics blend mode.
-- NÖN supports various blend modes: additive, alpha, multiplicative, premultiplied, replace, screen, substractive
-- @tparam string name name of the new blend mode
-- @usage non.graphics.setBlending("additive")
function non.graphics.setBlending(name)
  non.graphics.module:setBlending(name)
end

----------
-- Get current blend mode.
-- NÖN supports various blend modes: additive, alpha, multiplicative, premultiplied, replace, screen, substractive
-- @treturn string name of the current blend mode
-- @usage blendMode = non.graphics.getBlending()
function non.graphics.getBlending()
  return non.graphics.module:getBlending()
end

----------
-- Changes graphics shader
-- @tparam Shader shader shader to set
-- @see non.graphics.shader
-- @usage non.graphics.setShader(shader)
function non.graphics.setShader(shader)
  non.graphics.module:setShader(shader)
end

----------
-- Get current shader
-- @treturn Shader current shader
-- @see non.graphics.shader
-- @usage shader = non.graphics.getShader()
function non.graphics.getShader()
  return non.graphics.module:getShader()
end

----------
-- Set current font used for text drawing
-- @tparam Font font font to set
-- @see non.graphics.font
-- @usage non.graphics.setFont(font)
function non.graphics.setFont(font)
  non.graphics.module:setFont(font)
end

----------
-- Get current font
-- @treturn Font current font
-- @see non.graphics.font
-- @usage font = non.graphics.getFont()
function non.graphics.getFont()
  return non.graphics.module:getFont()
end

----------
-- Change background color. Background color is used for clearing the screen each frame. Color parts can be in range 0-255.
-- @tparam number r red
-- @tparam number g green
-- @tparam number b blue
-- @tparam number a alpha (default is 255)
-- @usage non.graphics.setBackgroundColor(100, 100, 255, 255)
function non.graphics.setBackgroundColor(r, g, b, a)
  if a == nil then a = 255 end
  non.graphics.module:setBackgroundColor(r / 255, g / 255, b / 255, a / 255)
end

----------
-- Get current background color. Background color is used for clearing the screen each frame. Color parts can be in range 0-255.
-- @treturn table current background color
-- @usage r, g, b, a = non.graphics.getBackgroundColor()
function non.graphics.getBackgroundColor()
  local color = non.graphics.module:getBackgroundColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end

----------
-- Change color. Color is used for drawing text and tinting images. Color parts can be in range 0-255.
-- @tparam number r red
-- @tparam number g green
-- @tparam number b blue
-- @tparam number a alpha (default is 255)
-- @usage non.graphics.setColor(100, 100, 255, 255)
function non.graphics.setColor(r, g, b, a)
  if a == nil then a = 255 end
  non.graphics.module:setColor(r / 255, g / 255, b / 255, a / 255)
end

----------
-- Get current background color. Color is used for drawing text and tinting images. Color parts can be in range 0-255.
-- @treturn table current color
-- @usage r, g, b, a = non.graphics.getColor()
function non.graphics.getColor()
  local color = non.graphics.module:getColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end

----------
-- Get size of specified image
-- @tparam Image image image to get size from
-- @treturn table size of the image
-- @see non.graphics.image
-- @usage width, height = non.graphics.getImageBounds(image)
function non.graphics.getImageBounds(image)
  return image:getWidth(), image:getHeight()
end

----------
-- Get size of specified text
-- @tparam string text text to get size from
-- @tparam string wrap text wrap (if using on multiline wrapped text, otherwise defaults to nil)
-- @treturn table size of the text
-- @usage width, height = non.graphics.getTextBounds("Hello World!")
function non.graphics.getTextBounds(text, wrap)
  if wrap == nil then
    local bounds = non.graphics.module:getFont():getBounds(text)
    return bounds.width, bounds.height
  else
    local bounds = non.graphics.module:getFont():getWrappedBounds(text, wrap)
    return bounds.width, bounds.height
  end
end

----------
-- Draw and transform image
-- @tparam Image image image to draw
-- @tparam table options transform options for image
-- @see non.graphics.image
-- @usage
-- non.graphics.draw(image, {
--   x = 10,                   -- x position of the image
--   y = 10,                   -- y position of the image
--   width = 100,              -- to what width will be the image scaled
--   height = 100,             -- to what height will be the image scaled
--   rotation = 20,            -- rotation (in degrees)
--   origin = { 15, 15 },      -- around what point will be the image rotated and scaled
--   scale = { 2, 2 },         -- scale of the image
--   source = { 0, 0, 50, 50 } -- source rectangle on the image texture
-- })
function non.graphics.draw(image, options)
  local x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  local y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  local width = options ~= nil and options["width"] ~= nil and options["width"] or image:getWidth()
  local height = options ~= nil and options["height"] ~= nil and options["height"] or image:getHeight()
  local origin = options ~= nil and options["origin"] ~= nil and options["origin"] or {0, 0}
  local scale = options ~= nil and options["scale"] ~= nil and options["scale"] or {1, 1}
  local rotation = options ~= nil and options["rotation"] ~= nil and options["rotation"] or 0
  local source = options ~= nil and options["source"] ~= nil and options["source"] or {0, 0, image:getWidth(), image:getHeight()}
  
  non.graphics.module:draw(image, x, y, width, height, scale, origin, source, rotation)
end

----------
-- Print and transform text
-- @tparam string text text to draw
-- @tparam table options transform options for text
-- @see non.graphics.image
-- @usage
-- non.graphics.print("Hello World!", {
--   x = 10,                   -- x position of the text
--   y = 10,                   -- y position of the text
--   align = "left",           -- align of the text ("left", "center", "right")
--   scale = { 2, 2 },         -- scale of the text
--   wrap = 20                 -- after how many glyphs will be text wrapped to new line
-- })
function non.graphics.print(text, options)
  local x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  local y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  local align = options ~= nil and options["align"] ~= nil and options["align"] or "left"
  local scale = options ~= nil and options["scale"] ~= nil and options["scale"] or {1, 1}
  
  if options ~= nil and options["wrap"] ~= nil then
    non.graphics.module:print(text, x, y, scale, options["wrap"], align)
  else
    local width, height = non.graphics.getTextBounds(text)
    non.graphics.module:print(text, x, y, scale, width, align)
  end
end

----------
-- Draw rectangle shape on the screen
-- @tparam number x x position of the shape
-- @tparam number y y position of the shape
-- @tparam number width width of the shape
-- @tparam number height height of the shape
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line"
-- @usage non.graphics.rectangle(0, 0, 30, 30, "fill")
function non.graphics.rectangle(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:rectangle(x, y, width, height, mode)
end

----------
-- Draw circle shape on the screen
-- @tparam number x x position of the shape
-- @tparam number y y position of the shape
-- @tparam number radius radius of the shape
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line"
-- @usage non.graphics.circle(0, 0, 30, "fill")  
function non.graphics.circle(x, y, radius, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:circle(x, y, radius, mode)
end

----------
-- Draw ellipse shape on the screen
-- @tparam number x x position of the shape
-- @tparam number y y position of the shape
-- @tparam number width width of the shape
-- @tparam number height height of the shape
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line"
-- @usage non.graphics.rectangle(0, 0, 30, 30, "fill")
function non.graphics.ellipse(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:ellipse(x, y, width, height, mode)
end

----------
-- Draw polygon shape on the screen
-- @tparam table vertices shape vertices
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line"
-- @usage non.graphics.polygon({0, 0, 30, 30, 50, 80, 120, 190}, "fill")  
function non.graphics.polygon(vertices, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:polygon(vertices, mode)
end

----------
-- Draw line shape on the screen
-- @tparam number x1 x1 position of the shape
-- @tparam number y1 y1 position of the shape
-- @tparam number x2 x2 position of the shape
-- @tparam number y2 y2 position of the shape
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line". For lines this is irrevelant.
-- @usage non.graphics.line(0, 10, 100, 10)
function non.graphics.line(x1, y1, x2, y2, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:line(x1, y1, x2, y2, mode)
end

----------
-- Draw point shape on the screen
-- @tparam number x x position of the shape
-- @tparam number y y position of the shape
-- @tparam string mode draw mode of shape. Can be "line" or "fill". Defaults to "line". For points this is irrevelant.
-- @usage non.graphics.point(50, 100)
function non.graphics.point(x, y, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:point(x, y, mode)
end