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
non = {
    extend: function(type, object) {
        return new JavaAdapter(type, object);
    },
    
    getFPS: function() { return Packages.com.badlogic.gdx.Gdx.graphics.getFramesPerSecond(); },
    getDelta: function() { return Packages.com.badlogic.gdx.Gdx.graphics.getDeltaTime(); },
    log: function(tag, msg) { Packages.com.deathbeam.nonfw.Utils.log(tag, msg); },
    error: function(tag, msg) { Packages.com.deathbeam.nonfw.Utils.error(tag, msg); },
    warning: function(tag, msg) { Packages.com.deathbeam.nonfw.Utils.warning(tag, msg); },
    debug: function(tag, msg) { Packages.com.deathbeam.nonfw.Utils.debug(tag, msg); },
    
    int_init: function(config) {
        this.config = config;
        var mods = config.get("modules").asStringArray();
        for (var i = 0; i < mods.length; i++) {
            if ("audio".equals(mods[i])) non.audio = new Packages.com.deathbeam.nonfw.audio.Audio();
            if ("graphics".equals(mods[i])) non.graphics = new Packages.com.deathbeam.nonfw.graphics.Graphics();
            if ("keyboard".equals(mods[i])) non.keyboard = new Packages.com.deathbeam.nonfw.input.Keyboard();
            if ("mouse".equals(mods[i])) non.mouse = new Packages.com.deathbeam.nonfw.input.Mouse();
            if ("touch".equals(mods[i])) non.touch = new Packages.com.deathbeam.nonfw.input.Touch();
            if ("math".equals(mods[i])) non.math = new Packages.com.deathbeam.nonfw.math.Math();
            if ("tiled".equals(mods[i])) non.tiled = new Packages.com.deathbeam.nonfw.tiled.Tiled();
            if ("network".equals(mods[i])) {
                Listener = Packages.com.deathbeam.nonfw.network.Listener;
                non.network = new Packages.com.deathbeam.nonfw.network.Network();
            }
        }
        
        if (this.init) this.init();
    },
    
    int_draw: function() {
        if (this.graphics) this.graphics.begin();
        if (this.draw) this.draw();
        if (this.graphics) this.graphics.end();
    },
    
    int_update: function() {
        if (this.update) this.update();
    },
    
    int_resize: function() {
        if (this.resize) this.resize();
    },
    
    int_dispose: function() {
        if (this.dispose) this.dispose();
        if (this.graphics) this.graphics.dispose();
        if (this.audio) this.audio.dispose();
    }
};