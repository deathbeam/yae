def file(path)
    java_import 'com.badlogic.gdx.Gdx'
    Gdx.files.internal(path)
end

# Core events
def init(assets); end
def ready; end
def render(dt); end
def close; end
def pause; end
def resume; end
def resize(w, h); end

# Input events
def keydown(key); end
def keyup(key); end
def keytyped(character); end
def touchdown(x, y, pointer, button); end
def touchup(x, y, pointer, button); end
def touchdragged(x, y, pointer); end
def mousemoved(x, y); end
def mousescrolled(amount); end

# Network events
def connected(connection); end
def disconnected(connection); end
def received(connection, data); end

require 'main'