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
    protected World world;
    private Box2DDebugRenderer renderer;
    private float step, accum, ppt, speed;
    
    public World getWorld() { return world; }
    public Vector2 getGravity() { return new Vector2(world.getGravity().x, -world.getGravity().y); }
    public NonPhysics setGravity(float x, float y) { world.setGravity(new Vector2(x,-y)); return this; }
    public NonPhysics setStep(float step) { this.step = step; return this; }
    public NonPhysics setSpeed(float speed) { this.speed = speed; return this; }
    
    public NonPhysics() {
        step = 1 / 60f;
        accum = 0;
        ppt = 1;
        speed = 1;
        
        Box2D.init();
        world = new World(new Vector2(), true);
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
    
    public NonPhysics destroy(Joint joint) {
        world.destroyJoint(joint);
        return this;
    }
    
    public NonPhysics destroy(Body body) {
        world.destroyBody(body);
        return this;
    }
}