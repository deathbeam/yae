/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.nonfw.Game;
import com.deathbeam.nonfw.Utils;
import java.io.IOException;
/**
 *
 * @author Thomas Slusny
 */
public class Desktop {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main (String[] args) throws IOException {
        JsonValue arg = Utils.configure();
        
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.addIcon("assets/icon-256.png", Files.FileType.Classpath);
        cfg.addIcon("assets/icon-192.png", Files.FileType.Classpath);
        cfg.addIcon("assets/icon-64.png", Files.FileType.Classpath);
        cfg.addIcon("assets/icon-32.png", Files.FileType.Classpath);
        cfg.addIcon("assets/icon-16.png", Files.FileType.Classpath);
        if (arg!= null) {
            cfg.title = arg.get("title").asString();
            cfg.width = arg.get("resolution").asIntArray()[0];
            cfg.height = arg.get("resolution").asIntArray()[1];
        } else {
            cfg.title = "Game not found!";
            cfg.width = 800;
            cfg.height = 600;
        }
        new LwjglApplication(new Game(arg), cfg);
    }
}