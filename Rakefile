require 'bundler/gem_tasks'

task :java do
    system 'javac javalib/*.java'
    system 'jar cvfe lib/non.jar Main -C javalib .'
end