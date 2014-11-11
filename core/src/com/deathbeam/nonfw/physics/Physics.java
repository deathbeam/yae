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

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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
    
    public Physics setGravity(Vector2 gravity) {
        this.gravity = gravity;
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
        return newShape(shape, type, 1f);
    }
    
    public Body newShape(Shape2D shape, String type, float density) {
        BodyDef def = new BodyDef();
        
        if (type.equalsIgnoreCase("dynamic")) def.type = BodyType.DynamicBody;
        else if (type.equalsIgnoreCase("static")) def.type = BodyType.StaticBody;
        else if (type.equalsIgnoreCase("kinematic")) def.type = BodyType.KinematicBody;
        
        if (shape instanceof Rectangle) {
            Rectangle cur = (Rectangle)shape;
            def.position.set(cur.x, cur.y);
            Body body = world.createBody(def);
            PolygonShape s = new PolygonShape();
            s.setAsBox(cur.width, cur.height);
            body.createFixture(s, density);
            s.dispose();
            return body;
        } else if (shape instanceof Circle) {
            Circle cur = (Circle)shape;
            def.position.set(cur.x, cur.y);
            Body body = world.createBody(def);
            CircleShape s = new CircleShape();
            s.setRadius(cur.radius);
            body.createFixture(s, density);
            s.dispose();
            return body;
        } else if (shape instanceof Polygon) {
            Polygon cur = (Polygon)shape;
            def.position.set(cur.getX(), cur.getY());
            Body body = world.createBody(def);
            PolygonShape s = new PolygonShape();
            s.set(cur.getVertices());
            body.createFixture(s, density);
            s.dispose();
            return body;
        }
        
        return null;
    }
}