require "fileutils"
require "thor"

module Non
    VERSION = "5.1.0"
    CLI_DIR = File.expand_path(File.dirname(__FILE__))
    CLI_DATA = File.join(CLI_DIR, "data")
    CLI_FILE = File.join(CLI_DIR, "non.jar")
    PROJECT_DIR = File.expand_path(".")
    PROJECT_DATA = File.join(PROJECT_DIR, ".non")
    PROJECT_FILE = File.join(PROJECT_DATA, "VERSION")
    EXECUTOR = "java -jar #{CLI_FILE} #{PROJECT_DATA} "
    
    def self.execute(cmd)
        system("#{EXECUTOR} #{cmd}")
    end

    def self.check
        unless File.exists?(PROJECT_DATA)
            Non.execute "resolveDependencies"
            FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
        end
    end
    
    class Build < Thor
        desc "build <platform>", "build your application for specified <platform>"
        def build(platform)
            Non.check
            Non.execute "update compileRuby #{platform}:dist --offline"
        end
        
        desc "start <platform>", "start your application for specified <platform>"
        def start(platform)
            Non.check
            Non.execute "update compileRuby #{platform}:run --offline"
        end
        
        desc "hello", "generate Hello World! project"
        def hello
            Non.check
            Non.execute "hello --offline"
        end
        
        desc "update", "update your project's runtime version and dependencies"
        def update
            Non.check
            version = File.read(PROJECT_FILE)
            puts "v#{version} found"
            
            unless version == Non::VERSION 
                FileUtils.rm_rf(PROJECT_DATA)
                Non.check
            end
        end
        
        desc "version", "print current compiler version"
        def version
            puts "NÖN v#{Non::VERSION}"
        end
    end
end