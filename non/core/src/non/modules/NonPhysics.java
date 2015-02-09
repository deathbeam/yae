package non.modules;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.utils.Array;
import org.mozilla.javascript.Function;

import non.Non;
import non.Line;

public class NonPhysics extends Module {
    public class BodyDefinition {
        public String type = "static";
        public Vector2 position = new Vector2();
        public float angle = 0;
        public Vector2 linearVelocity = new Vector2();
        public float angularVelocity = 0;
        public float linearDamping = 0;
        public float angularDamping = 0;
        public boolean fixedRotation = false;
        public boolean bullet = false;
        public boolean active = true;
        public float gravityScale = 1;
    }
    
    public class FixtureDefinition {
        public Shape2D shape;
        public float friction = 0.2f;
        public float restitution = 0;
        public float density = 0;
        public boolean isSensor = false;
    }
    
    public class JointDefinition {
        public String type = "unknown";
        public Body bodyA;
        public Body bodyB;
    }
    
    public class ScriptContactListener implements ContactListener {
        public void beginContact(Contact contact) {
            Non.script.call(beginContact, contact);
        }
            
        public void endContact(Contact contact) {
            Non.script.call(endContact, contact);
        }
            
        public void preSolve(Contact contact, Manifold oldManifold) {
            Non.script.call(preSolve, contact, oldManifold);
        }
            
        public void postSolve(Contact contact, ContactImpulse impulse) {
            Non.script.call(postSolve, contact, impulse);
        }
    }
        
    private World world;
    private Box2DDebugRenderer renderer;
    private boolean debug;
    private float step, accum, ppt, speed;
    
    public Function beginContact, endContact, preSolve, postSolve;
    
    public World getWorld() { return world; }
    public Vector2 getGravity() { return new Vector2(world.getGravity().x, -world.getGravity().y); }
    public NonPhysics setGravity(float x, float y) { world.setGravity(new Vector2(x,-y)); return this; }
    public NonPhysics setStep(float step) { this.step = step; return this; }
    public NonPhysics setDebug(boolean debug) { this.debug = debug; return this; }
    public NonPhysics setSpeed(float speed) { this.speed = speed; return this; }
    
    public NonPhysics() {
        debug = false;
        step = 1 / 60f;
        accum = 0;
        ppt = 1;
        speed = 1;
        
        Box2D.init();
        world = new World(new Vector2(), true);
        world.setContactListener(new ScriptContactListener());
    }
    
    public void dispose() {
        if (world != null) world.dispose();
        if (renderer != null) renderer.dispose();
    }
    
    public void update(float dt) {
        accum += dt * speed;
        while (accum >= step) {
            world.step(step, 6, 2);
            accum -= step;
        }
    }
    
    public void draw(NonGraphics graphics) {
        if (!debug) return;
        
        if (renderer != null) {
            renderer.render(world, graphics.getBatch().getProjectionMatrix());
        } else {
            renderer = new Box2DDebugRenderer();
        }
    }
    
    public Array<Joint> joints() {
        Array<Joint> joints = new Array();
        world.getJoints(joints);
        return joints;
    }
    
    public JointDefinition jointDef() {
        return new JointDefinition();
    }
    
    public Joint joint() {
        return joint(jointDef());
    }
    
    public Joint joint(JointDefinition def) {
        JointDef jointDef = new JointDef();
        
        jointDef.type = jointType(def.type);
        jointDef.bodyA = def.bodyA;
        jointDef.bodyB = def.bodyB;
        return world.createJoint(jointDef);
    }
    
    public Array<Body> bodies() {
        Array<Body> bodies = new Array();
        world.getBodies(bodies);
        return bodies;
    }
    
    public BodyDefinition bodyDef() {
        return new BodyDefinition();
    }
    
    public Body body() {
        return body(bodyDef());
    }
    
    public Body body(BodyDefinition def) {
        BodyDef bodyDef = new BodyDef();
        
        bodyDef.type = bodyType(def.type);
        bodyDef.position.set(def.position);
        bodyDef.angle = def.angle;
        bodyDef.linearVelocity.set(def.linearVelocity);
        bodyDef.angularVelocity = def.angularVelocity;
        bodyDef.linearDamping = def.linearDamping;
        bodyDef.angularDamping = def.angularDamping;
        bodyDef.fixedRotation = def.fixedRotation;
        bodyDef.bullet = def.bullet;
        bodyDef.active = def.active;
        bodyDef.gravityScale = def.gravityScale;
        
        return world.createBody(bodyDef);
    }
    
    public Array<Fixture> fixtures() {
        Array<Fixture> fixtures = new Array();
        world.getFixtures(fixtures);
        return fixtures;
    }
    
    public FixtureDefinition fixtureDef() {
        return new FixtureDefinition();
    }
    
    public Fixture fixture(Body body) {
        return fixture(body, fixtureDef());
    }
    
