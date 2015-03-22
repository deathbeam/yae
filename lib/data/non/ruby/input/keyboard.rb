class Keyboard
  java_import 'com.badlogic.gdx.Gdx'
  java_import 'non.Non'
  
  def show
    Gdx.input.setOnscreenKeyboardVisible(true)
  end
  
  def hide
    Gdx.input.setOnscreenKeyboardVisible(false)
  end
  
  def down(key)
    keycode = Non.getKey(name)
    Gdx.input.isKeyPressed(keycode) && keycode != 0
  end
end