package com.deathbeam.non.plugins;

import com.badlogic.gdx.Gdx;

public class Input extends Plugin {
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Plugin for handling input from keyboard, mouse and touch screen."; }

    public class Keyboard {
        public int getKey(String name) { return com.badlogic.gdx.Input.Keys.valueOf(name); }
        public boolean isKeyJustPressed(String name) { int key = getKey(name); return Gdx.input.isKeyJustPressed(key) && key != 0; }
        public boolean isKeyPressed(String name) { int key = getKey(name); return Gdx.input.isKeyPressed(key) && key != 0; }
    }
    
    public class Mouse {
        public int getButton(String name) {
            if ("Left".equals(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
            if ("Right".equals(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
            if ("Middle".equals(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
            if ("Back".equals(name)) return com.badlogic.gdx.Input.Buttons.BACK;
            if ("Forward".equals(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
            return -1;
        }

        public int getX() { return Gdx.input.getX(); }
        public int getY() { return Gdx.input.getY(); }
        public boolean isClicked() { return Gdx.input.isTouched(); }
        public boolean justClicked() { return Gdx.input.justTouched(); }
        public boolean isButtonPressed(String name) { return Gdx.input.isButtonPressed(getButton(name)); }
    }
    
    public class Touch {
        public int getX() { return Gdx.input.getX(); }
        public int getY() { return Gdx.input.getY(); }
        public boolean isTouched() { return Gdx.input.isTouched(); }
        public boolean justTouched() { return Gdx.input.justTouched(); }
    }
    
    public final Keyboard keyboard = new Keyboard();
    public final Mouse mouse = new Mouse();
    public final Touch touch = new Touch();
}