    public Fixture fixture(Body body, FixtureDefinition def) {
        Shape2D shape = def.shape;
        Shape s = null;
        
        if (shape instanceof Rectangle) {
            Rectangle cur = (Rectangle)shape;
            s = new PolygonShape();
            ((PolygonShape)s).setAsBox(cur.width, cur.height, new Vector2(cur.x, cur.y), 0);
        } else if (shape instanceof Circle) {
            Circle cur = (Circle)shape;
            s = new CircleShape();
            ((CircleShape)s).setPosition(new Vector2(cur.x, cur.y));
            ((CircleShape)s).setRadius(cur.radius);
        } else if (shape instanceof Polygon) {
            Polygon cur = (Polygon)shape;
            s = new PolygonShape();
            ((PolygonShape)s).set(cur.getVertices()); 
        } else if (shape instanceof Line) {
            Line cur = (Line)shape;
            s = new EdgeShape();
            ((EdgeShape)s).set(cur.x1, cur.y1, cur.x2, cur.y2); 
        }
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = def.density;
        fixtureDef.friction = def.friction;
        fixtureDef.restitution = def.restitution;
        fixtureDef.isSensor = def.isSensor;
        
        if (s!=null) {
            fixtureDef.shape = s;
        }
        
        return body.createFixture(fixtureDef);
    }
    
    public void queryAABB(Rectangle area, final Function callback) {
        world.QueryAABB(new QueryCallback() {
            public boolean reportFixture(Fixture fixture) {
                Object result = Non.script.call(callback, fixture);
                
                if (result instanceof Boolean) {
                    return (Boolean)result;
                } else {
                    return false;
                }
            }
        }, area.x, area.y + area.height, area.x + area.width, area.y);
    }
    
    public BodyType bodyType(String type) {
        if (type.equalsIgnoreCase("dynamic")) return BodyType.DynamicBody;
        else if (type.equalsIgnoreCase("static")) return BodyType.StaticBody;
        else if (type.equalsIgnoreCase("kinematic")) return BodyType.KinematicBody;
        return BodyType.StaticBody;
    }
    
    public JointType jointType(String type) {
        if (type.equalsIgnoreCase("revolute")) return JointType.RevoluteJoint;
        else if (type.equalsIgnoreCase("prismatic")) return JointType.PrismaticJoint;
        else if (type.equalsIgnoreCase("distance")) return JointType.DistanceJoint;
        else if (type.equalsIgnoreCase("pulley")) return JointType.PulleyJoint;
        else if (type.equalsIgnoreCase("mouse")) return JointType.MouseJoint;
        else if (type.equalsIgnoreCase("gear")) return JointType.GearJoint;
        else if (type.equalsIgnoreCase("wheel")) return JointType.WheelJoint;
        else if (type.equalsIgnoreCase("weld")) return JointType.WeldJoint;
        else if (type.equalsIgnoreCase("friction")) return JointType.FrictionJoint;
        else if (type.equalsIgnoreCase("rope")) return JointType.RopeJoint;
        else if (type.equalsIgnoreCase("motor")) return JointType.MotorJoint;
        return JointType.Unknown;
    }
    
    public DistanceJointDef distanceJointDef() {
        return new DistanceJointDef();
    }
    
    public DistanceJoint distanceJoint(DistanceJointDef def) {
        return (DistanceJoint)world.createJoint(def);
    }
    
    public FrictionJointDef frictionJointDef() {
        return new FrictionJointDef();
    }
    
    public FrictionJoint frictionJoint(FrictionJointDef def) {
        return (FrictionJoint)world.createJoint(def);
    }
    
    public GearJointDef gearJointDef() {
        return new GearJointDef();
    }
    
    public GearJoint gearJoint(GearJointDef def) {
        return (GearJoint)world.createJoint(def);
    }
    
    public MotorJointDef motorJointDef() {
        return new MotorJointDef();
    }
    
    public MotorJoint motorJoint(MotorJointDef def) {
        return (MotorJoint)world.createJoint(def);
    }
    
    public MouseJointDef mouseJointDef() {
        return new MouseJointDef();
    }
    
    public MouseJoint mouseJoint(MouseJointDef def) {
        return (MouseJoint)world.createJoint(def);
    }
    
    public PrismaticJointDef prismaticJointDef() {
        return new PrismaticJointDef();
    }
    
    public PrismaticJoint prismaticJoint(PrismaticJointDef def) {
        return (PrismaticJoint)world.createJoint(def);
    }
    
    public PulleyJointDef pulleyJointDef() {
        return new PulleyJointDef();
    }
    
    public PulleyJoint pulleyJoint(PulleyJointDef def) {
        return (PulleyJoint)world.createJoint(def);
    }
    
    public RevoluteJointDef revoluteJointDef() {
        return new RevoluteJointDef();
    }
    
    public RevoluteJoint revoluteJoint(RevoluteJointDef def) {
        return (RevoluteJoint)world.createJoint(def);
    }
    
    public RopeJointDef ropeJointDef() {
        return new RopeJointDef();
    }
    
    public RopeJoint ropeJoint(RopeJointDef def) {
        return (RopeJoint)world.createJoint(def);
    }
    
    public WeldJointDef weldJointDef() {
        return new WeldJointDef();
    }
    
    public WeldJoint weldJoint(WeldJointDef def) {
        return (WeldJoint)world.createJoint(def);
    }
    
    public WheelJointDef wheelJointDef() {
        return new WheelJointDef();
    }
    
    public WheelJoint wheelJoint(WheelJointDef def) {
        return (WheelJoint)world.createJoint(def);
    }
    
    public NonPhysics destroy(Joint joint) {
        world.destroyJoint(joint);
        return this;
    }
    
    public NonPhysics destroy(Body body) {
        world.destroyBody(body);
        return this;
    }
}