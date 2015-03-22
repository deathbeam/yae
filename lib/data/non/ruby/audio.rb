class Audio
  java_import 'com.badlogic.gdx.Gdx'
  java_import 'non.Non'
  
  def sound(path, type = :internal)
    Gdx.audio.newSound NON.files.open(path, type)
  end
  
  def music(path, type = :internal)
    Gdx.audio.newMusic NON.files.open(path, type)
  end
  
  def play(source)
    source.play
  end
  
  def pause(source)
    source.pause
  end
  
  def resume(source)
    source.resume
  end
  
  def stop(source)
    source.stop
  end
  
  def loop(source)
    source.loop
  end
end