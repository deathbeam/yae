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
import non.vm;

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
        
        if (config.containsKey("window")) {
            Map window = (Map<String, Object>)config.get("window");
            if (window.containsKey("width")) cfg.width = (Integer)window.get("width");
            if (window.containsKey("height")) cfg.height = (Integer)window.get("height");
            if (window.containsKey("resizable")) cfg.resizable = (Boolean)window.get("resizable");
            if (window.containsKey("fullscreen")) cfg.fullscreen = (Boolean)window.get("fullscreen");
        }
        
        new LwjglApplication(new vm(config), cfg);
    }
}
