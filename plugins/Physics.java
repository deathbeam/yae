package com.codeindie.non.plugins;

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

public class Physics extends Plugin {
    public String name() { return "physics"; }
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Plugin for handling physics."; }
    public String[] dependencies() { return new String[] { "graphics" }; }
    
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Vector2 gravity = Vector2.Zero;
    private boolean sleep = true;
    private float step = 1 / 60f;
    private float accum = 0f;
    private float ppt =  1;
    
    public World getWorld() { return world; }
    public Physics setGravity(float x, float y) { gravity = new Vector2(x, y); return this; }
    public Physics setSleep(boolean sleep) { this.sleep = sleep; return this; }
    public Physics setStep(float step) { this.step = step; return this; }

    public Physics init() {
        Box2D.init();
        world = new World(gravity, sleep);
        debugRenderer = new Box2DDebugRenderer();
        return this;
    }
    
    public void update() {
        accum += Gdx.graphics.getDeltaTime();
        while (accum >= step) {
            world.step(step, 6, 2);
            accum -= step;
        }
    }
    
    public void draw(Graphics graphics) {
        graphics.end();
        debugRenderer.render(world, graphics.getProjection());
        graphics.begin();
    }
    
    public Body newShape(Shape2D shape) {
        return newShape(shape, "dynamic");
    }
    
    public Body newShape(Shape2D shape, String type) {
        return newShape(shape, type, 0, 0.2f, 0);
    }
    
    public Body newShape(Shape2D shape, String type, float density, float friction, float restitution) {
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
    
    public Array<Body> newShape(MapObjects objects) {
        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {
            if (object instanceof TextureMapObject) continue;

            Shape shape;
            
            if (object instanceof RectangleMapObject) shape = getRectangle((RectangleMapObject)object);
            else if (object instanceof PolygonMapObject) shape = getPolygon((PolygonMapObject)object);
            else if (object instanceof PolylineMapObject) shape = getPolyline((PolylineMapObject)object);
            else if (object instanceof CircleMapObject) shape = getCircle((CircleMapObject)object);
            else continue;

            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);
            bodies.add(body);
            shape.dispose();
        }
        
        return bodies;
    }
    
    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
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
