module Touch
    java_import 'com.badlogic.gdx.Gdx'

    def self.getX(pointer = 0)
        Gdx.input.getX(pointer)
    end
    
    def self.getY(pointer = 0)
        Gdx.input.getY(pointer)
    end
    
    def self.isDown(pointer = 0)
        Gdx.input.isTouched(pointer)
    end
end