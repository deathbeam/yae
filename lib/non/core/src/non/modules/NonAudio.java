package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import non.Non;

public class NonAudio extends Module {
    public Sound load(String file) {
        return (Non.assets.isLoaded(file)) ? (Sound)Non.assets.get(file, Sound.class) : Gdx.audio.newSound(Non.file(file));
    }
}