function draw()
  -- Tint text with red
  NON.graphics.setColor(255, 0, 0)
  
  -- Draw the text on specified coordinates
  NON.graphics.print("What hath deathbeam wrought?", { x = x, y = y })
end

function resize(w, h)
  -- Calculate center of the window for text position
  x = (NON.width() - 165) / 2
  y = (NON.height() - 12) / 2
end

function keydown(key)
  -- Quit application if Q or Escape key was pressed
  if key == "Q" or key == "Escape" then
    NON.quit()
  end
end