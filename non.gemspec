# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)

Gem::Specification.new do |spec|
  spec.name          = "non"
  spec.version       = "0.5.0"
  spec.authors       = ["Thomas Slusny"]
  spec.email         = ["slusnucky@gmail.com"]

  spec.summary       = %q{A platform for developing games using Ruby and Lua on Windows, Linux, Mac, Android and iOS.}
  spec.description   = %q{NÖN is an engine you can use to make 2D games in Ruby and Lua. It's free, open-source, and works on Windows, Mac OS X, Linux, Android and iOS.}
  spec.homepage      = "http://non2d.github.io"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0").reject { |f| f.match(%r{^(test|spec|features|lib)/}) }
  spec.date          = Time.now.strftime("%Y-%m-%d")
  spec.executables   = ["non.jar", "non"]
  
  spec.add_development_dependency "bundler", "~> 1.8"
  spec.add_development_dependency "rake", "~> 10.0"
end