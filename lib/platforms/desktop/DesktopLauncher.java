package %PACKAGE%.desktop;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import non.NonVM;

public class DesktopLauncher {
    @SuppressWarnings("unchecked")
    public static void main (String[] args) throws FileNotFoundException {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.addIcon("non/icon-256.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-192.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-64.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-32.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-16.png", Files.FileType.Internal);
        cfg.forceExit = false;
        cfg.width = 800;
        cfg.height = 600;
        cfg.resizable = false;
        
        Yaml yaml = new Yaml();
        Map config;
        
        try {
            config = (Map<String, Object>)yaml.load(DesktopLauncher.class.getResourceAsStream("/non/config.yml"));
        } catch (Exception e) {
            config = (Map<String, Object>)yaml.load(new FileInputStream(new File("non/config.yml")));
        }
        
        cfg.title = (String)config.get("name");
        
        if (config.containsKey("desktop")) {
            Map desktop = (Map<String, Object>)config.get("desktop");
            if (desktop.containsKey("display")) {
                Map display = (Map<String, Object>)desktop.get("display");
                if (display.containsKey("width")) cfg.width = (Integer)display.get("width");
                if (display.containsKey("height")) cfg.height = (Integer)display.get("height");
                if (display.containsKey("resizable")) cfg.resizable = (Boolean)display.get("resizable");
                if (display.containsKey("fullscreen")) cfg.fullscreen = (Boolean)display.get("fullscreen");
            }
        }
        
        new LwjglApplication(new NonVM(config), cfg);
    }
}
