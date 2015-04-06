function non.draw()
  -- Tint text with purple
  non.graphics.setColor(193, 71, 193)
  
  -- Draw the text on specified coordinates
  non.graphics.print("What hath deathbeam wrought?", x, y)
end

function non.resize(w, h)
  -- Calculate center of the window for text position
  x = (w - 165) / 2
  y = (h - 12) / 2
end

function non.keypressed(key)
  -- Quit application if Q or Escape key was pressed
  if key == "q" or key == "escape" then
    non.system.quit()
  end
end