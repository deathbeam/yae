require "non/graphics"

def render(dt)
    Graphics.clear(0, 0, 0)
    Graphics.print(text: "Hello World!", position: [10, 10])
end