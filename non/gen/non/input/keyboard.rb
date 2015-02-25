module Keyboard
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("keyboard")
    
    def self.show
        Module.setVisible(true)
    end
    
    def self.hide
        Module.setVisible(false)
    end
    
    def self.setVisible(val)
        Module.setVisible(val)
    end
    
    def self.isDown(key)
        Module.isDown(key)
    end
end