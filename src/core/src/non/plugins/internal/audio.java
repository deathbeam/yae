package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import non.Non;
import non.plugins.Plugin;

public class audio extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return  "Plugin for playing audio and music files."; }
    
    public Class<?> musicLoader = Music.class;
    public Class<?> soundLoader = Sound.class;
    
    public Music music(String file) {
        return music(file, false);
    }
    
    public Music music(String file, boolean raw) {
        return (raw) ? Gdx.audio.newMusic(Non.file(file)) : (Music)Non.assets.get(file, musicLoader);
    }
    
    public Sound sound(String file) {
        return sound(file, false);
    }
    
    public Sound sound(String file, boolean raw) {
        return (raw) ? Gdx.audio.newSound(Non.file(file)) : (Sound)Non.assets.get(file, soundLoader);
    }
}