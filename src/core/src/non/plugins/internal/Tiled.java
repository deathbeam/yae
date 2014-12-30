package non.plugins.internal;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.io.IOException;
import non.plugins.Plugin;

public class Tiled extends Plugin {
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Plugin for loading and rendering Tiled(TMX) map files."; }
    public String[] dependencies() { return new String[] { "graphics" }; }
    
    public class TileMap {
        public TiledMap data;
        private TiledMapRenderer renderer;
        public int tileSize;
        public String orientation;
        public final Vector2 size;

        public TileMap(String file) throws IOException {
            data = new TmxMapLoader().load(file);
            tileSize = (Integer)data.getProperties().get("tilewidth");
            orientation = (String)data.getProperties().get("orientation");
            size = new Vector2(
                (Integer)data.getProperties().get("width") * tileSize, 
                (Integer)data.getProperties().get("height") * tileSize);
        }

        public void draw(Graphics graphics) {
            graphics.display();
            if (renderer == null) {
                if("orthogonal".equals(orientation)) 
                    renderer = new OrthogonalTiledMapRenderer(data, 1, graphics.batch);
                if("isometric".equals(orientation))
                    renderer = new IsometricTiledMapRenderer(data, 1, graphics.batch);
            }
            renderer.setView(graphics.getProjection(), 0, 0, size.x, size.y);
            renderer.render();
            graphics.begin();
        }
    }
    
    public TileMap newMap(String file) throws IOException {
        return new TileMap(file);
    }
}