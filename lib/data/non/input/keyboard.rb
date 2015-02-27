module Keyboard
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'
    
    def self.show
        self.setVisible(true)
    end
    
    def self.hide
        self.setVisible(false)
    end
    
    def self.setVisible(val)
        Gdx.input.setOnscreenKeyboardVisible(val)
    end
    
    def self.isDown(key)
        keycode = Non.getKey(name)
        Gdx.input.isKeyPressed(keycode) && keycode != 0
    end
end