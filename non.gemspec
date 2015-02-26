# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'non'

Gem::Specification.new do |spec|
  spec.name          = "non"
  spec.version       = Non::VERSION
  spec.authors       = ["Thomas Slusny"]
  spec.email         = ["slusnucky@gmail.com"]

  spec.summary       = %q{A platform for developing app using Ruby on Windows, Linux, Mac, Android and iOS.}
  spec.description   = %q{Hey folks! NON is a super easy framework you can use to make 2D games in Ruby. It's free, open-source, and works on Windows, Mac OS X, Linux, Android and iOS.}
  spec.homepage      = "http://non2d.github.io"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0").reject { |f| f.match(%r{^(test|spec|features)/}) }
  spec.executables   = ["non"]
  spec.require_paths = ["lib"]
  
  spec.add_runtime_dependency "thor", "~> 0.19.1"
  spec.add_development_dependency "bundler", "~> 1.8"
  spec.add_development_dependency "rake", "~> 10.0"
end