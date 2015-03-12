class NON
    require "non/app"
    require "non/audio"
    require "non/files"
    require "non/graphics"
    require "non/lights"
    require "non/network"
    require "non/particles"
    require "non/physics"
    require "non/shapes"
    require "non/input/accelerometer"
    require "non/input/keyboard"
    require "non/input/mouse"
    require "non/input/touch"
    
    @audio = Audio.new
    @files = Files.new
    @graphics = Graphics.new
    @physics = Physics.new
    @particles = Particles.new
    @lights = Lights.new
    @network = Network.new
    @shapes = Shapes.new
    @accelerometer = Accelerometer.new
    @keyboard = Keyboard.new
    @mouse = Mouse.new
    @touch = Touch.new
    
    class << self
        attr_reader :audio, :files, :graphics, :physics,
                    :particles, :lights, :network, :shapes, 
                    :accelerometer, :keyboard, :mouse, :touch
        include App
    end
end

# Callbacks
def init(assets); end
def ready; end
def update(dt); end
def draw; end
def close; end
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

require 'main'