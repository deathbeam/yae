package non.javan.module;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class JavanSystem {
    private boolean runningOnOuya;
    private String os;

    public JavanSystem() {
        try {
            Object device = Class.forName("android.os.Build").getDeclaredField("DEVICE").get(null);
            runningOnOuya = "ouya_1_1".equals(device) || "cardhu".equals(device);
        } catch (Exception e) { }
        
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            os = System.getProperty("os.name").toLowerCase();

            if (os.startsWith("windows")) {
                os = "Windows";
            } else if (os.startsWith("linux")) {
                os = "Linux";
            } else if (os.startsWith("mac") || os.startsWith("darwin")) {
                os = "OS X";
            } else if (os.startsWith("sunos")) {
                os = "Solaris";
            } else {
                os = "Unknown";
            }
        } else if (Gdx.app.getType() == ApplicationType.Android) {
            os = "Android";
        } else if (Gdx.app.getType() == ApplicationType.iOS) {
            os = "iOS";
        } else if (runningOnOuya) {
            os = "Ouya";
        } else {
            os = "Unknown";
        }
    }
    
    public String getClipboardText() {
        return Gdx.app.getClipboard().getContents();
    }
    
    public String getOS() {
        return os;
    }
    
    public float[] getMemoryUse() {
        return new float[] {
            Gdx.app.getJavaHeap(),
            Gdx.app.getNativeHeap()
        };
    }
    
    public boolean openURL(String address) {
        return Gdx.net.openURI(address);
    }
    
    public void vibrate() {
        vibrate(1f);
    }
    
    public void setClipboardText(String text) {
        Gdx.app.getClipboard().setContents(text);
    }
    
    public void vibrate(float seconds) {
        Gdx.input.vibrate((int)(seconds * 1000f));
    }
    
    public void quit() {
        Gdx.app.exit();
    }
}