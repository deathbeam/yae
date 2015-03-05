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
    
    def self.body(options = nil)
        type = options != nil && options[:type] ? options[:type] : :static
        position = options != nil && options[:position] ? options[:position] : [0, 0]
        linearVelocity = options != nil && options[:linearVelocity] ? options[:linearVelocity] : [0, 0]
        angle = options != nil && options[:angle] ? options[:angle] : 0
        angularVelocity = options != nil && options[:angularVelocity] ? options[:angularVelocity] : 0
        linearDamping = options != nil && options[:linearDamping] ? options[:linearDamping] : 0
        angularDamping = options != nil && options[:angularDamping] ? options[:angularDamping] : 0
        gravityScale = options != nil && options[:gravityScale] ? options[:gravityScale] : 1
        fixedRotation = options != nil && options[:fixedRotation] ? options[:fixedRotation] : false
        bullet = options != nil && options[:bullet] ? options[:bullet] : false
        active = options != nil && options[:active] ? options[:active] : true
        
        Module.body(type, position, linearVelocity, angle, angularVelocity, linearDamping, angularDamping, gravityScale, fixedRotation, bullet, active)
    end

    def self.fixture(body, options = nil)
        shape = options != nil && options[:shape] ? options[:shape] : nil
        density = options != nil && options[:density] ? options[:density] : 0
        friction = options != nil && options[:friction] ? options[:friction] : 0.2
        restitution = options != nil && options[:restitution] ? options[:restitution] : 0
        sensor = options != nil && options[:sensor] ? options[:sensor] : false
        
        Module.fixture(body, shape, density, friction, restitution, sensor)
    end
end