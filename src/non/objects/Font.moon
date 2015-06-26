File = require "non.objects.File"
FreeTypeFontGenerator = java.require "com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator"
GlyphLayout = java.require "com.badlogic.gdx.graphics.g2d.GlyphLayout"
c = require "non.internal.constants"

class Font
  new: (filename, size=16, filetype) =>
    file = nil

    if filename == nil
      file = File "non/font.ttf", filetype
    else
      file = File filename, filetype
    
    generator = java.new FreeTypeFontGenerator, file.file
    @font = generator\generateFont size
    @font_texture = @font\getRegion(0)\getTexture!
    @font_texture\setFilter c.filters["linear"], c.filters["linear"]
    @glyph_layout = java.new GlyphLayout

  get_ascent: =>
    @font\getAscent!

  get_descent: =>
    @font\getDescent!

  get_line_height: =>
    @font\getLineHeight!

  get_bounds: (text) =>
    @glyph_layout\setText text
    return @glyph_layout.width, @glyph_layout.height

  get_filter: =>
    min_filter = @font_texture\getMinFilter!
    mag_filter = @font_texture\getMagFilter!
    c.filtercodes[min_filter], c.filtercodes[mag_filter]

  get_height: (text) =>
    w, _ = @get_bounds text
    return w

  get_width: (text) =>
    _, h = @get_bounds text
    return h

  has_glyphs: (...) =>
    args = table.pack ...
    found = true

    for i = 1, args.n
      found = found and @font\containsCharacter args[i]

    return found

  set_filter: (min, mag) =>
    @font_texture\setFilter c.filters[min], c.filters[mag]

  set_line_height: (height) =>
    @font\getData()\setLineHeight height

  free: =>
    @font\dispose!

return Font