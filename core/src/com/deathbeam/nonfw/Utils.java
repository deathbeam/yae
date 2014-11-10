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
package com.deathbeam.nonfw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.zeroturnaround.zip.ZipUtil;

/**
 *
 * @author Thomas Slusny
 */

public class Utils {
    public static String DIR;
    public static File CONFIG;
    
    public static void warning(String type, String msg) {
        Gdx.app.error(type, msg);
    }
    
    public static void error(String type, String msg) {
        Gdx.app.error(type, msg);
        Gdx.app.exit();
    }
    
    public static void log(String type, String msg) {
        Gdx.app.log(type, msg);
    }
    
    public static void debug(String type, String msg) {
        Gdx.app.debug(type, msg);
    }
    
    public static void dump(Object obj) {
        System.out.println(obj);
    }
    
    public static void clearCache() {
        if (!".tmp".equals(DIR)) return;
        File cache = new File(DIR);
        for (File file: cache.listFiles()) {
            file.delete();
        }
        cache.delete();
    }
    
    public static String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        
        return extension;
    }
    
    public static JsonValue configure() throws IOException {
        File f = new File("."); // current directory
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                File cfg = new File(file.getName() + "/non.cfg");
                if (cfg.exists()) {
                    CONFIG = file;
                    return new JsonReader().parse(new FileInputStream(cfg));
                }
            } else if (isZipFile(file)) {
                InputStream cfg = findInZip(file, "non.cfg");
                if (cfg != null) {
                    CONFIG = file;
                    return new JsonReader().parse(cfg);
                }
            } else if ("non.cfg".equals(file.getName())) {
                CONFIG = file;
                return new JsonReader().parse(new FileInputStream(file));
            }
        }

        return null;
    }
    
    public static void loadPackage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (CONFIG.isDirectory()) {
                        DIR = CONFIG.getName();
                    } else if (isZipFile(CONFIG)) {
                        DIR = ".tmp";
                        File dir = new File(DIR);
                        dir.mkdir();
                        ZipUtil.unpack(CONFIG, dir);
                    } else {
                        DIR = ".";
                    }
                    Game.loaded = true;
                } catch (IOException ex) {
                    Utils.error("Resource not found", ex.getMessage());
                }
            }
        }).start();
    }
    
    public static InputStream findInZip(File zip, String toSearch) throws IOException {
        //open the source zip file
        ZipFile sourceZipFile = new ZipFile(zip);
        Enumeration e = sourceZipFile.entries();
        
        while(e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)e.nextElement();
            if(entry.getName().toLowerCase().contains(toSearch)) {
                return sourceZipFile.getInputStream(entry);                     
            }
        }
        sourceZipFile.close();
        return null;
    }
    
    public static boolean isZipFile(File file) throws IOException {
        if(file.isDirectory()) {
            return false;
        }
        if(!file.canRead()) {
            throw new IOException("Cannot read file "+file.getAbsolutePath());
        }
        if(file.length() < 4) {
            return false;
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }
    
    public static FileHandle getInternalResource(String path) throws IOException {
        return Gdx.files.classpath("assets/" + path);
    }
    
    public static FileHandle getResource(String path) throws IOException {
        return new FileHandle(DIR + "/" + path);
    }
    
    public static String readFile(InputStream in) throws IOException {
        final StringBuffer sBuffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final char[] buffer = new char[1024];
        int cnt;
        while ((cnt = br.read(buffer, 0, buffer.length)) > -1) {
            sBuffer.append(buffer, 0, cnt);
        }
        br.close();
        in.close();
        return sBuffer.toString();
    }
    
    public static String repeat(String str, int n) {
    	String out = "";
    	for (int i = 0; i < n; i++) { out += str; }
    	return out;
    }
}