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
    @fontTexture = @font\getRegion(0)\getTexture!
    @fontTexture\setFilter c.filters["linear"], c.filters["linear"]
    @glyphLayout = java.new GlyphLayout

  getAscent: =>
    @font\getAscent!

  getDescent: =>
    @font\getDescent!

  getLineHeight: =>
    @font\getLineHeight!

  getBounds: (text) =>
    @glyphLayout\setText text
    return @glyphLayout.width, @glyphLayout.height

  getFilter: =>
    min_filter = @fontTexture\getMinFilter!
    mag_filter = @fontTexture\getMagFilter!
    c.filtercodes[min_filter], c.filtercodes[mag_filter]

  getHeight: (text) =>
    w, _ = @getBounds text
    return w

  getWidth: (text) =>
    _, h = @getBounds text
    return h

  hasGlyphs: (...) =>
    args = table.pack ...
    found = true

    for i = 1, args.n
      found = found and @font\containsCharacter args[i]

    return found

  setFilter: (min, mag) =>
    @fontTexture\setFilter c.filters[min], c.filters[mag]

  setLineHeight: (height) =>
    @font\getData()\setLineHeight height

  free: =>
    @font\dispose!

return Font