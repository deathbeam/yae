module Physics
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("physics")
    defined?(Graphics) ? Module.setGraphics(Graphics::Module) : Module.setGraphics(ModuleHandler.get("graphics"))
    
    def self.setGravity(x, y)
        Module.setGravity(x, y)
    end
    
    def self.getGravity
        Module.getGravity()
    end
    
    def self.setStep(step)
        Module.setStep(step)
    end
    
    def self.setSpeed(speed)
        Module.setSpeed(speed)
    end
    
    def self.bodies
        Module.bodies()
    end
    
    def self.contacts
        Module.contacts()
    end
    
    def self.fixtures
        Module.fixtures()
    end
    
    def self.joints
        Module.joints()
    end
    
    def self.destroy(object)
        Module.destroy(object)
    end
    
    def self.body(options)
        type = options[:type] ? options[:type] : :static
        position = options[:position] ? options[:position] : [0, 0]
        linearVelocity = options[:linearVelocity] ? options[:linearVelocity] : [0, 0]
        angle = options[:angle] ? options[:angle] : 0
        angularVelocity = options[:angularVelocity] ? options[:angularVelocity] : 0
        linearDamping = options[:linearDamping] ? options[:linearDamping] : 0
        angularDamping = options[:angularDamping] ? options[:angularDamping] : 0
        gravityScale = options[:gravityScale] ? options[:gravityScale] : 1
        fixedRotation = options[:fixedRotation] ? options[:fixedRotation] : false
        bullet = options[:bullet] ? options[:bullet] : false
        active = options[:active] ? options[:active] : true
        
        Module.body(type, position, linearVelocity, angle, angularVelocity, linearDamping, angularDamping, gravityScale, fixedRotation, bullet, active)
    end

    def self.fixture(options)
        body = options[:body] ? options[:body] : nil
        shape = options[:shape] ? options[:shape] : nil
        density = options[:density] ? options[:density] : 0
        friction = options[:friction] ? options[:friction] : 0.2
        restitution = options[:restitution] ? options[:restitution] : 0
        sensor = options[:sensor] ? options[:sensor] : false
        
        Module.fixture(body, shape, density, friction, restitution, sensor)
    end
end