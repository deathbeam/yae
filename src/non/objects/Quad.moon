class Quad
  new: (x, y, w, h, sw, sh) =>
    @x = x
    @y = y
    @w = w
    @h = h
    @sw = sw
    @sh = sh

  get_viewport: =>
    @x, @y, @w, @h

  set_viewport: (x, y, w, h) =>
    @x = x
    @y = y
    @w = w
    @h = h

return Quad