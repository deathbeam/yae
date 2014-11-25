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
package com.codeindie.non.plugins;

import com.codeindie.non.Utils;
import com.codeindie.non.scripting.ScriptRuntime;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author Thomas Slusny
 */
public class PluginManager {
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    public static void add(Plugin plugin) {
        Utils.log("##### Plugin", "Loading...");
        Utils.log("Name", plugin.name());
        Utils.log("Class", plugin.toString());
        Utils.log("Author", plugin.author());
        Utils.log("License", plugin.license());
        Utils.log("Description", plugin.description());
        String[] depArray = plugin.dependencies();
        
        if (depArray != null) {
            String dependencies = "";
            for(String dependency: depArray) dependencies += dependency + ", ";
            Utils.log("Dependencies", dependencies);   
        } else {
            Utils.log("Dependencies", "None");
        }
        
        plugin.load();
        plugins.put(plugin.name(), plugin);
        ScriptRuntime.getCurrent().put(plugin.name(), plugin);
        Utils.log("##### Plugin" "Loaded successfully!");
    }
    
    public static Plugin get(String plugin) {
        return plugins.get(plugin);
    }
    
    public static boolean contains(String plugin) {
        return plugins.containsKey(plugin);
    }
    
    public static void load() {
        Reflections reflections = new Reflections("com.codeindie.non.plugins");    
        Set<Class<? extends Plugin>> classes = reflections.getSubTypesOf(Plugin.class);
        
        for(Class<? extends Plugin> plugClass: classes) {
            try { add(plugClass.newInstance()); }
            catch(Exception e) { continue; }
        }
		
        for (String plugin: plugins.keySet()) {
            String[] deps = get(plugin).dependencies();
            if (deps == null) continue;
            for (String dep: deps) {
                if (!contains(dep)) Utils.error(dep, "Plugin dependency not resolved!");
            }
        }
    }
    
    public static void dispose() {
        for (String plugin: plugins.keySet()) 
            get(plugin).dispose();
    }
}
