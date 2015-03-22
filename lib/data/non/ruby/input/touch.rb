class Touch
  java_import 'com.badlogic.gdx.Gdx'

  def x(pointer = 0)
    Gdx.input.getX(pointer)
  end
  
  def y(pointer = 0)
    Gdx.input.getY(pointer)
  end
  
  def down(pointer = 0)
    Gdx.input.isTouched(pointer)
  end
end