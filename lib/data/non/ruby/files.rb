class Files
  java_import 'com.badlogic.gdx.Gdx'
  
  def get_external_path
    Gdx.files.getExternalStoragePath
  end
  
  def get_local_path
    Gdx.files.getLocalStoragePath
  end
  
  def open(path, type = :internal)
    case type
    when :internal
      Gdx.files.internal(path)
    when :local
      Gdx.files.local(path)
    when :external
      Gdx.files.external(path)
    when :classpath
      Gdx.files.classpath(path)
    when :absolute
      Gdx.files.absolute(path)
    else
      Gdx.files.internal(path)
    end
  end
  
  def exists(path, type = :internal)
    open(path, type).exists
  end
  
  def is_directory(path, type = :internal)
    open(path, type).isDirectory
  end
  
  def is_file(path, type = :internal)
    !is_directory path, type
  end
  
  def read(path, type = :internal)
    open(path, type).readString
  end
  
  def write(path, text, type = :internal)
    open(path, type).writeString text, false
  end
  
  def append(path, text, type = :internal)
    open(path, type).writeString text, true
  end
  
  def remove(path, type = :internal)
    open(path, type).delete
  end
  
  def size(path, type = :internal)
    open(path, type).length
  end
  
  def last_modified(path, type = :internal)
    open(path, type).lastModified
  end
  
  def list(path, type = :internal)
    open(path, type).list
  end
  
  def mkdir(path, type = :internal)
    open(path, type).mkdirs
  end
end