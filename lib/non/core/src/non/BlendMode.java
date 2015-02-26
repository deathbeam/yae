package non;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL20;

public class BlendMode {
    private static Map<String, int[]> blendMap;

    public static int[] getOpenGLBlendMode(String blendmode) {
        if (!blendMap.containsKey(blendmode)) return blendMap.get("alpha");
        return blendMap.get(blendmode);
    }

    public static void addBlendMode(String name, int sFactor, int dFactor) {
        blendMap.put(name, new int[]{sFactor, dFactor});
    }

    static {
        blendMap = new HashMap<String, int[]>();
        
        addBlendMode("alpha", GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA);
        addBlendMode("multiplicative", GL20.GL_DST_COLOR , GL20.GL_ZERO);
        addBlendMode("premultiplied", GL20.GL_ONE , GL20. GL_ONE_MINUS_SRC_ALPHA);
        addBlendMode("subtractive", GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE);
        addBlendMode("additive", GL20.GL_SRC_ALPHA , GL20.GL_ONE);
        addBlendMode("screen", GL20.GL_ONE , GL20.GL_ONE_MINUS_SRC_COLOR);
        addBlendMode("replace", GL20.GL_ONE , GL20.GL_ZERO);
    }
}