package non;

import java.lang.reflect.Field;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Helpers {
    private boolean runningOnOuya;

    public Helpers() {
        try {
            Field field = Class.forName("android.os.Build").getDeclaredField("DEVICE");
            Object device = field.get(null);
            runningOnOuya = "ouya_1_1".equals(device) || "cardhu".equals(device);
        } catch (Exception e) { }
    }

    public FileHandle localfile(String name) {
        return Gdx.files.local(name);
    }

    public String getOS() {
    	String name = "unknown";

        if (Gdx.app.getType() == ApplicationType.Desktop) {
            String osname = System.getProperty("os.name").toLowerCase();

            if (osname.startsWith("windows")) {
                name = "Windows";
            } else if (osname.startsWith("linux")) {
                name = "Linux";
            } else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
                name = "OS X";
            } else if (osname.startsWith("sunos")) {
                name = "Solaris";
            }
        } else if (Gdx.app.getType() == ApplicationType.Android) {
            name = "Android";
        } else if (Gdx.app.getType() == ApplicationType.iOS) {
            name = "iOS";
        } else if (runningOnOuya) {
            name = "Ouya";
        }

        return name;
    }

    public void endBatch(SpriteBatch batch) {
        batch.end();
    }

    public void endShapes(ShapeRenderer shapes) {
        shapes.end();
    }
}
