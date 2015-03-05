module Audio
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'com.badlogic.gdx.audio.Sound'
    java_import 'non.Non'
    
    def self.load(path)
        Non.assets.isLoaded(file) ? Non.assets.get(path, Sound.java_class) : Gdx.audio.newSound(Non.file(file))
    end
end