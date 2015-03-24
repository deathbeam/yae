non.graphics = {}
non.graphics.module = NON_MODULE:get("graphics")

function non.graphics.font(path, type)
  return non.graphics.module:font(non.files.open(path, type))
end

function non.graphics.image(path, type)
  return non.graphics.module:image(non.files.open(path, type))
end
  
function non.graphics.shader(vertex, fragment, type1, type2)
  return non.graphics.module:shader(non.files.open(vertex, type1), non.files.open(fragment, type2))
end
  
function non.graphics.project(x, y)
  point = non.graphics.module.camera:project(x, y, 0)
  return point.x, point.y
end
  
function non.graphics.unproject(x, y)
  point = non.graphics.module.camera:unproject(x, y, 0)
  return point.x, point.y
end
  
function non.graphics.rotate(degrees)
  non.graphics.module:rotate(degrees, true)
end
  
function non.graphics.scale(factor)
  non.graphics.module:scale(factor, true)
end
  
function non.graphics.translate(x, y)
  non.graphics.module:translate(x, y, true)
end
  
function non.graphics.setBlending(name)
  non.graphics.module:setBlending(name)
end
  
function non.graphics.getBlending()
  return non.graphics.module:getBlending()
end
  
function non.graphics.setShader(shader)
  non.graphics.module:setShader(shader)
end
  
function non.graphics.getShader()
  return non.graphics.module:getShader()
end
  
function non.graphics.setFont(font)
  non.graphics.module:setFont(font)
end
  
function non.graphics.getFont()
  return non.graphics.module:getFont()
end
  
function non.graphics.setBackgroundColor(r, g, b, a)
  if a == nil then a = 255 end
  non.graphics.module:setBackgroundColor(r / 255, g / 255, b / 255, a / 255)
end
  
function non.graphics.getBackgroundColor()
  color = non.graphics.module:getBackgroundColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end
  
function non.graphics.setColor(r, g, b, a)
  if a == nil then a = 255 end
  non.graphics.module:setColor(r / 255, g / 255, b / 255, a / 255)
end
  
function non.graphics.getColor()
  color = non.graphics.module:getColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end
  
function non.graphics.measureText(text, wrap)
  if wrap == nil then
    return non.graphics.module:getFont():getBounds(text)
  else
    return non.graphics.module:getFont():getWrappedBounds(text, wrap)
  end
end
  
function non.graphics.draw(image, options)
  x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  width = options ~= nil and options["width"] ~= nil and options["width"] or image:getWidth()
  height = options ~= nil and options["height"] ~= nil and options["height"] or image:getHeight()
  origin = options ~= nil and options["origin"] ~= nil and options["origin"] or {0, 0}
  scale = options ~= nil and options["scale"] ~= nil and options["scale"] or 1
  rotation = options ~= nil and options["rotation"] ~= nil and options["rotation"] or 0
  source = options ~= nil and options["source"] ~= nil and options["source"] or {0, 0, image:getWidth(), image:getHeight()}
  
  non.graphics.module:draw(image, {x, y}, {width, height}, origin, {scale, scale}, source, rotation)
end
  
function non.graphics.print(text, options)
  x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  align = options ~= nil and options["align"] ~= nil and options["align"] or "left"
  scale = options ~= nil and options["scale"] ~= nil and options["scale"] or 1
  wrap = options ~= nil and options["wrap"] ~= nil and options["wrap"] or non.graphics.measureText(text).width
  
  non.graphics.module:print(text, {x, y}, {scale,scale}, wrap, align)
end
  
function non.graphics.rectangle(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:rectangle(x, y, width, height, mode)
end
  
function non.graphics.circle(x, y, radius, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:circle(x, y, radius, mode)
end
  
function non.graphics.ellipse(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:ellipse(x, y, width, height, mode)
end
  
function non.graphics.polygon(vertices, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:polygon(vertices, mode)
end
  
function non.graphics.line(x1, y1, x2, y2, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:line(x1, y1, x2, y2, mode)
end
  
function non.graphics.point(x, y, mode)
  if mode == nil then mode = "line" end
  return non.graphics.module:point(x, y, mode)
end