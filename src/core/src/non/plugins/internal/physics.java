package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import non.plugins.Plugin;

public class physics extends Plugin {
    public String author()         { return "Thomas Slusny"; }
    public String license()        { return "MIT"; }
    public String description()    { return "Plugin for handling physics."; }
    public String[] dependencies() { return new String[] { "graphics", "maths" }; }
    
    private World world;
    private Box2DDebugRenderer renderer;
    private boolean debug;
    private float step, accum, ppt;
    
    public World getWorld() { return world; }
    public physics setGravity(float x, float y) { world.setGravity(new Vector2(x,y)); return this; }
    public physics setStep(float step) { this.step = step; return this; }
    public physics setDebug(boolean debug) { this.debug = debug; return this; }
	
    public physics() {
        Box2D.init();
        world = new World(new Vector2(0,0), true);
    }
    
    public void plugin_load() {
        renderer = new Box2DDebugRenderer();
        debug = false;
        step = 1 / 60f;
        accum = 0;
        ppt = 1;
    }
    
    public void plugin_unload() {
        if (world != null) world.dispose();
        if (renderer != null) renderer.dispose();
    }
    
    public void plugin_update_before() {
        accum += Gdx.graphics.getDeltaTime();
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
    
    public Body shape(Shape2D shape) {
        return shape(shape, "dynamic");
    }
    
    public Body shape(Shape2D shape, String type) {
        return shape(shape, type, 0, 0.2f, 0);
    }
    
    public Body shape(Shape2D shape, String type, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();

        if (type.equalsIgnoreCase("dynamic")) bodyDef.type = BodyType.DynamicBody;
        else if (type.equalsIgnoreCase("static")) bodyDef.type = BodyType.StaticBody;
        else if (type.equalsIgnoreCase("kinematic")) bodyDef.type = BodyType.KinematicBody;
        
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
        }
        
        if (s!=null) {
            Body body = world.createBody(bodyDef);
            FixtureDef def = new FixtureDef();
            def.density = density;
            def.friction = friction;
            def.restitution = restitution;
            def.shape = s;
            body.createFixture(def);
            s.dispose();
            return body;
        }
        
        return null;
    }

    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt, (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size, 0.0f);
        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) worldVertices[i] = vertices[i] / ppt;
        polygon.set(worldVertices);
        return polygon;
    }

    private ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; ++i) worldVertices[i] = new Vector2(vertices[i * 2] / ppt, vertices[i * 2 + 1] / ppt);
        ChainShape chain = new ChainShape(); 
        chain.createChain(worldVertices);
        return chain;
    }
}
