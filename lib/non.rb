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
            update
            Non.execute "#{platform}:dist"
        end
        
        desc "start PLATFORM", "start your application for specified PLATFORM"
        def start(platform)
            update
            Non.execute "#{platform}:run"
        end
        
        desc "hello", "generates Hello World! project"
        def hello
            update
            Non.execute "gen:hello"
        end
        
        desc "update", "updates your projects Non version"
        def update
            if not(File.exists?(PROJECT_DATA))
                FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
            end
            
            version = File.read(PROJECT_VERSION)
            
            if version != Non::VERSION 
                FileUtils.rm_rf(PROJECT_DATA)
                FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
            end
        end
    end
end