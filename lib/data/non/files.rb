module Files
    java_import 'com.badlogic.gdx.Gdx'
    
    EXTERNAL_PATH = Gdx.files.getExternalStoragePath()
    LOCAL_PATH = Gdx.files.getLocalStoragePath()

    def self.internal(path)
        Gdx.files.internal(path)
    end

    def self.classpath(path)
        Gdx.files.classpath(path)
    end

    def self.local(path)
        Gdx.files.local(path)
    end

    def self.external(path)
        Gdx.files.external(path)
    end

    def self.absolute(path)
        Gdx.files.absolute(path)
    end

    def self.parse_yaml(text)
    end

    def self.parse_json(text)
    end

    def self.parse_xml(text)
    end
end