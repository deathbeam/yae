module Audio
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("audio")
    
    def self.load(path)
        Module.sound(path)
    end
end