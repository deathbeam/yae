package non.plugins;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.utils.Array;

import non.Non;
import non.Line;

public class physics extends Plugin {
    public String author()         { return "Thomas Slusny"; }
    public String license()        { return "MIT"; }
    public String description()    { return "Plugin for handling physics."; }
    public String[] dependencies() { return new String[] { "graphics", "math" }; }
    
    public class Definition {
        public float angle = 0;
        public Vector2 linearVelocity = new Vector2();
        public float angularVelocity = 0;
        public float linearDamping = 0;
        public float angularDamping = 0;
        public boolean fixedRotation = false;
        public boolean bullet = false;
        public boolean active = true;
        public float gravityScale = 1;
        
        public float friction = 0.2f;
        public float restitution = 0;
        public float density = 0;
    }
    
    public class ScriptContactListener implements ContactListener {
        public void beginContact(Contact contact) {
            Non.script.invoke("physics", "beginContact", contact);
        }
            
        public void endContact(Contact contact) {
            Non.script.invoke("physics", "endContact", contact);
        }
            
        public void preSolve(Contact contact, Manifold oldManifold) {
            Non.script.invoke("physics", "preSolve", contact, oldManifold);
        }
            
        public void postSolve(Contact contact, ContactImpulse impulse) {
            Non.script.invoke("physics", "postSolve", contact, impulse);
        }
    }
        
    private World world;
    private Box2DDebugRenderer renderer;
    private boolean debug;
    private float step, accum, ppt, speed;
    public Object beginContact, endContact, preSolve, postSolve;
    
    public World getWorld() { return world; }
    public Vector2 getGravity() { return new Vector2(world.getGravity().x, -world.getGravity().y); }
    public physics setGravity(float x, float y) { world.setGravity(new Vector2(x,-y)); return this; }
    public physics setStep(float step) { this.step = step; return this; }
    public physics setDebug(boolean debug) { this.debug = debug; return this; }
    public physics setSpeed(float speed) { this.speed = speed; return this; }
    
    public void plugin_load() {
        debug = false;
        step = 1 / 60f;
        accum = 0;
        ppt = 1;
        speed = 1;
        
        Box2D.init();
        world = new World(new Vector2(), true);
        world.setContactListener(new ScriptContactListener());
    }
    
    public void plugin_unload() {
        if (world != null) world.dispose();
        if (renderer != null) renderer.dispose();
    }
    
    public void plugin_update_before() {
        accum += Non.getDelta() * speed;
        while (accum >= step) {
            world.step(step, 6, 2);
            accum -= step;
        }
    }
    
    public void plugin_update_after() {
        if (!debug) return;
        graphics graphics = (graphics)Plugin.get("graphics");
        if (renderer != null)
            renderer.render(world, graphics.getBatch().getProjectionMatrix());
        else
            renderer = new Box2DDebugRenderer();
    }
    
    public Array<Body> bodies() {
        Array<Body> bodies = new Array();
        world.getBodies(bodies);
        return bodies;
    }
    
    public Array<Joint> joints() {
        Array<Joint> joints = new Array();
        world.getJoints(joints);
        return joints;
    }
    
    public Joint joint(String type, Body a, Body b) {
        JointDef jointDef = new JointDef();
        
        jointDef.type = jointType(type);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        return world.createJoint(jointDef);
    }
    
    public Definition definition() {
        return new Definition();
    }
    
    public Body body(String type, Shape2D shape) {
        return body(type, shape, new Definition());
    }
    
    public Body body(String type, Shape2D shape, Definition def) {
        BodyDef bodyDef = new BodyDef();
        
        bodyDef.type = bodyType(type);
        bodyDef.angle = def.angle;
        bodyDef.linearVelocity.x = def.linearVelocity.x;
        bodyDef.linearVelocity.y = def.linearVelocity.y;
        bodyDef.angularVelocity = def.angularVelocity;
        bodyDef.linearDamping = def.linearDamping;
        bodyDef.angularDamping = def.angularDamping;
        bodyDef.fixedRotation = def.fixedRotation;
        bodyDef.bullet = def.bullet;
        bodyDef.active = def.active;
        bodyDef.gravityScale = def.gravityScale;
        
        Shape s = null;
        
        if (shape instanceof Rectangle) {
            Rectangle cur = (Rectangle)shape;
            bodyDef.position.set(cur.x, cur.y);
            s = new PolygonShape();
            ((PolygonShape)s).setAsBox(cur.width, cur.height);
        } else if (shape instanceof Circle) {
            Circle cur = (Circle)shape;
            bodyDef.position.set(cur.x, cur.y);
            s = new CircleShape();
            ((CircleShape)s).setRadius(cur.radius);
        } else if (shape instanceof Polygon) {
            Polygon cur = (Polygon)shape;
            bodyDef.position.set(cur.getX(), cur.getY());
            s = new PolygonShape();
            ((PolygonShape)s).set(cur.getVertices()); 
        } else if (shape instanceof Line) {
            Line cur = (Line)shape;
            bodyDef.position.set(cur.x1, cur.y1);
            s = new EdgeShape();
            ((EdgeShape)s).set(cur.x1, cur.y1, cur.x2, cur.y2); 
        }
        
        if (s!=null) {
            Body body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = def.density;
            fixtureDef.friction = def.friction;
            fixtureDef.restitution = def.restitution;
            fixtureDef.shape = s;
            body.createFixture(fixtureDef);
            s.dispose();
            return body;
        }
        
        return null;
    }
    
    private BodyType bodyType(String type) {
        if (type.equalsIgnoreCase("dynamic")) return BodyType.DynamicBody;
        else if (type.equalsIgnoreCase("static")) return BodyType.StaticBody;
        else if (type.equalsIgnoreCase("kinematic")) return BodyType.KinematicBody;
        return BodyType.StaticBody;
    }
    
    private JointType jointType(String type) {
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
}