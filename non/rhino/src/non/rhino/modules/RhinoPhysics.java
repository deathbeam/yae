package non.rhino.modules;

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
import org.mozilla.javascript.Scriptable;

import non.Non;
import non.Line;
import non.rhino.Arguments;
import non.rhino.RhinoNon;
import non.modules.NonPhysics;

public class RhinoPhysics extends NonPhysics {
    public class ScriptContactListener implements ContactListener {
        public void beginContact(Contact contact) {
            RhinoNon.script.call(beginContact, contact);
        }
            
        public void endContact(Contact contact) {
            RhinoNon.script.call(endContact, contact);
        }
            
        public void preSolve(Contact contact, Manifold oldManifold) {
            RhinoNon.script.call(preSolve, contact, oldManifold);
        }
            
        public void postSolve(Contact contact, ContactImpulse impulse) {
            RhinoNon.script.call(postSolve, contact, impulse);
        }
    }
    
    public Function beginContact, endContact, preSolve, postSolve;
    
    public RhinoPhysics() {
        world.setContactListener(new ScriptContactListener());
    }
    
    public Body body(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        BodyDef bodyDef = new BodyDef();
        
        bodyDef.type = bodyType(args.getString("type", "static"));
        
        float[] position = args.getNumArray("position", new float[]{0,0});
        float[] linearVelocity = args.getNumArray("linearVelocity", new float[]{0,0});
        
        bodyDef.position.set(position[0], position[1]);
        bodyDef.linearVelocity.set(linearVelocity[0], linearVelocity[1]);
        bodyDef.angle = args.getNum("angle", 0f);
        bodyDef.angularVelocity = args.getNum("angularVelocity", 0f);
        bodyDef.linearDamping = args.getNum("linearDamping", 0f);
        bodyDef.angularDamping = args.getNum("angularDamping", 0f);
        bodyDef.gravityScale = args.getNum("gravityScale", 1f);
        bodyDef.fixedRotation = args.getBool("fixedRotation", false);
        bodyDef.bullet = args.getBool("bullet", false);
        bodyDef.active = args.getBool("active", true);
        
        return world.createBody(bodyDef);
    }
    
    public Fixture fixture(Body body, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        Object shape = args.get("shape", null);
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
        fixtureDef.density = args.getNum("density", 0f);
        fixtureDef.friction = args.getNum("friction", 0.2f);
        fixtureDef.restitution = args.getNum("restitution", 0f);
        fixtureDef.isSensor = args.getBool("isSensor", false);
        
        if (s!=null) fixtureDef.shape = s;
        Fixture fixture = body.createFixture(fixtureDef);
        if (s!=null) s.dispose();
        return fixture;
    }
    
