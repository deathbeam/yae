module App
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'non.Non'

    def self.width
        Gdx.graphics.getWidth()
    end
    
    def self.height
        Gdx.graphics.getHeight()
    end

    def self.fps
        Gdx.graphics.getFramesPerSecond()
    end
    
    def self.delta
        Gdx.graphics.getDeltaTime()
    end
    
    def self.clipboard
        Gdx.app.getClipboard()
    end
    
    def self.version
        Gdx.app.getVersion()
    end
    
    def self.platform
        Non.getPlatform()
    end
    
    def self.log(tag, msg)
        Gdx.app.log(tag, msg)
    end
    
    def self.debug(tag, msg)
        Gdx.app.debug(tag, msg)
    end
    
    def self.error(tag, msg)
        Gdx.app.error(tag, msg)
    end

    def self.thread(&block)
        Thread.new block
    end
    
    def self.quit
        Gdx.app.exit()
    end
end