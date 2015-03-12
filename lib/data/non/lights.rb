class Lights
    java_import 'non.ModuleHandler'

    def initialize
        @module = ModuleHandler.get("lights")
        @module.setGraphics(ModuleHandler.get("graphics"))
        @module.setPhysics(ModuleHandler.get("physics"))
    end
    
    def set_gamma_correction(bool)
        @module.setGammaCorrection(bool)
    end
    
    def set_diffuse_light(bool)
        @module.setDiffuseLight(bool)
    end
    
    def set_ambient_color(r, g, b, a = 1)
        @module.setAmbientColor(r, g, b, a)
    end
    
    def set_blur(bool)
        @module.setBlur(bool)
    end
    
    def set_blur_num(num)
        @module.setBlurNum(num)
    end
    
    def set_shadows(bool)
        @module.setShadows(bool)
    end
   
    def set_culling(bool)
        @module.setCulling(bool)
    end
    
    def point_at_light(x, y)
        @module.pointAtLight(x, y)
    end
    
    def point_at_shadow(x, y)
        @module.pointAtShadow(x, y)
    end
    
    def directional(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        direction = options[:direction] ? options[:direction] : 0
        
        @module.directional(rays, color, direction)
    end
    
    def point(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        position = options[:position] ? options[:position] : [0, 0]
        
        @module.point(rays, color, distance, position[0], position[1])
    end
    
    def cone(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        position = options[:position] ? options[:position] : [0, 0]
        direction = options[:direction] ? options[:direction] : 0
        cone = options[:cone] ? options[:cone] : 20
        
        @module.cone(rays, color, distance, position[0], position[1], direction, cone)
    end
    
    def chain(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        orientation = options[:orientation] ? options[:orientation] : "left"
        chains = options[:chains] ? options[:chains] : nil
        
        if orientation == "right"
            @module.chain(rays, color, distance, -1, chains)
        else
            @module.chain(rays, color, distance, 1, chains)
        end
    end
end