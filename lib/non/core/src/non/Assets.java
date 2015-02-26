package non;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class Assets extends AssetManager {
    public void add(String asset) {
        String extension = Non.getExtension(asset);
        
        if (extension.equalsIgnoreCase("png") ||
            extension.equalsIgnoreCase("jpg") ||
            extension.equalsIgnoreCase("bmp")) {
            load(asset, Texture.class);
        } else if (extension.equalsIgnoreCase("fnt")) {
            load(asset, BitmapFont.class);
        } else if (extension.equalsIgnoreCase("particle")) {
            load(asset, ParticleEffect.class);
        } else if (extension.equalsIgnoreCase("mp3") ||
            extension.equalsIgnoreCase("wav") ||
            extension.equalsIgnoreCase("ogg")) {
            load(asset, Sound.class);
        } 
    }
}