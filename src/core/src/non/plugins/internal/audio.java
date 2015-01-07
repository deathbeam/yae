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
        return (Non.assets.isLoaded(file)) ? (Music)Non.assets.get(file, musicLoader) : Gdx.audio.newMusic(Non.file(file));
    }
    
    public Sound sound(String file) {
        return (Non.assets.isLoaded(file)) ? (Sound)Non.assets.get(file, soundLoader) : Gdx.audio.newSound(Non.file(file));
    }
}