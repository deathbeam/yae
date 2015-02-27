module Mouse
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'
    
    def self.show
        self.setVisible(true)
    end
    
    def self.hide
        self.setVisible(false)
    end
    
    def self.setVisible(val)
        Gdx.input.setCursorCatched(not(val))
    end
    
    def self.getX
        Gdx.input.getX()
    end
    
    def self.getY
        Gdx.input.getY()
    end
    
    def self.setPosition(x, y)
        Gdx.input.setCursorPosition(x, y)
    end
    
    def self.isDown(button = "left")
        buttoncode = Non.getButton(name)
        Gdx.input.isButtonPressed(buttoncode)
    end
end