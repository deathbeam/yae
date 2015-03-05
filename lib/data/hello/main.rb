# Load the Graphics module
require "non/graphics"

# Rendering goes here
def render(dt)
    # Clear the screen with black color
    Graphics.clear(0, 0, 0)
    
    # Draw the "Hello World" text on x: 10 and y: 10
    Graphics.print("Hello World!", position: [10, 10])
end