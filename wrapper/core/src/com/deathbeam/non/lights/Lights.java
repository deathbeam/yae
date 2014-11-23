/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
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
package com.deathbeam.non.lights;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.deathbeam.non.Game;

/**
 *
 * @author Thomas Slusny
 */
public class Lights {
    private final RayHandler handler;
    
    public Lights() {
        handler = new RayHandler(null);
    }
    
    public Lights init() {
        handler.setWorld(Game.physics.getWorld());
        return this;
    }
    
    public void draw() {
        if (Game.graphics == null) return;
        handler.setCombinedMatrix(Game.graphics.getProjection());
        Game.graphics.end();
        handler.render();
        Game.graphics.begin();
    }
    
    public void update() {
        handler.update();
    }
    
    public Lights setGammaCorrection(boolean wanted) {
        RayHandler.setGammaCorrection(wanted);
        return this;
    }
    
    public Lights setDiffuseLight(boolean use) {
        RayHandler.useDiffuseLight(use);
        return this;
    }
    
    public Lights setAmbient(Color color) {
        handler.setAmbientLight(color);
        return this;
    }
    
    public Lights setBlur(boolean blur) {
        handler.setBlur(blur);
        return this;
    }
    
    public Lights setBlurNum(int num) {
        handler.setBlurNum(num);
        return this;
    }
    
    public Lights setShadows(boolean shadows) {
        handler.setShadows(shadows);
        return this;
    }
    
    public Lights setCulling(boolean culling) {
        handler.setCulling(culling);
        return this;
    }
    
    public Lights setLightmaps(boolean isAutomatic) {
        handler.setLightMapRendering(isAutomatic);
        return this;
    }
    
    public DirectionalLight newDirectionalLight(int rays, Color color, float direction) {
        return new DirectionalLight(handler, rays, color, direction);
    }
    
    public PointLight newPointLight(int rays) {
        return new PointLight(handler, rays);
    }
    
    public PointLight newPointLight(int rays, Color color, float distance, float x, float y) {
        return new PointLight(handler, rays, color, distance, x, y);
    }
    
    public ConeLight newConeLight(int rays, Color color, float distance, float x, float y, float direction, float cone) {
        return new ConeLight(handler, rays, color, distance, x, y, direction, cone);
    }
	
	public ChainLight newChainLight(int rays, Color color, float distance, int rayDirection) {
		return new ChainLight(handler, rays, color, distance, rayDirection);
	}
	
	public ChainLight newChainLight(int rays, Color color, float distance, int rayDirection, float[] chains) {
		return new ChainLight(handler, rays, color, distance, rayDirection, chains);
	}
}
