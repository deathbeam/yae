module Particles
    java_import 'com.badlogic.gdx.graphics.g2d.ParticleEffect'
    java_import 'non.Non'
    java_import 'non.ModuleHandler'

    Module = ModuleHandler.get("particles")
    defined?(Graphics) ? Module.setGraphics(Graphics::Module) : Module.setGraphics(ModuleHandler.get("graphics"))
    
    def self.effect(path)
        if Non.assets.isLoaded(file)
            return Non.assets.get(file, ParticleEffect.java_class)
        end
        
        particle = ParticleEffect.new
        particle.load(Non.file(file), Non.file("."))
        return particle
    end
    
    def self.draw(particle)
        Module.draw(particle)
    end
end