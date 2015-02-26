module Accelerometer
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("accelerometer")
    
    def self.getX
        Module.getX()
    end
    
    def self.getY
        Module.getY()
    end
    
    def self.getZ
        Module.getZ()
    end
    
    def self.getRotation
        Module.getRotation()
    end
end