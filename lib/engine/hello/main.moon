export x, y

non.draw = ->
  -- Tint text with purple
  non.graphics.setColor 193, 71, 193
  
  -- Draw the text on specified coordinates
  non.graphics.print "What hath deathbeam wrought?", x: x, y: y

non.resize = (w, h) ->
  -- Calculate center of the window for text position
  x = (non.graphics.getWidth! - 165) / 2
  y = (non.graphics.getHeight! - 12) / 2

non.keydown = (key) ->
  -- Quit application if Q or Escape key was pressed
  if key == "Q" or key == "Escape"
    non.system.quit!