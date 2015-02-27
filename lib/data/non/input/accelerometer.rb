module Accelerometer
    java_import 'com.badlogic.gdx.Gdx'

    def self.getX
        Gdx.input.getAccelerometerX()
    end
    
    def self.getY
        Gdx.input.getAccelerometerY()
    end
    
    def self.getZ
        Gdx.input.getAccelerometerZ()
    end
    
    def self.getRotation
        Gdx.input.getRotation()
    end
end