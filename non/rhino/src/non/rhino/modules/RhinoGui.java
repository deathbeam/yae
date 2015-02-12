package non.rhino.modules;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import non.Non;
import non.rhino.Arguments;
import non.rhino.RhinoNon;
import non.modules.NonGraphics;
import non.modules.NonGui;

public class RhinoGui extends NonGui {
    public Scriptable renderer;
    
    protected Function getRenderer(String name) {
        return (Function)renderer.get(name, renderer);
    }
    
    protected NonGraphics getGraphics() {
        return (NonGraphics)RhinoNon.script.convertToJava(renderer.get("graphics", renderer), NonGraphics.class);
    }
    
    protected void render(String renderer, Object... args) {
        RhinoNon.script.call(getRenderer(renderer), args);
    }
    
    protected  void renderLabel(int id, String text, float x, float y) {
        render("label", id, text, x, y);
    }
    
    protected  void renderButton(int id, String state, String text, float x, float y, float width, float height) {
        render("button", id, state, text, x, y, width, height);
    }
    
    protected  void renderText(int id, String textOutput, float x, float y, float width, float height) {
        render("text", id, textOutput, x, y, width, height);
    }
    
    protected  void renderVScroll(int id, int xpos, float x, float y, float width, float height) {
        render("vscroll", id, xpos, x, y, width, height);
    }
    
    protected  void renderHScroll(int id, int ypos, float x, float y, float width, float height) {
        render("hscroll", id, ypos, x, y, width, height);
    }
    
    protected void renderFocus(int id, float x, float y, float width, float height) {
        if (state.kbdItem == id && renderFocus) {
            render("focus", id, x, y, width, height);
        }
    }
    
    public boolean label(Scriptable rhinoArgs) {
        return label("active", rhinoArgs);
    }
    
    public boolean label(String stateString, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        String text = args.getString("text", "");
        int id = (int)args.getNum("id", (float)(generateId()));
        maxid++;
        float[] position = args.getNumArray("position", new float[] {0, 0});
        TextBounds size = getGraphics().measureText(text);
        Rectangle bounds = new Rectangle(position[0], position[1], size.width, size.height);
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        renderLabel(id, text, bounds.x, bounds.y);
        
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
        
        int id = (int)args.getNum("id", (float)(generateId()));
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
        
        if (state.kbdItem == id) {
            switch (state.keyEntered) {
                case Keys.TAB: 
                    processTab();
                    break;
                case Keys.ENTER:
                    return stateString.equals("active");
                default: break;
            }
        }
        
        renderFocus(id, bounds.x, bounds.y, bounds.width, bounds.height);
        renderButton(id, getState(id), text, bounds.x, bounds.y, bounds.width, bounds.height);
        
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
        
        int id = (int)args.getNum("id", (float)(generateId()));
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
            RhinoNon.script.call(args.getFunction("onFocus"));
        }
        
        if (state.kbdItem == id) {
            switch (state.keyEntered) {
                case Keys.TAB: 
                    processTab();
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
        
        renderFocus(id, bounds.x, bounds.y, bounds.width, bounds.height);
        renderText(id, textOutput, bounds.x, bounds.y, bounds.width, bounds.height);
        
        state.lastWidget = id;
        
        if (!state.mouseDown && 
            state.hotItem == id && 
            state.activeItem == id) {
            state.kbdItem = id;
            RhinoNon.script.call(args.getFunction("onTouch"));
        }
        
        return textOutput;
    }
    
    public float vscroll(float value, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int id = (int)args.getNum("id", (float)(generateId()));
        maxid++;
        
        
        float[] position = args.getNumArray("position", new float[] {0, 0});
        float[] size = args.getNumArray("size", new float[] {0, 0});
        Rectangle bounds = new Rectangle(position[0], position[1], size[0], size[1]);
        float max = args.getNum("max", bounds.height);
        
        int ypos = (int)((bounds.height * value) / max);
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        if (state.kbdItem == 0) state.kbdItem = id;
        
        if (state.kbdItem == id) {
            switch (state.keyEntered) {
                case Keys.TAB: 
                    processTab();
                    break;
                case Keys.UP:
                    if (value > 0) value--;
                    break;
                case Keys.DOWN:
                    if (value < max) value++;
                    break;
                default: break;
            }
        }
        
        renderFocus(id, bounds.x, bounds.y, bounds.width, bounds.height);
        renderVScroll(id, ypos, bounds.x, bounds.y, bounds.width, bounds.height);
        
        state.lastWidget = id;
        
        if (state.activeItem == id) {
            int mousepos = (int)(state.mouseY - bounds.y);
            if (mousepos < 0) mousepos = 0;
            if (mousepos > bounds.height) mousepos = (int)bounds.height;
            value = (mousepos * max) / bounds.height;
        }
        
        return value;
    }
    
    public float hscroll(float value, Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int id = (int)args.getNum("id", (float)(generateId()));
        maxid++;
        
        
        float[] position = args.getNumArray("position", new float[] {0, 0});
        float[] size = args.getNumArray("size", new float[] {0, 0});
        Rectangle bounds = new Rectangle(position[0], position[1], size[0], size[1]);
        float max = args.getNum("max", bounds.height);
        
        int ypos = (int)((bounds.width * value) / max);
        
        if (regionHit(bounds)) {
            state.hotItem = id;
            if (state.activeItem == 0 && state.mouseDown) state.activeItem = id;
        }
        
        if (state.kbdItem == 0) state.kbdItem = id;
        
        if (state.kbdItem == id) {
            switch (state.keyEntered) {
                case Keys.TAB: 
                    processTab();
                    break;
                case Keys.LEFT:
                    if (value > 0) value--;
                    break;
                case Keys.RIGHT:
                    if (value < max) value++;
                    break;
                default: break;
            }
        }
        
        renderFocus(id, bounds.x, bounds.y, bounds.width, bounds.height);
        renderHScroll(id, ypos, bounds.x, bounds.y, bounds.width, bounds.height);
        
        state.lastWidget = id;
        
        if (state.activeItem == id) {
            int mousepos = (int)(state.mouseX - bounds.x);
            if (mousepos < 0) mousepos = 0;
            if (mousepos > bounds.width) mousepos = (int)bounds.width;
            value = (mousepos * max) / bounds.width;
        }
        
        return value;
    }
}