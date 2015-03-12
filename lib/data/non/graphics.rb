class Graphics
    java_import 'non.ModuleHandler'
    
    def initialize
        @module = ModuleHandler.get("graphics")
    end
    
    def measure_text(text, wrap = nil)
        wrap == nil ? @module.measureText(text) : @module.measureText(text, wrap)
    end

    def image(path)
        @module.image(path)
    end
    
    def font(path)
        @module.font(path)
    end
    
    def shader(vertex, fragment)
        @module.shader(vertex, fragment)
    end
    
    def color(r, g, b, a = 1)
        @module.color(r, g, b, a)
    end
    
    def project(x, y)
        @module.project(x, y)
    end
    
    def unproject(x, y)
        @module.unproject(x, y)
    end
    
    def rotate(degrees, x = 0, y = 0, z = 0)
        @module.rotate(degrees, x, y, z)
    end
    
    def scale(factor)
        @module.scale(factor)
    end
    
    def translate(x, y)
        @module.translate(x, y)
    end
    
    def clear(r, g, b, a = 1)
        @module.clear(r, g, b, a)
    end
    
    def set_blending(blending)
        @module.setBlending(blending)
    end
    
    def set_shader(shader)
        @module.setShader(shader)
    end
    
    def set_font(font)
        @module.setFont(font)
    end
    
    def set_color(r, g, b, a = 1)
        @module.setColor(r, g, b, a)
    end
    
    def get_color()
        @module.getColor()
    end
    
    def draw(image, options = nil)
        position = options != nil && options[:position] ? options[:position] : [0, 0]
        size = options != nil && options[:size] ? options[:size] : [image.getWidth(), image.getHeight()]
        origin = options != nil && options[:origin] ? options[:origin] : [0, 0]
        scale = options != nil && options[:scale] ? options[:scale] : [1, 1]
        rotation = options != nil && options[:rotation] ? options[:rotation] : 0
        source = options != nil && options[:source] ? options[:source] : [0, 0, image.getWidth(), image.getHeight()]
        
        @module.draw(image, position, size, origin, scale, source, rotation)
    end
    
    def print(text, options = nil)
        align = options != nil && options[:align] ? options[:align] : :left
        position = options != nil && options[:position] ? options[:position] : [0, 0]
        scale = options != nil && options[:scale] ? options[:scale] : [1, 1]
        wrap = options != nil && options[:wrap] ? options[:wrap] : @module.measureText(text).width
        
        @module.print(text, position, scale, wrap, align)
    end
    
    def fill(shape, mode = :line)
        @module.fill(shape, mode)
    end
end