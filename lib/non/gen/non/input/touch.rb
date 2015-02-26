module Touch
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("touch")
    
    def self.getX(pointer = 0)
        Module.getX(pointer)
    end
    
    def self.getY(pointer = 0)
        Module.getY(pointer)
    end
    
    def self.getPosition(pointer = 0)
        Module.getPosition(pointer)
    end
    
    def self.isDown(pointer = 0)
        Module.isDown(pointer)
    end
end