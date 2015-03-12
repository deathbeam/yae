require "fileutils"
require "thor"

module Non
    VERSION = "5.3.0"
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
            FileUtils.copy_entry(CLI_DATA, PROJECT_DATA)
            Non.execute "resolveDependencies"
        end
    end
    
    class Build < Thor
        desc "build <PLATFORM> [--compile]", "build your application for specified platform with optional ruby to bytecode compiling"
        option :compile, :type => :boolean
        def build(platform)
            puts "Packaging your application\n"
            Non.check
            Non.execute options[:compile] ? "update compileRuby compileNonRuby #{platform}:dist --offline" : "update #{platform}:dist --offline"
        end
        
        desc "start <PLATFORM>", "start your application for specified platform"
        def start(platform)
            puts "Starting your application\n"
            Non.check
            Non.execute "update #{platform}:run --offline"
        end
        
        desc "hello", "generate Hello World! project"
        def hello
            puts "Generating Hello World! project\n"
            Non.check
            Non.execute "hello --offline"
        end
        
        desc "clean", "clean temporary data for your project"
        def clean
            puts "Cleaning your project's temporary data\n"
            Non.check
            Non.execute "clean --offline"
        end
        
        desc "update", "update your project's runtime version and dependencies"
        def update
            puts "Updating your project's runtime\n"
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