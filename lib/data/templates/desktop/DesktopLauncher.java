package %PACKAGE%.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import non.Non;

public class DesktopLauncher {
    public static void main (String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.addIcon("non/icon-256.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-192.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-64.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-32.png", Files.FileType.Internal);
        cfg.addIcon("non/icon-16.png", Files.FileType.Internal);
        cfg.width = 800;
        cfg.height = 600;
        new LwjglApplication(new Non(), cfg);
    }
}
