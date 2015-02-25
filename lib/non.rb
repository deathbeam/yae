require "non/version"
require "thor"

module Non
    CLI_DIR = File.expand_path(File.dirname(__FILE__))
    CLI_FILE = File.join(CLI_DIR, "non.launcher.jar")
    PROJECT_DIR = File.expand_path(".")
    EXECUTOR = "java -jar #{CLI_FILE} #{PROJECT_DIR} "
    
    def self.execute(cmd)
        system("#{EXECUTOR} #{cmd}")
    end
    
    class Build < Thor
        desc "build PLATFORM", "build your application for specified PLATFORM"
        def build(platform)
            Non.execute "#{platform}:dist"
        end
        
        desc "start PLATFORM", "start your application for specified PLATFORM"
        def start(platform)
            Non.execute "#{platform}:run"
        end
        
        desc "hello", "generates Hello World! project"
        def hello
            Non.execute "gen:hello"
        end
    end
end