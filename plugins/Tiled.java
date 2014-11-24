package com.deathbeam.non.plugins;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.deathbeam.non.Utils;
import java.io.IOException;

public class Tiled extends Plugin {
    public String name() { return "tiled"; }
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Plugin for loading and rendering Tiled(TMX) map files."; }
    public String[] dependencies() { return new String[] { "graphics" }; }
    
    class TiledMap {
        public final com.badlogic.gdx.maps.tiled.TiledMap map;
        private TiledMapRenderer renderer;
        public int tileSize;
        public String orientation;
        public final Vector2 size;

        public TiledMap(String file) throws IOException {
            map = new TmxMapLoader().load(Utils.DIR + "/" + file);
            tileSize = (Integer)map.getProperties().get("tilewidth");
            orientation = (String)map.getProperties().get("orientation");
            size = new Vector2((Integer)map.getProperties().get("width") * tileSize, (Integer)map.getProperties().get("height") * tileSize);
        }

        public void draw(Graphics graphics) {
            graphics.end();
            if (renderer == null) {
                if("orthogonal".equals(orientation)) renderer = new OrthogonalTiledMapRenderer(map, 1, gfx.batch);
                if("isometric".equals(orientation)) renderer = new IsometricTiledMapRenderer(map, 1, gfx.batch);
            }
            renderer.setView(gfx.getProjection(), 0, 0, size.x, size.y);
            renderer.render();
            graphics.begin();
        }
    }
    
    public TiledMap newMap(String file) throws IOException {
        return new TiledMap(file);
    }
}
