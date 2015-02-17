package non.rhino.modules;

import box2dLight.Light;
import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import non.Non;
import non.Line;
import non.rhino.Arguments;
import non.rhino.RhinoNon;
import non.modules.NonLights;

public class RhinoLights extends NonLights {
    public DirectionalLight directional(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int rays = (int)args.getNum("rays", 100f);
        Color color = (Color)args.get("color", Color.WHITE);
        float direction = args.getNum("direction", 0f);
        
        return directional(rays, color, direction);
    }
    
    public PointLight point(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int rays = (int)args.getNum("rays", 100f);
        Color color = (Color)args.get("color", Color.WHITE);
        float distance = args.getNum("distance", 100f);
        float[] position = args.getNumArray("position", new float[]{0,0});
        
        return point(rays, color, distance, position[0], position[1]);
    }
    
    public ConeLight cone(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int rays = (int)args.getNum("rays", 100f);
        Color color = (Color)args.get("color", Color.WHITE);
        float distance = args.getNum("distance", 100f);
        float[] position = args.getNumArray("position", new float[]{0,0});
        float direction = args.getNum("direction", 0f);
        float cone = args.getNum("cone", 20f);
        
        return cone(rays, color, distance, position[0], position[1], direction, cone);
    }
    
    public ChainLight chain(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        
        int rays = (int)args.getNum("rays", 100f);
        Color color = (Color)args.get("color", Color.WHITE);
        float distance = args.getNum("distance", 100f);
        String orientation = args.getString("orientation", "left");
        float[] chains = args.getNumArray("chains", null);
        
        if (orientation.equals("right")) {
            return chain(rays, color, distance, -1, chains);
        }
        
        return chain(rays, color, distance, 1, chains);
    }
}