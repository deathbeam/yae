export x, y

non.draw = ->
  -- Draw the text on specified coordinates
  non.graphics.print "What hath deathbeam wrought?", x, y

non.resize = (w, h) ->
  -- Calculate center of the window for text position
  x = (w - 165) / 2
  y = (h - 12) / 2

non.keypressed = (key) ->
  -- Quit application if Q or Escape key was pressed
  if key == "q" or key == "escape"
    non.system.quit!