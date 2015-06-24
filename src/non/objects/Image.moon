Texture = java.require "com.badlogic.gdx.graphics.Texture"

c = require "non.internal.constants"

class Image
	new: (filename, format, filetype) =>
		file = non.filesystem.newFile filename, filetype
		@texture = java.new Texture, file, c.formats[format], false
		texture\setFilter c.filters["linear"], c.filters["linear"]

	getDimensions: =>
		@getWidth!, @getHeight!

	getFilter: =>
		min_filter = @texture\getMinFilter!
		mag_filter = @texture\getMagFilter!
		c.filtercodes[min_filter], c.filtercodes[mag_filter]

	getFormat: =>
		texture_data = @texture\getTextureData!
		format = texture_data\getFormat!
		c.formatcodes[format]

	getHeight: =>
		@texture\getHeight!

	getWidth: =>
		@texture\getWidth!

	getWrap: =>
		c.wraps[@texture\getUWrap!], c.wraps[@texture\getVWrap!]

	setFilter: (min, mag) =>
		@texture\setFilter c.filters[min], c.filters[mag]

	setWrap: (horiz, vert) =>
		@texture\setWrap c.wraps[horiz], c.wraps[vert]

return Image