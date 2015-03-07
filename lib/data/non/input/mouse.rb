module Mouse
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'
    
    def self.show
        self.setVisible(true)
    end
    
    def self.hide
        self.setVisible(false)
    end
    
    def self.x
        Gdx.input.getX()
    end
    
    def self.y
        Gdx.input.getY()
    end
    
    def self.move(x, y)
        Gdx.input.setCursorPosition(x, y)
    end
    
    def self.down(button = :left)
        buttoncode = Non.getButton(name)
        Gdx.input.isButtonPressed(buttoncode)
    end
end