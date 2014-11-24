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
package com.deathbeam.non.plugins;

import com.deathbeam.non.Utils;
/**
 *
 * @author Thomas Slusny
 */
public abstract class Plugin {
    public abstract String name();
    public String author() { return "Unknown"; }
    public String license() { return "Public Domain"; }
    public String description() { return "Not specified."; }
    public String[] dependencies { return null; }
    
    public void loadPlugin() {
        Utils.log("## Plugin", name() + " loaded!"
        Utils.log("Author", author());
        Utils.log("License", license());
        Utils.log("Description", description());
        String[] depArray = dependencies();
        if (depArray != null) {
            String dependencies = "";
            for(String dependency: depArray) dependencies += dependency + ", ";
            Utils.log("Dependencies", dependencies);   
        } else {
            Utils.log("Dependencies", "None");
        }
    }
}
