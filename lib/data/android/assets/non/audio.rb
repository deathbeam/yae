module Audio
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'com.badlogic.gdx.audio.Sound'
    java_import 'non.Non'
    require_relative 'files'
    
    def self.load(path)
        Non.assets.isLoaded(file) ? Non.assets.get(path, Sound.java_class) : Gdx.audio.newSound(Files.internal(file))
    end
end