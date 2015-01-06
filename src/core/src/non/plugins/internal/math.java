package non.plugins.internal;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;
import non.plugins.Plugin;

public class math extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Easy math and geometry."; }
    
    public class Quad {
        public int sx, sy, sw, sh, w, h;
        
        public Quad (int x1, int y1, int x2, int y2, int x3, int y3) {
            sx = x1;
            sy = y1;
            sw = x2;
            sh = y2;
            w = x3;
            h = y3;
        }
    }
	
    public class Line implements Shape2D {
        public float x1, y1, x2, y2;
		
        public Line(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
    
    public Line line (float x1, float y1, float x2, float y2) {
        return new Line(x1, y1, x2, y2);
    }
    
    public Quad quad (int sx, int sy, int sw, int sh) {
        return quad(sx,sy,sw,sh,sw,sh);
    }
    
    public Quad quad (int sx, int sy, int sw, int sh, int w, int h) {
        return new Quad(sx,sy,sw,sh,w,h);
    }
    
    public Polygon polygon(float[] vertices) {
        return new Polygon(vertices);
    }
    
    public Polyline polyline(float[] vertices) {
        return new Polyline(vertices);
    }
    
    public Vector2 vector() {
        return vector(0, 0);
    }
    
    public Vector2 vector(float x, float y) {
        return new Vector2(x, y);
    }
    
    public Rectangle rectangle() {
        return rectangle(0, 0, 0, 0);
    }
    
    public Rectangle rectangle(float x, float y, float width, float height) {
        return new Rectangle(x, y, width, height);
    }
    
    public Circle circle() {
        return circle(0, 0, 0);
    }
    
    public Circle circle(float x, float y, float radius) {
        return new Circle(x, y, radius);
    }
    
    public Ellipse ellipse() {
        return ellipse(0, 0, 0, 0);
    }
    
    public Ellipse ellipse(float x, float y, float width, float height) {
        return new Ellipse(x, y, width, height);
    }

    public float abs(float num) { return Math.abs(num); }
    public float random() { return MathUtils.random(); }
    public float random(float range) { return MathUtils.random(range); }
    public float random(float start, float end) { return MathUtils.random(start, end); }
    public int random(int range) { return MathUtils.random(range); }
    public int random(int start, int end) { return MathUtils.random(start, end); }
    public boolean isPowerOfTwo(int num) { return MathUtils.isPowerOfTwo(num); }
    public int nextPowerOfTwo(int num) { return MathUtils.nextPowerOfTwo(num); }
}