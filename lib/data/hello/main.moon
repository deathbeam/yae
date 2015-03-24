export x, y

non.draw = ->
  -- Tint text with red
  non.graphics.setColor 255, 0, 0
  
  -- Draw the text on specified coordinates
  non.graphics.print "What hath deathbeam wrought?", x: x, y: y

non.resize = (w, h) ->
  -- Calculate center of the window for text position
  x = (non.width() - 165) / 2
  y = (non.height() - 12) / 2

non.keydown = (key) ->
  -- Quit application if Q or Escape key was pressed
  if key == "Q" or key == "Escape"
    non.quit!