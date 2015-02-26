package non.modules;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;
import non.Line;

public class NonShapes extends Module {
    public Line line(float x1, float y1, float x2, float y2) {
        return new Line(x1, y1, x2, y2);
    }
    
    public Polygon polygon(float[] vertices) {
        return new Polygon(vertices);
    }
    
    public Polyline polyline(float[] vertices) {
        return new Polyline(vertices);
    }
    
    public Vector2 vector(float x, float y) {
        return new Vector2(x, y);
    }
    
    public Rectangle rectangle(float x, float y, float width, float height) {
        return new Rectangle(x, y, width, height);
    }
    
    public Circle circle(float x, float y, float radius) {
        return new Circle(x, y, radius);
    }
    
    public Ellipse ellipse(float x, float y, float width, float height) {
        return new Ellipse(x, y, width, height);
    }
}