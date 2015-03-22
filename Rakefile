require 'bundler/gem_tasks'

task :java do
    system 'javac lib/*.java'
    system 'jar cvfe bin/non.jar Main -C lib .'
end