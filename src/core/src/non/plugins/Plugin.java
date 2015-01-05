package non.plugins;

import non.Non;
import non.plugins.internal._init;
import java.util.HashMap;
import java.util.Set;

public abstract class Plugin {
    public String author()         { return "Unknown"; }
    public String license()        { return "Public Domain"; }
    public String description()    { return "Not specified."; }
    public String[] dependencies() { return null; }
    public String name()           { return this.getClass().getSimpleName(); }
	
    public Plugin() { load(this); }
    public void plugin_load() { }
    public void plugin_unload() { }
    public void plugin_resize() { }
    public void plugin_update_before() { }
    public void plugin_update_after() { }
    
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    public static void load(Plugin plugin) {
        Non.log("Name", plugin.name());
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
        
        plugins.put(plugin.name(), plugin);
        Non.script.put(plugin.name(), plugin);
    }
    
    public static Plugin get(String name) {
        return plugins.get(name);
    }
    
    public static void loadAll() {
        Non.log("PluginManager", "Loading plugins...");
        new _init();
        for(Plugin plugin: plugins.values()) plugin.plugin_load();
    }
    
    public static void updateBefore() { for(Plugin plugin: plugins.values()) plugin.plugin_update_before(); }
    public static void updateAfter() { for(Plugin plugin: plugins.values()) plugin.plugin_update_after(); }
    public static void resize() { for(Plugin plugin: plugins.values()) plugin.plugin_resize(); }
    public static void dispose() { for(Plugin plugin: plugins.values()) plugin.plugin_unload(); }
}