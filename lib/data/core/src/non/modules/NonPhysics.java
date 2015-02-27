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

import non.Non;
import non.Line;

public class NonPhysics extends Module {
    public class ScriptContactListener implements ContactListener {
        public void beginContact(Contact contact) {
            Non.script.callMethod(Non.receiver, "begincontact, contact);
        }
            
        public void endContact(Contact contact) {
            Non.script.callMethod(Non.receiver, "endcontact", contact);
        }
            
        public void preSolve(Contact contact, Manifold oldManifold) {
            Non.script.callMethod(Non.receiver, "presolve", contact, oldManifold);
        }
            
        public void postSolve(Contact contact, ContactImpulse impulse) {
            Non.script.callMethod(Non.receiver, "postsolve", contact, impulse);
        }
    }
    
    private final World world;
    private NonGraphics graphics;
    private Box2DDebugRenderer renderer;
    private float step, accum, ppt, speed;
    
    public World getWorld() {
        return world;
    }
    
    public void setGraphics(NonGraphics graphics) {
        this.graphics = graphics;
    }
    
    public Vector2 getGravity() {
        return world.getGravity();
    }
    
    public void setGravity(float x, float y) {
        world.setGravity(new Vector2(x,y));
    }
    
    public void setStep(float step) {
        this.step = step;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public NonPhysics() {
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
    
    public void draw() {
        if (renderer != null) {
            renderer.render(world, graphics.getBatch().getProjectionMatrix());
        } else {
            renderer = new Box2DDebugRenderer();
        }
    }
    
    public Array<Body> bodies() {
        Array<Body> bodies = new Array();
        world.getBodies(bodies);
        return bodies;
    }
    
    public Array<Fixture> fixtures() {
        Array<Fixture> fixtures = new Array();
        world.getFixtures(fixtures);
        return fixtures;
    }
    
    public Array<Joint> joints() {
        Array<Joint> joints = new Array();
        world.getJoints(joints);
        return joints;
    }
    
    public Array<Contact> contacts() {
        return world.getContactList();
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
    
    public void destroy(Joint joint) {
        world.destroyJoint(joint);
    }
    
    public void destroy(Body body) {
        world.destroyBody(body);
    }
    
    public Body body(String type, float[] position, float[] linearVelocity, float angle, float angularVelocity, float linearDamping, float angularDamping, float gravityScale, boolean fixedRotation, boolean bullet, boolean active) {
        BodyDef def = new BodyDef();
        def.type = bodyType(type);
        def.position.set(position[0], position[1]);
        def.linearVelocity.set(linearVelocity[0], linearVelocity[1]);
        def.angle = angle;
        def.angularVelocity = angularVelocity;
        def.linearDamping = linearDamping;
        def.angularDamping = angularDamping;
        def.gravityScale = gravityScale;
        def.fixedRotation = fixedRotation;
        def.bullet = bullet;
        def.active = active;
        
        return world.createBody(def);
    }
    
    public Fixture fixture(Body body, Object shape, float density, float friction, float restitution, boolean sensor) {
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
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = sensor;
        
        if (s!=null) fixtureDef.shape = s;
        Fixture fixture = body.createFixture(fixtureDef);
        if (s!=null) s.dispose();
        return fixture;
    }