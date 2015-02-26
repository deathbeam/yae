module Mouse
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("mouse")
    
    def self.show
        Module.setVisible(true)
    end
    
    def self.hide
        Module.setVisible(false)
    end
    
    def self.setVisible(val)
        Module.setVisible(val)
    end
    
    def self.getX
        Module.getX()
    end
    
    def self.getY
        Module.getY()
    end
    
    def self.getPosition
        Module.getPosition()
    end
    
    def self.setPosition(x, y)
        Module.setPosition(x, y)
    end
    
    def self.isDown(button = "left")
        Module.isDown(button)
    end
end