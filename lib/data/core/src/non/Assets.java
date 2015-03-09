package non;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class Assets extends AssetManager {
    public void add(String[] assets, String type) {
        for (String asset: assets) {
            add(asset, type);
        }
    }
    
    public void add(String asset, String type) {
        if (type.equals("image")) {
            load(asset, Texture.class);
        } else if (type.equals("font")) {
            load(asset, BitmapFont.class);
        } else if (type.equals("effect")) {
            load(asset, ParticleEffect.class);
        } else if (type.equals("sound")) {
            load(asset, Sound.class);
        } else if (type.equals("music")) {
            load(asset, Music.class);
        }
    }
}