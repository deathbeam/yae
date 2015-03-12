class Particles
    java_import 'com.badlogic.gdx.graphics.g2d.ParticleEffect'
    java_import 'non.Non'
    java_import 'non.ModuleHandler'
    
    def initialize
        @module = ModuleHandler.get("particles")
        @module.setGraphics(ModuleHandler.get("graphics"))
    end
    
    def effect(path)
        if Non.assets.isLoaded(file)
            return Non.assets.get(file, ParticleEffect.java_class)
        end
        
        particle = ParticleEffect.new
        particle.load(Non.file(file), Non.file("."))
        return particle
    end
    
    def draw(particle)
        Module.draw(particle)
    end
end