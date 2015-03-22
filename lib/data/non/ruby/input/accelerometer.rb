class Accelerometer
  java_import 'com.badlogic.gdx.Gdx'

  def x
    Gdx.input.getAccelerometerX()
  end
  
  def y
    Gdx.input.getAccelerometerY()
  end
  
  def z
    Gdx.input.getAccelerometerZ()
  end
  
  def rotation
    x, y, z
  end
  
  def orientation
    Gdx.input.getRotation()
  end
end