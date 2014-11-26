package com.codeindie.non.plugins;

import com.codeindie.non.Utils;
import com.codeindie.non.scripting.ScriptRuntime;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;

public class PluginManager {
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    public static void add(Plugin plugin) {
        Utils.log("##### Plugin", "Loading...");
        Utils.log("Name", plugin.name());
        Utils.log("Class", plugin.getClass().getSimpleName());
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
        Utils.log("##### Plugin", "Loaded successfully!");
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
