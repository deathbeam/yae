-- Use with for easier usage and prettier code
with non
  .draw = ->
    -- Draw the text on specified coordinates
    .graphics.print "This is #{.project.name}", 10, 10

  .keypressed = (key) ->
    -- Quit application if Q or Escape key was pressed
    if key == "q" or key == "escape"
      .system.quit!
