module App
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'

    def width
        Gdx.graphics.getWidth()
    end
    
    def height
        Gdx.graphics.getHeight()
    end

    def fps
        Gdx.graphics.getFramesPerSecond()
    end
    
    def delta
        Gdx.graphics.getDeltaTime()
    end
    
    def clipboard
        Gdx.app.getClipboard()
    end
    
    def version
        Gdx.app.getVersion()
    end
    
    def platform
        Non.getPlatform()
    end
    
    def log(tag, msg)
        Gdx.app.log(tag, msg)
    end
    
    def debug(tag, msg)
        Gdx.app.debug(tag, msg)
    end
    
    def error(tag, msg)
        Gdx.app.error(tag, msg)
    end
    
    def quit
        Gdx.app.exit()
    end
end