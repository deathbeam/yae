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
    
    public static void init(String[] plugins) {
        for(String plugin: plugins) {
            try {
                load((Plugin)ClassReflection.newInstance(ClassReflection.forName("non.plugins." + plugin)));
            } catch(Exception e) {
                Non.error(Non.TAG, Non.E_PLUGIN + name);
            }
        }
        
        for(Plugin plugin: plugins.values()) plugin.plugin_load();
    }
    
    public static void load(Plugin plugin) {
        Non.log(Non.TAG, "Loading plugin " + plugin.name());
        Non.log(Non.TAG, "> author - " + plugin.author());
        Non.log(Non.TAG, "> license - " + plugin.license());
        Non.log(Non.TAG, "> description - " + plugin.description());
        
        String[] depArray = plugin.dependencies();

        if (depArray != null) {
            String dependencies = "";
            for(String dependency: depArray) dependencies += dependency + ", ";
            Non.log(Non.TAG, "> dependencies - " + dependencies);
        }
        
        plugins.put(plugin.name(), plugin);
        Non.script.put(plugin.name(), plugin);
    }
    
    public static Plugin get(String name) {
        if (plugins.containsKey(name) {
            return plugins.get(name);
        } else {
            Non.error(Non.TAG, Non.E_PLUGIN + name);
            Non.quit();
            return null;
        }
    }
    
    public static void updateBefore() { for(Plugin plugin: plugins.values()) plugin.plugin_update_before(); }
    public static void updateAfter() { for(Plugin plugin: plugins.values()) plugin.plugin_update_after(); }
    public static void resize() { for(Plugin plugin: plugins.values()) plugin.plugin_resize(); }
    public static void dispose() { for(Plugin plugin: plugins.values()) plugin.plugin_unload(); }
}