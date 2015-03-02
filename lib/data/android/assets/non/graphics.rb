module Graphics
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("graphics")
    
    def self.image(path)
        Module.image(path)
    end
    
    def self.font(path)
        Module.font(path)
    end
    
    def self.shader(vertex, fragment)
        Module.shader(vertex, fragment)
    end
    
    def self.color(r, g, b, a = 1)
        Module.color(r, g, b, a)
    end
    
    def self.project(x, y)
        Module.project(x, y)
    end
    
    def self.unproject(x, y)
        Module.unproject(x, y)
    end
    
    def self.rotate(degrees, x = 0, y = 0, z = 0)
        Module.rotate(degrees, x, y, z)
    end
    
    def self.scale(factor)
        Module.scale(factor)
    end
    
    def self.translate(x, y)
        Module.translate(x, y)
    end
    
    def self.clear(r, g, b, a = 1)
        Module.clear(r, g, b, a)
    end
    
    def self.setBlending(blending)
        Module.setBlending(blending)
    end
    
    def self.setShader(shader)
        Module.setShader(shader)
    end
    
    def self.setFont(font)
        Module.setFont(font)
    end
    
    def self.setColor(r, g, b, a = 1)
        Module.setColor(r, g, b, a)
    end
    
    def self.getColor()
        Module.getColor()
    end
    
    def self.draw(options)
        image = options[:image] ? options[:image] : nil
        position = options[:position] ? options[:position] : [0, 0]
        size = options[:size] ? options[:size] : [image.getWidth(), image.getHeight()]
        origin = options[:origin] ? options[:origin] : [0, 0]
        scale = options[:scale] ? options[:scale] : [1, 1]
        rotation = options[:rotation] ? options[:rotation] : 0
        source = options[:source] ? options[:source] : [0, 0, image.getWidth(), image.getHeight()]
        
        Module.draw(image, position, size, origin, scale, source, rotation)
    end
    
    def self.print(options)
        text = options[:text] ? options[:text] : nil
        align = options[:align] ? options[:align] : :left
        position = options[:position] ? options[:position] : [0, 0]
        scale = options[:scale] ? options[:scale] : [1, 1]
        wrap = options[:wrap] ? options[:wrap] : Module.measureText(text).width
        
        Module.print(text, position, scale, wrap, align)
    end
    
    def self.fill(options)
        shape = options[:shape] ? options[:shape] : null
        mode = options[:mode] ? options[:mode] : :line
        
        Module.fill(shape, mode)
    end
end