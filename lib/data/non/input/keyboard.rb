class Keyboard
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'
    
    def show
        setVisible(true)
    end
    
    def hide
        setVisible(false)
    end
    
    def down(key)
        keycode = Non.getKey(name)
        Gdx.input.isKeyPressed(keycode) && keycode != 0
    end
end