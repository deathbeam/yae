module Particles
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("particles")
    defined?(Graphics) ? Module.link(Graphics::Module) : Module.link(ModuleHandler.get("graphics"))
    
    def self.load(path)
        Module.load(path)
    end
    
    def self.draw(particle)
        Module.draw(particle)
    end
end