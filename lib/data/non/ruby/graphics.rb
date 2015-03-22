class Graphics
  java_import 'com.badlogic.gdx.graphics.g2d.BitmapFont'
  java_import 'com.badlogic.gdx.graphics.glutils.ShaderProgram'
  java_import 'com.badlogic.gdx.graphics.Texture'
  
  def initialize
    @module = NON::JModule.get("graphics")
  end
  
  def font(path, type = :internal)
    BitmapFont.new NON.files.open(path, type)
  end

  def image(path, type = :internal)
    Texture.new NON.files.open(path, type)
  end
  
  def shader(vertex, fragment, type1 = :internal, type2 = :internal)
    ShaderProgram.new NON.files.open(vertex, type1), NON.files.open(fragment, type2)
  end
  
  def project(x, y)
    point = @module.camera.project(x, y, 0)
    return point.x, point.y
  end
  
  def unproject(x, y)
    point = @module.camera.unproject(x, y, 0)
    return point.x, point.y
  end
  
  def rotate(degrees)
    @module.rotate(degrees, true)
  end
  
  def scale(factor)
    @module.scale(factor, true)
  end
  
  def translate(x, y)
    @module.translate(x, y, true)
  end
  
  def set_blending(name)
    @module.setBlending name
  end
  
  def get_blending
    @module.getBlending
  end
  
  def set_shader(shader)
    @module.setShader shader
  end
  
  def get_shader
    @module.getShader
  end
  
  def set_font(font)
    @module.setFont font
  end
  
  def get_font
    @module.getFont
  end
  
  def set_background_color(r, g, b, a = 255)
    @module.setBackgroundColor r / 255, g / 255, b / 255, a / 255
  end
  
  def get_background_color
    color = @module.getBackgroundColor
    return color.r * 255, color.g * 255, color.b * 255, color.a * 255
  end
  
  def set_color(r, g, b, a = 255)
    @module.setColor r / 255, g / 255, b / 255, a / 255
  end
  
  def get_color
    color = @module.getColor
    return color.r * 255, color.g * 255, color.b * 255, color.a * 255
  end
  
  def measure_text(text, wrap = nil)
    if wrap == nil
      @module.getFont.getBounds text
    else
      @module.getFont.getWrappedBounds text, wrap
    end
  end
  
  def draw(image, options = nil)
    x = options != nil && options[:x] ? options[:x] : 0
    y = options != nil && options[:y] ? options[:y] : 0
    width = options != nil && options[:width] ? options[:width] : image.getWidth()
    height = options != nil && options[:height] ? options[:height] : image.getHeight()
    origin = options != nil && options[:origin] ? options[:origin] : [0, 0]
    scale = options != nil && options[:scale] ? options[:scale] : 1
    rotation = options != nil && options[:rotation] ? options[:rotation] : 0
    source = options != nil && options[:source] ? options[:source] : [0, 0, image.getWidth(), image.getHeight()]
    
    @module.draw(image, [x, y], [width, height], origin, [scale, scale], source, rotation)
  end
  
  def print(text, options = nil)
    x = options != nil && options[:x] ? options[:x] : 0
    y = options != nil && options[:y] ? options[:y] : 0
    align = options != nil && options[:align] ? options[:align] : :left
    scale = options != nil && options[:scale] ? options[:scale] : 1
    wrap = options != nil && options[:wrap] ? options[:wrap] : measure_text(text).width
    
    @module.print(text, [x, y], [scale,scale], wrap, align)
  end
  
  def rectangle(x, y, width, height, mode = "line")
    @module.rectangle x, y, width, height, mode
  end
  
  def circle(x, y, radius, mode = "line")
    @module.circle x, y, radius, mode
  end
  
  def ellipse(x, y, width, height, mode = "line")
    @module.ellipse x, y, width, height, mode
  end
  
  def polygon(vertices, mode = "line")
    @module.polygon vertices, mode
  end
  
  def line(x1, y1, x2, y2, mode = "line")
    @module.line x1, y1, x2, y2, mode
  end
  
  def point(x, y, mode = "line")
    @module.point x, y, mode
  end
end