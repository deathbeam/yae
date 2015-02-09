package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import non.Non;

public class NonAudio extends Module {
    public Class<?> musicLoader = Music.class;
    public Class<?> soundLoader = Sound.class;
    
    public Music music(String file) {
        return (Non.assets.isLoaded(file)) ? (Music)Non.assets.get(file, musicLoader) : Gdx.audio.newMusic(Non.file(file));
    }
    
    public Sound sound(String file) {
        return (Non.assets.isLoaded(file)) ? (Sound)Non.assets.get(file, soundLoader) : Gdx.audio.newSound(Non.file(file));
    }
}