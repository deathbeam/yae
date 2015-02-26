require "fileutils"
require "non/version"
require "thor"

module Non
    CLI_DIR = File.expand_path(File.dirname(__FILE__))
    CLI_FILE = File.join(CLI_DIR, "non.launcher.jar")
    CLI_DATA = File.join(CLI_DIR, "non")
    PROJECT_DIR = File.expand_path(".")
    PROJECT_DATA = File.join(PROJECT_DIR, ".non")
    PROJECT_VERSION = File.join(PROJECT_DATA, "VERSION")
    
    EXECUTOR = "java -jar #{CLI_FILE} #{PROJECT_DIR} "
    
    def self.execute(cmd)
        system("#{EXECUTOR} #{cmd}")
    end
    
    class Build < Thor
        desc "build PLATFORM", "build your application for specified PLATFORM"
        def build(platform)
            Non.execute "#{platform}:dist --offline"
        end
        
        desc "start PLATFORM", "start your application for specified PLATFORM"
        def start(platform)
            Non.execute "#{platform}:run --offline"
        end
        
        desc "hello", "generates Hello World! project"
        def hello
            Non.execute "gen:hello --offline"
        end
        
        desc "update", "updates your projects Non version"
        def update
            if File.exists?(PROJECT_DATA)
                return
            end
            
            version = File.read(PROJECT_VERSION)
            
            if version == Non::VERSION 
                return
            end
            
            FileUtils.rm_rf(PROJECT_TEMP)
            FileUtils.copy_entry(CLI_DATA, PROJECT_TEMP)
        end
    end
end