class Files
    java_import 'com.badlogic.gdx.Gdx'
    java_import 'org.yaml.snakeyaml.Yaml'
    java_import 'com.badlogic.gdx.utils.JsonReader'
    java_import 'com.badlogic.gdx.utils.XmlReader'

    def path_external
        Gdx.files.getExternalStoragePath
    end
    
    def path_local
        Gdx.files.getLocalStoragePath
    end
    
    def internal(path)
        Gdx.files.internal(path)
    end

    def classpath(path)
        Gdx.files.classpath(path)
    end

    def local(path)
        Gdx.files.local(path)
    end

    def external(path)
        Gdx.files.external(path)
    end

    def absolute(path)
        Gdx.files.absolute(path)
    end

    def parse_yaml(text)
        Yaml.new.load(text)
    end

    def parse_json(text)
        JsonReader.new.parse(text)
    end
    
    def parse_xml(text)
        XmlReader.new.parse(text)
    end
end