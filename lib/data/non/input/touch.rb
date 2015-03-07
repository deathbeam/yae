module Touch
    java_import 'com.badlogic.gdx.Gdx'

    def self.x(pointer = 0)
        Gdx.input.getX(pointer)
    end
    
    def self.y(pointer = 0)
        Gdx.input.getY(pointer)
    end
    
    def self.down(pointer = 0)
        Gdx.input.isTouched(pointer)
    end
end