-- Import non.graphics and non.system for easier usage
import graphics from non
import system from non

-- Pre-declaring x and y allows to use them in draw callback
-- and to modify them in resize callback
local x, y

non.draw = ->
  -- Draw the text on specified coordinates
  graphics.print "What hath deathbeam wrought?", x, y

non.resize = (w, h) ->
  -- Calculate center of the window for text position
  x = (w - 165) / 2
  y = (h - 12) / 2

non.keypressed = (key) ->
  -- Quit application if Q or Escape key was pressed
  if key == "q" or key == "escape"
    system.quit!