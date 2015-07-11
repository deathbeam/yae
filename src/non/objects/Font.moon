-------------------------------------------------------------------------------
-- Defines the shape of characters that can be drawn onto the screen.
-------------------------------------------------------------------------------
-- @classmod non.Font

import java, File from non
FreeTypeFontGenerator = java.require "com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator"
GlyphLayout = java.require "com.badlogic.gdx.graphics.g2d.GlyphLayout"
Constants = require "non.constants"

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
    @fontTexture\setFilter Constants.filters["linear"], Constants.filters["linear"]
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
    Constants.filtercodes[min_filter], Constants.filtercodes[mag_filter]

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
    @fontTexture\setFilter Constants.filters[min], Constants.filters[mag]

  setLineHeight: (height) =>
    @font\getData()\setLineHeight height

  free: =>
    @font\dispose!

return Font