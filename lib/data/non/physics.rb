class Physics
    java_import "non.ModuleHandler"
    java_import "com.badlogic.gdx.math.Vector2"
    include_package "com.badlogic.gdx.physics.box2d.joints"
    
    def initialize
        @module = ModuleHandler.get("physics")
        @module.setGraphics(ModuleHandler.get("graphics"))
    end
    
    def set_gravity(x, y)
        @module.setGravity(x, y)
    end
    
    def get_gravity
        @module.getGravity()
    end
    
    def set_step(step)
        @module.setStep(step)
    end
    
    def set_speed(speed)
        @module.setSpeed(speed)
    end
    
    def bodies
        @module.bodies()
    end
    
    def contacts
        @module.contacts()
    end
    
    def fixtures
        @module.fixtures()
    end
    
    def joints
        @module.joints()
    end
    
    def destroy(object)
        @module.destroy(object)
    end
    
    def body(options = nil)
        type = options != nil && options[:type] ? options[:type] : :static
        position = options != nil && options[:position] ? options[:position] : [0, 0]
        linearVelocity = options != nil && options[:linear_velocity] ? options[:linear_velocity] : [0, 0]
        angle = options != nil && options[:angle] ? options[:angle] : 0
        angularVelocity = options != nil && options[:angular_velocity] ? options[:angular_velocity] : 0
        linearDamping = options != nil && options[:linear_damping] ? options[:linear_damping] : 0
        angularDamping = options != nil && options[:angular_damping] ? options[:angular_damping] : 0
        gravityScale = options != nil && options[:gravity_scale] ? options[:gravity_scale] : 1
        fixedRotation = options != nil && options[:fixed_rotation] ? options[:fixed_rotation] : false
        bullet = options != nil && options[:bullet] ? options[:bullet] : false
        active = options != nil && options[:active] ? options[:active] : true
        
        @module.body(type, position, linearVelocity, angle, angularVelocity, linearDamping, angularDamping, gravityScale, fixedRotation, bullet, active)
    end

    def fixture(body, options = nil)
        shape = options != nil && options[:shape] ? options[:shape] : nil
        density = options != nil && options[:density] ? options[:density] : 0
        friction = options != nil && options[:friction] ? options[:friction] : 0.2
        restitution = options != nil && options[:restitution] ? options[:restitution] : 0
        sensor = options != nil && options[:sensor] ? options[:sensor] : false
        
        @module.fixture(body, shape, density, friction, restitution, sensor)
    end
    
    def distance_joint(bodyA, bodyB, options = nil)
        new_def = DistanceJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchor_a = options != nil && options[:anchor_a] ? options[:anchor_a] : [0, 0]
        anchor_b = options != nil && options[:anchor_b] ? options[:anchor_b] : [0, 0]
        new_def.frequencyHz = options != nil && options[:frequency_hz] ? options[:frequency_hz] : 0
        new_def.dampingRatio = options != nil && options[:damping_ratio] ? options[:damping_ratio] : 0
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor_a[0], anchor_a[1]), Vector2.new(anchor_b[0], anchor_b[1]))
        @module.get_world.create_joint(new_def)
    end
    
    def friction_joint(bodyA, bodyB, options = nil)
        new_def = FrictionJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false     
        anchor = options != nil && options[:anchor] ? options[:anchor] : [0, 0]
        new_def.maxForce = options != nil && options[:max_force] ? options[:max_force] : 0
        new_def.maxTorque = options != nil && options[:max_torque] ? options[:max_torque] : 0
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor[0], anchor[1]))
        @module.get_world.create_joint(new_def)
    end
    
    def gear_joint(jointA, jointB, options = nil)
        new_def = GearJointDef.new
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        new_def.joint1 = jointA
        new_def.joint2 = jointB
        new_def.ratio = options != nil && options[:ratio] ? options[:ratio] : 1
        @module.get_world.create_joint(new_def)
    end
    
    def motor_joint(bodyA, bodyB, options = nil)
        new_def = MotorJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        new_def.maxForce = options != nil && options[:max_force] ? options[:max_force] : 1
        new_def.maxTorque = options != nil && options[:max_torque] ? options[:max_torque] : 1
        new_def.correctionFactor = options != nil && options[:correction_factor] ? options[:correction_factor] : 0.3
        new_def.initialize(bodyA, bodyB)
        @module.get_world.create_joint(new_def)
    end
    
    def mouse_joint(bodyA, bodyB, options = nil)
        new_def = MouseJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        target = options != nil && options[:target] ? options[:target] : [0, 0]
        new_def.maxForce = options != nil && options[:max_force] ? options[:max_force] : 0
        new_def.frequencyHz = options != nil && options[:frequency_hz] ? options[:frequency_hz] : 5
        new_def.dampingRatio = options != nil && options[:damping_ratio] ? options[:damping_ratio] : 0.7
        new_def.target.set(target[0], target[1])
        @module.get_world.create_joint(new_def)
    end
    
    def prismatic_joint(bodyA, bodyB, options = nil)
        new_def = PrismaticJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchor = options != nil && options[:anchor] ? options[:anchor] : [0, 0]
        axis = options != nil && options[:axis] ? options[:axis] : [0, 0]
        new_def.enableLimit = options != nil && options[:enable_limit] ? options[:enable_limit] : false
        new_def.lowerTranslation = options != nil && options[:lower_translation] ? options[:lower_translation] : 0
        new_def.upperTranslation = options != nil && options[:upper_translation] ? options[:upper_translation] : 0
        new_def.enableMotor = options != nil && options[:enable_motor] ? options[:enable_motor] : false
        new_def.maxMotorForce = options != nil && options[:max_motor_force] ? options[:max_motor_force] : 0
        new_def.motorSpeed = options != nil && options[:motor_speed] ? options[:motor_speed] : 0
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor[0], anchor[1]), Vector2.new(axis[0], axis[1]))
        @module.get_world.create_joint(new_def)
    end
    
    def pulley_joint(bodyA, bodyB, options = nil)
        new_def = PulleyJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        groupAnchorA = options != nil && options[:group_anchor_a] ? options[:group_anchor_a] : [-1, 1]
        groupAnchorB = options != nil && options[:group_anchor_b] ? options[:group_anchor_b] : [1, 1]
        anchorA = options != nil && options[:anchor_a] ? options[:anchor_a] : [-1, 0]
        anchorB = options != nil && options[:anchor_b] ? options[:anchor_b] : [1, 0]
        ratio = options != nil && options[:ratio] ? options[:ratio] : 1
        new_def.initialize(bodyA, bodyB, Vector2.new(groupAnchorA[0], groupAnchorA[1]),
            Vector2.new(groupAnchorB[0], groupAnchorB[1]),
            Vector2.new(anchorA[0], anchorA[1]),
            Vector2.new(anchorB[0], anchorB[1]), ratio)
        @module.get_world.create_joint(new_def)
    end
    
    def revolute_joint(bodyA, bodyB, options = nil)
        new_def = RevoluteJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchor = options != nil && options[:anchor] ? options[:anchor] : [0, 0]
        axis = options != nil && options[:axis] ? options[:axis] : [0, 0]
        new_def.enableLimit = options != nil && options[:enable_limit] ? options[:enable_limit] : false
        new_def.lowerAngle = options != nil && options[:lower_angle] ? options[:lower_angle] : 0
        new_def.upperAngle = options != nil && options[:upper_angle] ? options[:upper_angle] : 0
        new_def.enableMotor = options != nil && options[:enable_motor] ? options[:enable_motor] : false
        new_def.maxMotorTorque = options != nil && options[:max_motor_torque] ? options[:max_motor_torque] : 0
        new_def.motorSpeed = options != nil && options[:motor_speed] ? options[:motor_speed] : 0
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor[0], anchor[1]))
        @module.get_world.create_joint(new_def)
    end
    
    def rope_joint(bodyA, bodyB, options = nil)
        new_def = RopeJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchorA = options != nil && options[:anchor_a] ? options[:anchor_a] : [0, 0]
        anchorB = options != nil && options[:anchor_b] ? options[:anchor_b] : [0, 0]
        new_def.maxLength = options != nil && options[:max_length] ? options[:max_length] : 0
        new_def.localAnchorA.set(anchorA[0], anchorA[1])
        new_def.localAnchorB.set(anchorB[0], anchorB[1])
        @module.get_world.create_joint(new_def)
    end
    
    def weld_joint(bodyA, bodyB, options = nil)
        new_def = WeldJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchor = options != nil && options[:anchor] ? options[:anchor] : [0, 0]
        new_def.frequencyHz = options != nil && options[:frequency_hz] ? options[:frequency_hz] : 0
        new_def.dampingRatio = options != nil && options[:damping_ratio] ? options[:damping_ratio] : 0
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor[0], anchor[1]))
        @module.get_world.create_joint(new_def)
    end
    
    def wheel_joint(bodyA, bodyB, options = nil)
        new_def = WheelJointDef.new
        new_def.bodyA = bodyA
        new_def.bodyB = bodyB
        new_def.collide_connected = options != nil && options[:collide_connected] ? options[:collide_connected] : false
        anchor = options != nil && options[:anchor] ? options[:anchor] : [0, 0]
        axis = options != nil && options[:axis] ? options[:axis] : [0, 0]
        new_def.enableMotor = options != nil && options[:enable_motor] ? options[:enable_motor] : false
        new_def.maxMotorTorque = options != nil && options[:max_motor_torque] ? options[:max_motor_torque] : 0
        new_def.motorSpeed = options != nil && options[:motor_speed] ? options[:motor_speed] : 0
        new_def.frequencyHz = options != nil && options[:frequency_hz] ? options[:frequency_hz] : 2
        new_def.dampingRatio = options != nil && options[:damping_ratio] ? options[:damping_ratio] : 0.7
        new_def.initialize(bodyA, bodyB, Vector2.new(anchor[0], anchor[1]), Vector2.new(axis[0], axis[1]))
        @module.get_world.create_joint(new_def)
    end
end