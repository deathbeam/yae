package com.deathbeam.non.desktop;

import java.io.IOException;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.non.Game;
import com.deathbeam.non.Utils;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		JsonValue conf = Utils.configure();
        
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.addIcon("resources/icon-256.png", Files.FileType.Classpath);
        cfg.addIcon("resources/icon-192.png", Files.FileType.Classpath);
        cfg.addIcon("resources/icon-64.png", Files.FileType.Classpath);
        cfg.addIcon("resources/icon-32.png", Files.FileType.Classpath);
        cfg.addIcon("resources/icon-16.png", Files.FileType.Classpath);
        if (conf!= null) {
            cfg.title = conf.get("title").asString();
            cfg.width = conf.get("resolution").asIntArray()[0];
            cfg.height = conf.get("resolution").asIntArray()[1];
        } else {
            cfg.title = "Game not found!";
            cfg.width = 800;
            cfg.height = 600;
        }
        new LwjglApplication(new Game(conf), cfg);
	}
}
