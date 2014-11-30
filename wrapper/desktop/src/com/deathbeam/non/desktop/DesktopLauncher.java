package com.deathbeam.non.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deathbeam.non.Non;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.addIcon("res/icon-256.png", Files.FileType.Classpath);
        cfg.addIcon("res/icon-192.png", Files.FileType.Classpath);
        cfg.addIcon("res/icon-64.png", Files.FileType.Classpath);
        cfg.addIcon("res/icon-32.png", Files.FileType.Classpath);
        cfg.addIcon("res/icon-16.png", Files.FileType.Classpath);
        cfg.width = 800;
        cfg.height = 600;
        new LwjglApplication(new Non(), cfg);
    }
}
