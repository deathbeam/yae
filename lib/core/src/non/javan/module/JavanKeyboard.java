package non.javan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.utils.ObjectIntMap;

public class JavanKeyboard {
    private static ObjectIntMap<String> keyNames;
    
    public boolean isDown(String key) {
        return Gdx.input.isKeyPressed(getKey(key));
    }
    
    public boolean isVisible() {
        return Gdx.input.isPeripheralAvailable(Peripheral.OnscreenKeyboard);
    }
    
    public void setVisible(boolean visible) {
        Gdx.input.setOnscreenKeyboardVisible(visible);
    }

    public static int getKey(String keyname) {
        if (keyNames == null) {
            keyNames = new ObjectIntMap<String>();
            for (int i = 0; i < 255; i++) {
                String name = getKey(i);
                if (name != null) keyNames.put(name, i);
            }
            
            keyNames.put(" ", Keys.SPACE);
        }

        return keyNames.get(keyname, -1);
    }

    public static String getKey(int keycode) {
        switch (keycode) {
        case Keys.UNKNOWN: return "unknown";
        case Keys.SOFT_LEFT: return "sleft";
        case Keys.SOFT_RIGHT: return "sright";
        case Keys.HOME: return "home";
        case Keys.BACK: return "back";
        case Keys.CALL: return "call";
        case Keys.ENDCALL: return "endcall";
        case Keys.NUM_0: return "0";
        case Keys.NUM_1: return "1";
        case Keys.NUM_2: return "2";
        case Keys.NUM_3: return "3";
        case Keys.NUM_4: return "4";
        case Keys.NUM_5: return "5";
        case Keys.NUM_6: return "6";
        case Keys.NUM_7: return "7";
        case Keys.NUM_8: return "8";
        case Keys.NUM_9: return "9";
        case Keys.STAR: return "*";
        case Keys.POUND: return "#";
        case Keys.UP: return "up";
        case Keys.DOWN: return "down";
        case Keys.LEFT: return "left";
        case Keys.RIGHT: return "right";
        case Keys.CENTER: return "center";
        case Keys.VOLUME_UP: return "volumeup";
        case Keys.VOLUME_DOWN: return "volumedown";
        case Keys.POWER: return "power";
        case Keys.CAMERA: return "camera";
        case Keys.CLEAR: return "clear";
        case Keys.A: return "a";
        case Keys.B: return "b";
        case Keys.C: return "c";
        case Keys.D: return "d";
        case Keys.E: return "e";
        case Keys.F: return "f";
        case Keys.G: return "g";
        case Keys.H: return "h";
        case Keys.I: return "i";
        case Keys.J: return "j";
        case Keys.K: return "k";
        case Keys.L: return "l";
        case Keys.M: return "m";
        case Keys.N: return "n";
        case Keys.O: return "o";
        case Keys.P: return "p";
        case Keys.Q: return "q";
        case Keys.R: return "r";
        case Keys.S: return "s";
        case Keys.T: return "t";
        case Keys.U: return "u";
        case Keys.V: return "v";
        case Keys.W: return "w";
        case Keys.X: return "x";
        case Keys.Y: return "y";
        case Keys.Z: return "z";
        case Keys.COMMA: return ",";
        case Keys.PERIOD: return ".";
        case Keys.ALT_LEFT: return "lalt";
        case Keys.ALT_RIGHT: return "ralt";
        case Keys.SHIFT_LEFT: return "lshift";
        case Keys.SHIFT_RIGHT: return "rshift";
        case Keys.TAB: return "tab";
        case Keys.SPACE: return "space";
        case Keys.SYM: return "SYM";
        case Keys.EXPLORER: return "www";
        case Keys.ENVELOPE: return "mail";
        case Keys.ENTER: return "return";
        case Keys.DEL: return "delete";
        case Keys.GRAVE: return "`";
        case Keys.MINUS: return "-";
        case Keys.EQUALS: return "=";
        case Keys.LEFT_BRACKET: return "[";
        case Keys.RIGHT_BRACKET: return "]";
        case Keys.BACKSLASH: return "\\";
        case Keys.SEMICOLON: return ";";
        case Keys.APOSTROPHE: return "'";
        case Keys.SLASH: return "/";
        case Keys.AT: return "@";
        case Keys.NUM: return "num";
        case Keys.HEADSETHOOK: return "headsethook";
        case Keys.FOCUS: return "focus";
        case Keys.PLUS: return "plus";
        case Keys.MENU: return "menu";
        case Keys.NOTIFICATION: return "notification";
        case Keys.SEARCH: return "search";
        case Keys.MEDIA_PLAY_PAUSE: return "pause";
        case Keys.MEDIA_STOP: return "stop";
        case Keys.MEDIA_NEXT: return "next";
        case Keys.MEDIA_PREVIOUS: return "prev";
        case Keys.MEDIA_REWIND: return "rewind";
        case Keys.MEDIA_FAST_FORWARD: return "fastforward";
        case Keys.MUTE: return "mute";
        case Keys.PAGE_UP: return "pageup";
        case Keys.PAGE_DOWN: return "pagedown";
        case Keys.PICTSYMBOLS: return "PICTSYMBOLS";
        case Keys.SWITCH_CHARSET: return "SWITCH_CHARSET";
        case Keys.BUTTON_A: return "btnA";
        case Keys.BUTTON_B: return "btnB";
        case Keys.BUTTON_C: return "btnC";
        case Keys.BUTTON_X: return "btnX";
        case Keys.BUTTON_Y: return "btnY";
        case Keys.BUTTON_Z: return "btnZ";
        case Keys.BUTTON_L1: return "btnl1";
        case Keys.BUTTON_R1: return "btnr1";
        case Keys.BUTTON_L2: return "btnl2";
        case Keys.BUTTON_R2: return "btnr2";
        case Keys.BUTTON_THUMBL: return "btnlthumb";
        case Keys.BUTTON_THUMBR: return "btnrthumb";
        case Keys.BUTTON_START: return "btnstart";
        case Keys.BUTTON_SELECT: return "btnselect";
        case Keys.BUTTON_MODE: return "buttonmode";
        case Keys.FORWARD_DEL: return "forwarddelete";
        case Keys.CONTROL_LEFT: return "lctrl";
        case Keys.CONTROL_RIGHT: return "rctrl";
        case Keys.ESCAPE: return "escape";
        case Keys.END: return "end";
        case Keys.INSERT: return "insert";
        case Keys.NUMPAD_0: return "kp0";
        case Keys.NUMPAD_1: return "kp1";
        case Keys.NUMPAD_2: return "kp2";
        case Keys.NUMPAD_3: return "kp3";
        case Keys.NUMPAD_4: return "kp4";
        case Keys.NUMPAD_5: return "kp5";
        case Keys.NUMPAD_6: return "kp6";
        case Keys.NUMPAD_7: return "kp7";
        case Keys.NUMPAD_8: return "kp8";
        case Keys.NUMPAD_9: return "kp9";
        case Keys.COLON: return ":";
        case Keys.F1: return "f1";
        case Keys.F2: return "f2";
        case Keys.F3: return "f3";
        case Keys.F4: return "f4";
        case Keys.F5: return "f5";
        case Keys.F6: return "f6";
        case Keys.F7: return "f7";
        case Keys.F8: return "f8";
        case Keys.F9: return "f9";
        case Keys.F10: return "f10";
        case Keys.F11: return "f11";
        case Keys.F12: return "f12";
        default: return null;
        }
    }
}