# Rendering goes here
def draw
  # Clear the screen with black color
  NON.graphics.clear 0, 0, 0
  
  # Tint text with red
  NON.graphics.set_color 1, 0, 0
  
  # Draw the text on specified coordinates
  NON.graphics.print "What hath Matz wrought?", position: [@x, @y]
end

def resize(w, h)
  # Calculate center of the window for text position
  @x = (NON.width - 165) / 2
  @y = (NON.height - 12) / 2
end

def keydown(key)
  # Quit application if Q or Escape key was pressed
  if key == "Q" or key == "Escape"
    NON.quit
  end
end