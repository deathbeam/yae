class Mouse
  java_import 'com.badlogic.gdx.Gdx'
  java_import 'non.Non'
  
  def show
    Gdx.input.setCursorCatched(false)
  end
  
  def hide
    Gdx.input.setCursorCatched(true)
  end
  
  def x
    Gdx.input.getX()
  end
  
  def y
    Gdx.input.getY()
  end
  
  def move(x, y)
    Gdx.input.setCursorPosition(x, y)
  end
  
  def down(button = :left)
    buttoncode = Non.getButton(name)
    Gdx.input.isButtonPressed(buttoncode)
  end
end