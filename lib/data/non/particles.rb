module Particles
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("particles")
    defined?(Graphics) ? Module.setGraphics(Graphics::Module) : Module.setGraphics(ModuleHandler.get("graphics"))
    
    def self.load(path)
        Module.load(path)
    end
    
    def self.draw(particle)
        Module.draw(particle)
    end
end