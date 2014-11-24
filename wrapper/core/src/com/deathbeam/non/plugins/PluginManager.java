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
import com.deathbeam.non.ScriptRuntime;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;
/**
 *
 * @author Thomas Slusny
 */
public class PluginManager {
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    public void add(Plugin plugin) {
        plugin.loadPlugin();
        plugins.put(plugin.name(), plugin);
        ScriptRuntime.getCurrent().put(plugin.name(), plugin);
    }
    
    public Plugin get(String plugin) {
        return plugins.get(plugin);
    }
    
    public boolean contains(String plugin) {
        return plugins.containsKey(plugin);
    }
    
    public void load() {
        Reflections reflections = new Reflections("com.deathbeam.non.plugins");    
        Set<Class<? extends Plugin>> classes = reflections.getSubTypesOf(Plugin.class);
        for(Class<? extends Plugin> plugClass: classes)
            add(new plugClass());
        check();
    }
    
    public void check() {
        for (String key: plugins.keySet()) {
            String[] deps = get(key).dependencies();
            if (deps == null) continue;
            for (String dep: deps) {
                if (!contains(dep) Utils.error("Plugin dependency not resolved!");
            }
        }
    }
}
