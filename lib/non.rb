require "fileutils"
require "thor"

module Non
    VERSION = "5.0.2"
    CLI_DIR = File.expand_path(File.dirname(__FILE__))
    CLI_DATA = File.join(CLI_DIR, "data")
    CLI_FILE = File.join(CLI_DIR, "non.launcher.jar")
    PROJECT_DIR = File.expand_path(".")
    PROJECT_DATA = File.join(PROJECT_DIR, ".non")
    PROJECT_FILE = File.join(PROJECT_DATA, "VERSION")
    EXECUTOR = "java -jar #{CLI_FILE} #{PROJECT_DIR} "
    
    def self.execute(cmd)
        system("#{EXECUTOR} #{cmd}")
    end

    def self.check
        if not(File.exists?(PROJECT_DATA))
            FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
        end
    end
    
    class Build < Thor
        desc "build PLATFORM", "build your application for specified PLATFORM"
        def build(platform)
            Non.check
            Non.execute "#{platform}:dist"
        end
        
        desc "start PLATFORM", "start your application for specified PLATFORM"
        def start(platform)
            Non.check
            Non.execute "#{platform}:run"
        end
        
        desc "hello", "generate Hello World! project"
        def hello
            Non.check
            Non.execute "hello"
        end
        
        desc "update", "update your project's runtime version"
        def update
            Non.check
            version = File.read(PROJECT_FILE)
            
            if version != Non::VERSION 
                FileUtils.rm_rf(PROJECT_DATA)
                FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
            end
        end
        
        desc "version", "prints current NON version"
        def version
            puts Non::VERSION
        end
    end
end