/*
 * The MIT License
 *
 * Copyright 2014 Tomas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.physics;

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
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.deathbeam.nonfw.graphics.Graphics;
import com.deathbeam.nonfw.tiled.TiledMap;
/**
 *
 * @author Tomas
 */
public class Physics {
    private World world;
    private Vector2 gravity = Vector2.Zero;
    private boolean sleep = true;
    private float timescale = 1f;
    private Box2DDebugRenderer debugRenderer;
    
    public Physics setGravity(float x, float y) {
        this.gravity = new Vector2(x, y);
        return this;
    }
    
    public Physics setSleep(boolean sleep) {
        this.sleep = sleep;
        return this;
    }
    
    public Physics setTimeScale(float timescale) {
        this.timescale = timescale;
        return this;
    }
    
    public Physics init() {
        Box2D.init();
        world = new World(gravity, sleep);
        debugRenderer = new Box2DDebugRenderer();
        return this;
    }
    
    public void update(float delta) {
        world.step(Math.min(delta, 0.02f) * timescale, 4, 4);
    }
    
    public void draw(Graphics gfx) {
        gfx.end();
        debugRenderer.render(world, gfx.getProjection());
        gfx.begin();
    }
    
    public Array<Body> newMap(TiledMap map) {
        return MapBodyBuilder.buildShapes(map.map, map.tileSize, world);
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
    
    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
    }
}