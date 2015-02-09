package non.modules;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.Color;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import com.badlogic.gdx.math.*;

import non.Non;
import non.script.Arguments;
import non.modules.NonGraphics;

public class NonGui extends Module {
    static final int MAGIC_NUMBER = 1270475;
    
    class UIState {
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
    
    public Scriptable renderer;
    private final UIState state;
    private int maxid;
    
    public NonGui() {
        state = new UIState();
    }
    
    private boolean regionHit(Rectangle bounds) {
        return state.mouseX >= bounds.x &&
               state.mouseY >= bounds.y &&
               state.mouseX <= bounds.x + bounds.width &&
               state.mouseY <= bounds.y + bounds.height;
    }
    
    private String getState(int id) {
        if (state.activeItem == id) {
            return "active";
        } else if (state.hotItem == id) {
            return "hot";
        }
        
        return "normal";
    }
    
    private Function getRenderer(String name) {
        return (Function)renderer.get(name, renderer);
    }
    
    private NonGraphics getGraphics() {
        return (NonGraphics)Non.script.convertToJava(renderer.get("graphics", renderer), NonGraphics.class);
    }
    
    public void update(float dt) {
        state.hotItem = 0;
        maxid = 0;
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
    
    public boolean label(Scriptable rhinoArgs) {
        return label("active", rhinoArgs);
    }
    
    public boolean label(String stateString, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        String text = args.getString("text", "");
        int id = (int)args.getNum("id", (float)(MAGIC_NUMBER + maxid));
        maxid++;
        float[] position = args.getNumArray("position", new float[] {0, 0});
        TextBounds size = getGraphics().measureText(text);
        Rectangle bounds = new Rectangle(position[0], position[1], size.width, size.height);
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        Non.script.call(getRenderer("label"),
            id, text, bounds.x, bounds.y
        );
        
        if (!state.mouseDown && 
            state.hotItem == id && 
            state.activeItem == id) {
            return stateString.equals("active");
        } else if (state.hotItem == id) {
            return stateString.equals("hot");
        }
        
        return stateString.equals("normal");
    }
    
    public boolean button(Scriptable rhinoArgs) {
        return button("active", rhinoArgs);
    }
    
    public boolean button(String stateString, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int id = (int)args.getNum("id", (float)(MAGIC_NUMBER + maxid));
        maxid++;
        
        String text = args.getString("text", "");
        float[] position = args.getNumArray("position", new float[] {0, 0});
        float[] size = args.getNumArray("size", new float[] {0, 0});
        Rectangle bounds = new Rectangle(position[0], position[1], size[0], size[1]);
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        if (state.kbdItem == 0) state.kbdItem = id;
        
        Non.script.call(getRenderer("button"),
            id, getState(id), text, bounds.x, bounds.y, bounds.width, bounds.height
        );
        
        if (state.kbdItem == id) {
            Non.script.call(getRenderer("focus"),
                id, bounds.x, bounds.y, bounds.width, bounds.height
            );
            
            switch (state.keyEntered) {
                case Keys.TAB: 
                    state.kbdItem = 0;
                    
                    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                        state.kbdItem = state.lastWidget;
                    }
                    
                    state.keyEntered = 0;
                    break;
                case Keys.ENTER:
                    return stateString.equals("active");
                default: break;
            }
        }
        
        state.lastWidget = id;
        
        if (!state.mouseDown && 
            state.hotItem == id && 
            state.activeItem == id) {
            return stateString.equals("active");
        } else if (state.hotItem == id) {
            return stateString.equals("hot");
        }
        
        return stateString.equals("normal");
    }
    
    public String text(String textOutput, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int id = (int)args.getNum("id", (float)(MAGIC_NUMBER + maxid));
        maxid++;
        float[] position = args.getNumArray("position", new float[] {0, 0});
        float[] size = args.getNumArray("size", new float[] {0, 0});
        Rectangle bounds = new Rectangle(position[0], position[1], size[0], size[1]);
        
        int len = textOutput.length();
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        if (state.kbdItem == 0) {
            state.kbdItem = id;
            Non.script.call(args.getFunction("onFocus"));
        }
        
        Non.script.call(getRenderer("text"),
            id, textOutput, bounds.x, bounds.y, bounds.width, bounds.height
        );
        
        if (state.kbdItem == id) {
            Non.script.call(getRenderer("focus"),
                id, bounds.x, bounds.y, bounds.width, bounds.height
            );
            
            switch (state.keyEntered) {
                case Keys.TAB: 
                    state.kbdItem = 0;
                    
                    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                        state.kbdItem = state.lastWidget;
                    }
                    
                    state.keyEntered = 0;
                    break;
                default: break;
            }
            
            if (state.keyChar >= 32 && state.keyChar < 127) {
                textOutput += state.keyChar;
            } else if (state.keyChar == 8) {
                if (len > 0) {
                    len--;
                    textOutput = textOutput.substring(0, len);
                }
            }
        }
        
        state.lastWidget = id;
        
        if (!state.mouseDown && 
            state.hotItem == id && 
            state.activeItem == id) {
            state.kbdItem = id;
            Non.script.call(args.getFunction("onTouch"));
        }
        
        return textOutput;
    }
}