module Shapes
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("shapes")
    
    def self.line(x1, y1, x2, y2)
        Module.line(x1, y1, x2, y2)
    end
    
    def self.polygon(vertices)
        Module.polygon(vertices)
    end
    
    def self.polyline(vertices)
        Module.polyline(vertices)
    end
    
    def self.vector(x, y)
        Module.vector2(x, y)
    end
    
    def self.rectangle(x, y, width, height)
        Module.rectangle(x, y, width, height)
    end
    
    def self.circle(x, y, radius)
        Module.circle(x, y, radius)
    end
    
    def self.ellipse(x, y, width, height)
        Module.ellipse(x, y, width, height)
    end
end