-------------------------------------------------------------------------------
-- A quadrilateral (a polygon with four sides and four corners) with texture
-- coordinate information.
-------------------------------------------------------------------------------
-- @classmod yae.Quad

class
  new: (x, y, w, h, sw, sh) =>
    @x = x
    @y = y
    @w = w
    @h = h
    @sw = sw
    @sh = sh

  ---
  -- Gets the current viewport of this Quad.
  -- @tparam Quad self
  -- @treturn number x The top-left corner along the x-axis.
  -- @treturn number y The top-right corner along the y-axis.
  -- @treturn number w The width of the viewport.
  -- @treturn number h The height of the viewport.
  -- @usage
  -- x, y, width, height = Quad\getViewport!
  getViewport: =>
    @x, @y, @w, @h

  ---
  -- Sets the texture coordinates according to a viewport.
  -- @tparam Quad self
  -- @number x The top-left corner along the x-axis.
  -- @number y The top-right corner along the y-axis.
  -- @number w The width of the viewport.
  -- @number h The height of the viewport.
  -- @usage
  -- Quad\setViewport x, y, width, height
  setViewport: (x, y, w, h) =>
    @x = x
    @y = y
    @w = w
    @h = h
