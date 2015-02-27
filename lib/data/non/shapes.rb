module Shapes
    java_import 'non.ModuleHandler'
    java_import 'com.badlogic.gdx.math.Circle'
    java_import 'com.badlogic.gdx.math.Ellipse'
    java_import 'com.badlogic.gdx.math.Polygon'
    java_import 'com.badlogic.gdx.math.Rectangle'
    java_import 'com.badlogic.gdx.math.Vector2'
    java_import 'non.Line'
    
    def self.line(x1, y1, x2, y2)
        Line.new(x1, x2, y1, y2)
    end
    
    def self.polygon(vertices)
        Polygon.new(vertices)
    end
    
    def self.vector(x, y)
        Vector.new(x, y)
    end
    
    def self.rectangle(x, y, width, height)
        Rectangle.new(x, y, width, height)
    end
    
    def self.circle(x, y, radius)
        Circle.new(x, y, radius)
    end
    
    def self.ellipse(x, y, width, height)
        Ellipse.new(x, y, width, height)
    end
end