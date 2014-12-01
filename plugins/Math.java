package com.codeindie.non.plugins;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Math extends Plugin {
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Easy math and geometry."; }
    
    public Ellipse newEllipse(float x, float y, float width, float height) {
        return new Ellipse(x, y, width, height);
    }
    
    public Polygon newPolygon(float[] vertices) {
        return new Polygon(vertices);
    }
    
    public Polyline newPolyline(float[] vertices) {
        return new Polyline(vertices);
    }
    
    public Vector2 newVector() {
        return newVector(0, 0);
    }
    
    public Vector2 newVector(float x, float y) {
        return new Vector2(x, y);
    }
    
    public Rectangle newRectangle() {
        return newRectangle(0, 0, 0, 0);
    }
    
    public Rectangle newRectangle(float x, float y, float width, float height) {
        return new Rectangle(x, y, width, height);
    }
    
    public Circle newCircle() {
        return newCircle(0, 0, 0);
    }
    
    public Circle newCircle(float x, float y, float radius) {
        return new Circle(x, y, radius);
    }
}
