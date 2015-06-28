class Quad
  new: (x, y, w, h, sw, sh) =>
    @x = x
    @y = y
    @w = w
    @h = h
    @sw = sw
    @sh = sh

  getViewport: =>
    @x, @y, @w, @h

  setViewport: (x, y, w, h) =>
    @x = x
    @y = y
    @w = w
    @h = h

return Quad