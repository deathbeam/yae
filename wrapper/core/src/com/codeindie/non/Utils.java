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
package com.deathbeam.non;

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

/**
 *
 * @author Thomas Slusny
 */

public class Utils {
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
	
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i+1);
        return extension;
    }
    
    public static JsonValue configure() throws IOException {
        File cfg = getResource("non.cfg").file();
        if (cfg.exists()) return new JsonReader().parse(new FileInputStream(cfg));
        return null;
    }
    
    public static FileHandle getInternalResource(String path) throws IOException {
        return Gdx.files.classpath(path);
    }
    
    public static FileHandle getResource(String path) throws IOException {
        return Gdx.files.classpath(path);
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
