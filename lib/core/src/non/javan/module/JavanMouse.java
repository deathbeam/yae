package non.javan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.ObjectIntMap;

public class JavanMouse {
    private static ObjectIntMap<String> buttonNames;
    
    public float[] getPosition() {
        return new float[] {
            Gdx.input.getX(),
            Gdx.input.getY()
        };
    }

    public float getX() {
        return Gdx.input.getX();
    }

    public float getY() {
        return Gdx.input.getY();
    }
    
    public boolean isDown(String button) {
        return Gdx.input.isButtonPressed(getButton(button));
    }
    
    public boolean isVisible() {
        return !Gdx.input.isCursorCatched();
    }
    
    public void setPosition(float x, float y) {
        Gdx.input.setCursorPosition((int)x, (int)y);
    }
    
    public void setVisible(boolean visible) {
        Gdx.input.setCursorCatched(!visible);
    }
    
    public void setX(float x) {
        setPosition(x, getY());
    }
    
    public void setY(float y) {
        setPosition(getX(), y);
    }

    public static int getButton(String buttonname) {
        if (buttonNames == null) {
            buttonNames = new ObjectIntMap<String>();
            for (int i = 0; i < 255; i++) {
                String name = getButton(i);
                if (name != null) buttonNames.put(name, i);
            }
        }

        return buttonNames.get(buttonname, -1);
    }

    public static String getButton(int buttoncode) {
        switch (buttoncode) {
        case Buttons.LEFT: return "left";
        case Buttons.RIGHT: return "right";
        case Buttons.MIDDLE: return "middle";
        case Buttons.BACK: return "back";
        case Buttons.FORWARD: return "forward";
        default: return null;
        }
    }
}