package non.plugins;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.HashMap;
import java.util.Set;

import non.Non;

public abstract class Plugin {
    public String author()         { return "Unknown"; }
    public String license()        { return "Public Domain"; }
    public String description()    { return "Not specified."; }
    public String[] dependencies() { return null; }
    public String name()           { return this.getClass().getSimpleName(); }

    public void plugin_load() { }
    public void plugin_unload() { }
    public void plugin_resize() { }
    public void plugin_update_before() { }
    public void plugin_update_after() { }
    
    private static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    private static void initPlugin(String name) {
        try {
            plugins.put(
                name,
                (Plugin)ClassReflection.newInstance(ClassReflection.forName("non.plugins." + name))
            );
        } catch(Exception e) {
            Non.error(Non.TAG, Non.E_PLUGIN + name);
            Non.quit();
        }
    }
    
    public static void init(String[] plugins) {
        load("non");
            
        for(String plugin: plugins) {
            load(plugin);
        }
    }
    
    public static void load(String name) {
        if (plugins.containsKey(name)) return;
        
        initPlugin(name);
        
        Plugin plugin = get(name);
        
        Non.log(Non.TAG, "Loading plugin " + plugin.name());
        Non.log(Non.TAG, "> author - " + plugin.author());
        Non.log(Non.TAG, "> license - " + plugin.license());
        Non.log(Non.TAG, "> description - " + plugin.description());

        Non.script.put(plugin.name(), plugin);
        
        String[] depArray = plugin.dependencies();

        if (depArray != null) {
            for(String dependency: depArray) {
                load(dependency);
            }
        }
        
        plugin.plugin_load();
    }
    
    public static Plugin get(String name) {
        if (plugins.containsKey(name)) {
            return plugins.get(name);
        } else {
            Non.error(Non.TAG, Non.E_PLUGIN + name);
            return null;
        }
    }
    
    public static void updateBefore() { for(Plugin plugin: plugins.values()) plugin.plugin_update_before(); }
    public static void updateAfter() { for(Plugin plugin: plugins.values()) plugin.plugin_update_after(); }
    public static void resize() { for(Plugin plugin: plugins.values()) plugin.plugin_resize(); }
    public static void dispose() { for(Plugin plugin: plugins.values()) plugin.plugin_unload(); }
}