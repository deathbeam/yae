def draw
  # Tint text with red
  NON.graphics.set_color 255, 0, 0
  
  # Draw the text on specified coordinates
  NON.graphics.print "What hath deathbeam wrought?", x: @x, y: @y
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