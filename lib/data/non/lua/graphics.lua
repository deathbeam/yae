NON.graphics = {}
NON.graphics.module = NON_MODULE:get("graphics")

function NON.graphics.font(path, type)
  return NON.graphics.module:font(NON.files.open(path, type))
end

function NON.graphics.image(path, type)
  return NON.graphics.module:image(NON.files.open(path, type))
end
  
function NON.graphics.shader(vertex, fragment, type1, type2)
  return NON.graphics.module:shader(NON.files.open(vertex, type1), NON.files.open(fragment, type2))
end
  
function NON.graphics.project(x, y)
  point = NON.graphics.module.camera:project(x, y, 0)
  return point.x, point.y
end
  
function NON.graphics.unproject(x, y)
  point = NON.graphics.module.camera:unproject(x, y, 0)
  return point.x, point.y
end
  
function NON.graphics.rotate(degrees)
  NON.graphics.module:rotate(degrees, true)
end
  
function NON.graphics.scale(factor)
  NON.graphics.module:scale(factor, true)
end
  
function NON.graphics.translate(x, y)
  NON.graphics.module:translate(x, y, true)
end
  
function NON.graphics.setBlending(name)
  NON.graphics.module:setBlending(name)
end
  
function NON.graphics.getBlending()
  return NON.graphics.module:getBlending()
end
  
function NON.graphics.setShader(shader)
  NON.graphics.module:setShader(shader)
end
  
function NON.graphics.getShader()
  return NON.graphics.module:getShader()
end
  
function NON.graphics.setFont(font)
  NON.graphics.module:setFont(font)
end
  
function NON.graphics.getFont()
  return NON.graphics.module:getFont()
end
  
function NON.graphics.setBackgroundColor(r, g, b, a)
  if a == nil then a = 255 end
  NON.graphics.module:setBackgroundColor(r / 255, g / 255, b / 255, a / 255)
end
  
function NON.graphics.getBackgroundColor()
  color = NON.graphics.module:getBackgroundColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end
  
function NON.graphics.setColor(r, g, b, a)
  if a == nil then a = 255 end
  NON.graphics.module:setColor(r / 255, g / 255, b / 255, a / 255)
end
  
function NON.graphics.getColor()
  color = NON.graphics.module:getColor()
  return color.r * 255, color.g * 255, color.b * 255, color.a * 255
end
  
function NON.graphics.measureText(text, wrap)
  if wrap == nil then
    return NON.graphics.module:getFont():getBounds(text)
  else
    return NON.graphics.module:getFont():getWrappedBounds(text, wrap)
  end
end
  
function NON.graphics.draw(image, options)
  x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  width = options ~= nil and options["width"] ~= nil and options["width"] or image:getWidth()
  height = options ~= nil and options["height"] ~= nil and options["height"] or image:getHeight()
  origin = options ~= nil and options["origin"] ~= nil and options["origin"] or {0, 0}
  scale = options ~= nil and options["scale"] ~= nil and options["scale"] or 1
  rotation = options ~= nil and options["rotation"] ~= nil and options["rotation"] or 0
  source = options ~= nil and options["source"] ~= nil and options["source"] or {0, 0, image:getWidth(), image:getHeight()}
  
  NON.graphics.module:draw(image, {x, y}, {width, height}, origin, {scale, scale}, source, rotation)
end
  
function NON.graphics.print(text, options)
  x = options ~= nil and options["x"] ~= nil and options["x"] or 0
  y = options ~= nil and options["y"] ~= nil and options["y"] or 0
  align = options ~= nil and options["align"] ~= nil and options["align"] or "left"
  scale = options ~= nil and options["scale"] ~= nil and options["scale"] or 1
  wrap = options ~= nil and options["wrap"] ~= nil and options["wrap"] or NON.graphics.measureText(text).width
  
  NON.graphics.module:print(text, {x, y}, {scale,scale}, wrap, align)
end
  
function NON.graphics.rectangle(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:rectangle(x, y, width, height, mode)
end
  
function NON.graphics.circle(x, y, radius, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:circle(x, y, radius, mode)
end
  
function NON.graphics.ellipse(x, y, width, height, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:ellipse(x, y, width, height, mode)
end
  
function NON.graphics.polygon(vertices, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:polygon(vertices, mode)
end
  
function NON.graphics.line(x1, y1, x2, y2, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:line(x1, y1, x2, y2, mode)
end
  
function NON.graphics.point(x, y, mode)
  if mode == nil then mode = "line" end
  return NON.graphics.module:point(x, y, mode)
end