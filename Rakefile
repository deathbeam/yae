require 'bundler/gem_tasks'

task :java do
    system 'javac java/*.java'
    system 'jar cvfe lib/non.jar Main -C java .'
end