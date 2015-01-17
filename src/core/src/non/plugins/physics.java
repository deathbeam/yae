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
        public final Filter filter = new Filter();
    }
    
    public class JointDefinition {
        public String type = "unknown";
        public Body a;
        public Body b;
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
        jointDef.bodyA = def.a;
        jointDef.bodyB = def.b;
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
        fixtureDef.filter.categoryBits = def.filter.categoryBits;
        fixtureDef.filter.maskBits = def.filter.maskBits;
        fixtureDef.filter.groupIndex = def.filter.groupIndex;
        
        if (s!=null) {
            fixtureDef.shape = s;
            s.dispose();
        }
        
        return body.createFixture(fixtureDef);
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