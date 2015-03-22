class NON
  java_import 'non.JModule'
  
  require "non/ruby/files"
  require "non/ruby/app"
  require "non/ruby/audio"
  require "non/ruby/graphics"
  require "non/ruby/network"
  require "non/ruby/input/accelerometer"
  require "non/ruby/input/keyboard"
  require "non/ruby/input/mouse"
  require "non/ruby/input/touch"
  
  @files = Files.new
  @audio = Audio.new
  @graphics = Graphics.new
  @network = Network.new
  @accelerometer = Accelerometer.new
  @keyboard = Keyboard.new
  @mouse = Mouse.new
  @touch = Touch.new
  
  class << self
    attr_reader :files, :audio, :graphics, :network, :accelerometer, :keyboard, :mouse, :touch
      
    include App
  end
end

# Callbacks
def ready; end
def update(dt); end
def draw; end
def quit; end
def pause; end
def resume; end
def resize(w, h); end

# Input Callbacks
def keydown(key); end
def keyup(key); end
def keytyped(character); end
def touchdown(x, y, pointer, button); end
def touchup(x, y, pointer, button); end
def touchdragged(x, y, pointer); end
def mousemoved(x, y); end
def mousescrolled(amount); end

# Network Callbacks
def connected(connection); end
def disconnected(connection); end
def received(connection, data); end

require "main"