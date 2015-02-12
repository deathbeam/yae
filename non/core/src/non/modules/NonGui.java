package non.modules;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;

import non.Non;
import non.modules.NonGraphics;

public class NonGui extends Module {
    static final int MAGIC_NUMBER = 1270475;
    
    public class UIState {
        public int mouseX = 0;
        public int mouseY = 0;
        public boolean mouseDown = false;
        public int hotItem = 0;
        public int activeItem = 0;
        public int kbdItem = 0;
        public int keyEntered = 0;
        public char keyChar = 0;
        public int lastWidget = 0;
    }
    
    protected final UIState state;
    protected int maxid;
    protected float focusTimer = 0;
    protected boolean renderFocus = false;
    
    public NonGui() {
        state = new UIState();
    }
    
    protected int generateId() {
        maxid++;
        return MAGIC_NUMBER + maxid;
    }
    
    protected boolean regionHit(Rectangle bounds) {
        return state.mouseX >= bounds.x &&
               state.mouseY >= bounds.y &&
               state.mouseX <= bounds.x + bounds.width &&
               state.mouseY <= bounds.y + bounds.height;
    }
    
    protected String getState(int id) {
        if (state.activeItem == id) {
            return "active";
        } else if (state.hotItem == id) {
            return "hot";
        }
        
        return "normal";
    }
    
    protected void processTab() {
        focusTimer = 0;
        renderFocus = true;
        state.kbdItem = 0;
        
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            state.kbdItem = state.lastWidget;
        }
                    
        state.keyEntered = 0;
    }
    
    public void update(float dt) {
        state.hotItem = 0;
        maxid = 0;
        
        if (renderFocus) {
            if (focusTimer >= 300) {
                renderFocus = false;
            } else {
                focusTimer += dt;
            }
        }
    }
    
    public void keyPressed(int key) {
        state.keyEntered = key;
    }
    
    public void keyTyped(char key) {
        state.keyChar = key;
    }
    
    public void updateAfter(float dt) {
        if (!state.mouseDown) {
            state.activeItem = 0;
        } else if (state.activeItem == 0) {
            state.activeItem = -1;
        }
        
        if (state.keyEntered == Keys.TAB) state.kbdItem = 0;
        state.keyEntered = 0;  
        state.keyChar = 0;
        
        state.mouseX = Gdx.input.getX();
        state.mouseY = Gdx.input.getY();
        state.mouseDown = Gdx.input.isTouched();
    }
}