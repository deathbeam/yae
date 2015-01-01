package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import non.Non;
import non.plugins.Plugin;

public class Audio extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return  "Plugin for playing audio and music files."; }
    
    public Class<?> music = Music.class;
    public Class<?> sound = Sound.class;
    
    public Music newMusic(String file) {
        return newMusic(file, false);
    }
    
    public Music newMusic(String file, boolean raw)
        return (raw) ? Gdx.audio.newMusic(Non.file(file)) : Non.assets.get(file, music);
    }
    
    public Music newSound(String file) {
        return newSound(file, false);
    }
    
    public Music newSound(String file, boolean raw)
        return (raw) ? Gdx.audio.newSound(Non.file(file)) : Non.assets.get(file, sound);
    }
}