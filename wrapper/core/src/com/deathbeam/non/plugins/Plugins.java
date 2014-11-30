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

import com.deathbeam.non.Non;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;
/**
 *
 * @author Thomas Slusny
 */
public class Plugins {
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    public static void add(Plugin plugin) {
    	String name = plugin.getClass().getSimpleName();
    	String subStr = name.substring(0, 1);
    	name = name.replaceFirst(subStr, subStr.toLowerCase());
        Non.log("## Plugin", name);
        Non.log("Author", plugin.author());
        Non.log("License", plugin.license());
        Non.log("Description", plugin.description());
        String[] depArray = plugin.dependencies();
        if (depArray != null) {
            String dependencies = "";
            for(String dependency: depArray) dependencies += dependency + ", ";
            Non.log("Dependencies", dependencies);   
        } else {
            Non.log("Dependencies", "None");
        }
        plugins.put(name, plugin);
        Non.script.put(name, plugin);
    }
    
    public static Plugin get(String plugin) {
        return plugins.get(plugin);
    }
    
    public static boolean contains(String plugin) {
        return plugins.containsKey(plugin);
    }
    
    public static void load() {
        Non.log("PluginManager", "Loading plugins...");
        new _init().build();
        /* TODO:
         * Reflections reflections = new Reflections("com.deathbeam.non.plugins");    
         * Set<Class<? extends Plugin>> classes = reflections.getSubTypesOf(Plugin.class);
		 * for(Class<? extends Plugin> plugClass: classes) {
		 *    try {
		 *        add(plugClass.newInstance());
		 *    } catch(Exception e) {
		 *        Non.log(plugClass.toString(), e.getMessage());
		 *        continue;
		 *    }
		 *}
		 */
		
        for (String plugin: plugins.keySet()) {
            String[] deps = get(plugin).dependencies();
            if (deps == null) continue;
            for (String dep: deps) {
                if (!contains(dep)) Non.error(dep, "Plugin dependency not resolved!");
            }
        }
    }
}
