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
package com.deathbeam.nonfw.input;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author Tomas
 */
public class Mouse {
    public int getButton(String name) {
        if ("LEFT".equals(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
        if ("RIGHT".equals(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
        if ("MIDDLE".equals(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
        if ("BACK".equals(name)) return com.badlogic.gdx.Input.Buttons.BACK;
        if ("FORWARD".equals(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
        return -1;
    }
    
    public int getX() {
        return Gdx.input.getX();
    }
    
    public int getY() {
        return Gdx.input.getY();
    }
    
    public boolean isClicked() {
        return Gdx.input.isTouched();
    }
    
    public boolean justClicked() {
        return Gdx.input.justTouched();
    }
    
    public boolean isButtonPressed(String name) {
        return Gdx.input.isButtonPressed(getButton(name));
    }
}