    public Joint joint(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        String type = args.getString("type", "unknown");
        Body bodyA = (Body)args.get("bodyA", null);
        Body bodyB = (Body)args.get("bodyB", null);
        boolean collideConnected = args.getBool("collideConnected", false);
        
        if (type.equals("distance")) {
            DistanceJointDef newDef = new DistanceJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchorA = args.getNumArray("anchorA", new float[]{0,0});
            float[] anchorB = args.getNumArray("anchorB", new float[]{0,0});
            newDef.frequencyHz = args.getNum("frequencyHz", 0f);
            newDef.dampingRatio = args.getNum("dampingRatio", 0f);
            newDef.initialize(bodyA, bodyB, new Vector2(anchorA[0], anchorA[1]),
                new Vector2(anchorB[0], anchorB[1]));
            return (DistanceJoint)world.createJoint(newDef);
        } else if(type.equals("friction")) {
            FrictionJointDef newDef = new FrictionJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            newDef.maxForce = args.getNum("maxForce", 0f);
            newDef.maxTorque = args.getNum("maxTorque", 0f);
            newDef.initialize(bodyA, bodyB, new Vector2((float)anchor[0], (float)anchor[1]));
            return (FrictionJoint)world.createJoint(newDef);
        } else if(type.equals("gear")) {
            GearJointDef newDef = new GearJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            newDef.joint1 = (Joint)args.get("jointA", null);
            newDef.joint2 = (Joint)args.get("jointB", null);
            newDef.ratio = args.getNum("ratio", 1f);
            return (GearJoint)world.createJoint(newDef);
        } else if(type.equals("motor")) {
            MotorJointDef newDef = new MotorJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            newDef.maxForce = args.getNum("maxForce", 1f);
            newDef.maxTorque = args.getNum("maxTorque", 1f);
            newDef.correctionFactor = args.getNum("correctionFactor", 0.3f);
            newDef.initialize(bodyA, bodyB);
            return (MotorJoint)world.createJoint(newDef);
        } else if(type.equals("mouse")) {
            MouseJointDef newDef = new MouseJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] target = args.getNumArray("target", new float[]{0,0});
            newDef.maxForce = args.getNum("maxForce", 0f);
            newDef.frequencyHz = args.getNum("frequencyHz", 5f);
            newDef.dampingRatio = args.getNum("dampingRatio", 0.7f);
            newDef.target.set(target[0], target[1]);
            return (MouseJoint)world.createJoint(newDef);
        } else if(type.equals("prismatic")) {
            PrismaticJointDef newDef = new PrismaticJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            float[] axis = args.getNumArray("axis", new float[]{0,0});
            newDef.enableLimit = args.getBool("enableLimit", false);
            newDef.lowerTranslation = args.getNum("lowerTranslation", 0f);
            newDef.upperTranslation = args.getNum("upperTranslation", 0f);
            newDef.enableMotor = args.getBool("enableMotor", false);
            newDef.maxMotorForce = args.getNum("maxMotorForce", 0f);
            newDef.motorSpeed = args.getNum("motorSpeed", 0f);
            newDef.initialize(bodyA, bodyB, new Vector2(anchor[0], anchor[1]),
                new Vector2(axis[0], axis[1]));
            return (PrismaticJoint)world.createJoint(newDef);
        } else if(type.equals("pulley")) {
            PulleyJointDef newDef = new PulleyJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] groupAnchorA = args.getNumArray("groupAnchorA", new float[]{-1,1});
            float[] groupAnchorB = args.getNumArray("groupAnchorB", new float[]{1,1});
            float[] anchorA = args.getNumArray("anchorA", new float[]{-1,0});
            float[] anchorB = args.getNumArray("anchorB", new float[]{1,0});
            float ratio = args.getNum("ratio", 1f);
            newDef.initialize(bodyA, bodyB, new Vector2(groupAnchorA[0], groupAnchorA[1]),
                new Vector2(groupAnchorB[0], groupAnchorB[1]),
                new Vector2(anchorA[0], anchorA[1]),
                new Vector2(anchorB[0], anchorB[1]), ratio);
            return (PulleyJoint)world.createJoint(newDef);
        } else if(type.equals("revolute")) {
            RevoluteJointDef newDef = new RevoluteJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            float[] axis = args.getNumArray("axis", new float[]{0,0});
            newDef.enableLimit = args.getBool("enableLimit", false);
            newDef.lowerAngle = args.getNum("lowerAngle", 0f);
            newDef.upperAngle = args.getNum("upperAngle", 0f);
            newDef.enableMotor = args.getBool("enableMotor", false);
            newDef.maxMotorTorque = args.getNum("maxMotorTorque", 0f);
            newDef.motorSpeed = args.getNum("motorSpeed", 0f);
            newDef.initialize(bodyA, bodyB, new Vector2(anchor[0], anchor[1]));
            return (RevoluteJoint)world.createJoint(newDef);
        } else if(type.equals("rope")) {
            RopeJointDef newDef = new RopeJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchorA = args.getNumArray("anchorA", new float[]{0,0});
            float[] anchorB = args.getNumArray("anchorB", new float[]{0,0});
            newDef.maxLength = args.getNum("maxLength", 0f);
            newDef.localAnchorA.set(anchorA[0], anchorA[1]);
            newDef.localAnchorB.set(anchorB[0], anchorB[1]);
            return (RopeJoint)world.createJoint(newDef);
        } else if (type.equals("weld")) {
            WeldJointDef newDef = new WeldJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            newDef.frequencyHz = args.getNum("frequencyHz", 0f);
            newDef.dampingRatio = args.getNum("dampingRatio", 0f);
            newDef.initialize(bodyA, bodyB, new Vector2(anchor[0], anchor[1]));
            return (WeldJoint)world.createJoint(newDef);
        } else if(type.equals("wheel")) {
            WheelJointDef newDef = new WheelJointDef();
            newDef.bodyA = bodyA;
            newDef.bodyB = bodyB;
            newDef.collideConnected = collideConnected;
            float[] anchor = args.getNumArray("anchor", new float[]{0,0});
            float[] axis = args.getNumArray("axis", new float[]{0,0});
            newDef.enableMotor = args.getBool("enableMotor", false);
            newDef.maxMotorTorque = args.getNum("maxMotorTorque", 0f);
            newDef.motorSpeed = args.getNum("motorSpeed", 0f);
            newDef.frequencyHz = args.getNum("frequencyHz", 2f);
            newDef.dampingRatio = args.getNum("dampingRatio", 0.7f);
            newDef.initialize(bodyA, bodyB, new Vector2(anchor[0], anchor[1]), new Vector2(axis[0], axis[1]));
            return (WheelJoint)world.createJoint(newDef);
        }
        
        JointDef def = new JointDef();
        def.type = jointType(type);
        def.bodyA = bodyA;
        def.bodyB = bodyB;
        
        return world.createJoint(def);
    }
    
    public void queryAABB(Rectangle area, final Function callback) {
        world.QueryAABB(new QueryCallback() {
            public boolean reportFixture(Fixture fixture) {
                Object result = RhinoNon.script.call(callback, fixture);
                
                if (result instanceof Boolean) {
                    return (Boolean)result;
                } else {
                    return false;
                }
            }
        }, area.x, area.y + area.height, area.x + area.width, area.y);
    }
}