package non;

import com.badlogic.gdx.math.Shape2D;

public class Line implements Shape2D {
    public float x1, y1, x2, y2;
		
    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}