/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.deathbeam.nonfw.Utils;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author Thomas Slusny
 */
public class Audio {
    private Music currentMusic;
    private final HashSet<Sound> sounds = new HashSet<Sound>();
    
    public void play(Music music) {
        currentMusic = music;
        music.play();
    }
    
    public void play(Sound sound) {
        sounds.add(sound);
        sound.play();
    }
    
    public void stop() {
        stopMusic();
        stopSounds();
    }
    
    public void stopMusic() {
        currentMusic.stop();
    }
    
    public void stopSounds() {
        for(Sound sound: sounds) sound.stop();
    }
    
    public Music newMusic(String file) throws IOException {
        return Gdx.audio.newMusic(Utils.getResource(file));
    }
    
    public Sound newSound(String file) throws IOException {
        return Gdx.audio.newSound(Utils.getResource(file));
    }
    
    public void dispose() {
        stop();
        currentMusic.dispose();
        for(Sound sound: sounds) sound.dispose();
    }
}