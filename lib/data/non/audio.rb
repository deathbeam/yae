class Audio
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'com.badlogic.gdx.audio.Sound'
    java_import 'com.badlogic.gdx.audio.Music'
    java_import 'non.Non'
    
    def sound(path)
        Non.assets.isLoaded(file) ? Non.assets.get(path, Sound.java_class) : Gdx.audio.newSound(Non.file(file))
    end
    
    def music(path)
        Non.assets.isLoaded(file) ? Non.assets.get(path, Music.java_class) : Gdx.audio.newMusic(Non.file(file))
    end
end