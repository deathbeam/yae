module Lights
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("lights")
    defined?(Graphics) ? Module.setPhysics(Graphics::Module) : Module.setPhysics(ModuleHandler.get("graphics"))
    defined?(Physics) ? Module.setPhysics(Physics::Module) : Module.setPhysics(ModuleHandler.get("physics"))
    
    def self.setGammaCorrection(bool)
        Module.setGammaCorrection(bool)
    end
    
    def self.setDiffuseLight(bool)
        Module.setDiffuseLight(bool)
    end
    
    def self.setAmbientColor(r, g, b, a = 1)
        Module.setAmbientColor(r, g, b, a)
    end
    
    def self.setBlur(bool)
        Module.setBlur(bool)
    end
    
    def self.setBlurNum(num)
        Module.setBlurNum(num)
    end
    
    def self.setShadows(bool)
        Module.setShadows(bool)
    end
   
    def self.setCulling(bool)
        Module.setCulling(bool)
    end
    
    def self.pointAtLight(x, y)
        Module.pointAtLight(x, y)
    end
    
    def self.pointAtShadow(x, y)
        Module.pointAtShadow(x, y)
    end
    
    def self.directional(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        direction = options[:direction] ? options[:direction] : 0
        
        Module.directional(rays, color, direction)
    end
    
    def self.point(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        position = options[:position] ? options[:position] : [0, 0]
        
        Module.point(rays, color, distance, position[0], position[1])
    end
    
    def self.cone(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        position = options[:position] ? options[:position] : [0, 0]
        direction = options[:direction] ? options[:direction] : 0
        cone = options[:cone] ? options[:cone] : 20
        
        Module.cone(rays, color, distance, position[0], position[1], direction, cone)
    end
    
    def self.chain(options)
        rays = options[:rays] ? options[:rays] : 100
        color = options[:color] ? options[:color] : [1, 1, 1, 0.75]
        distance = options[:distance] ? options[:distance] : 100
        orientation = options[:orientation] ? options[:orientation] : "left"
        chains = options[:chains] ? options[:chains] : nil
        
        if orientation == "right"
            Module.chain(rays, color, distance, -1, chains)
        else
            Module.chain(rays, color, distance, 1, chains)
        end
    end
end