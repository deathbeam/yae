function non.draw()
  -- Tint text with purple
  non.graphics.setColor(193, 71, 193)
  
  -- Draw the text on specified coordinates
  non.graphics.print("What hath deathbeam wrought?", { x = x, y = y })
end

function non.resize(w, h)
  -- Calculate center of the window for text position
  x = (w - 165) / 2
  y = (h - 12) / 2
end

function non.keydown(key)
  -- Quit application if Q or Escape key was pressed
  if key == "Q" or key == "Escape" then
    non.system.quit()
  end
end