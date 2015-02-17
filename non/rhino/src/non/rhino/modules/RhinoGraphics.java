package non.rhino.modules;

import com.badlogic.gdx.graphics.Texture;
import org.mozilla.javascript.Scriptable;
import non.rhino.Arguments;
import non.modules.NonGraphics;


public class RhinoGraphics extends NonGraphics {
    public NonGraphics print(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        if (!args.has("text")) return this;
        
        String text = args.getString("text", "");
        String align = args.getString("align", "left");
        float scale[] = args.getNumArray("scale", new float[]{1,1});
        float wrap = args.getNum("wrap", curFont.getBounds(text).width);
        float position[] = args.getNumArray("position", new float[]{0,0});

        return print(text, position, scale, wrap, align);
    }
    
    public NonGraphics fill(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        if (!args.has("shape")) return this;
        
        Object shape = args.get("shape", null);
        String mode = args.getString("mode", "line");
        
        return fill(shape, mode);
    }
    
    public NonGraphics draw(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        if (args.has("image")) {
            Texture image = (Texture)args.get("image", null);
            float[] position = args.getNumArray("position", new float[]{0,0});
            float[] origin = args.getNumArray("origin", new float[]{0,0});
            float[] size = args.getNumArray("size", new float[]{image.getWidth(),image.getHeight()});
            float[] scale = args.getNumArray("scale", new float[]{1,1});
            float rotation = args.getNum("rotation", 0f);
            float[] source = args.getNumArray("source", new float[]{0,0,image.getWidth(),image.getHeight()});
            
            return draw(image, position,  size, origin, scale, source, rotation);
        }
        
        return this;
    }
}