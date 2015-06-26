File = require "non.objects.File"
Texture = java.require "com.badlogic.gdx.graphics.Texture"

c = require "non.internal.constants"

class Image
	new: (filename, format="rgba8", filetype) =>
		file = File filename, filetype
		@texture = java.new Texture, file.file, c.formats[format], false
		@texture\setFilter c.filters["linear"], c.filters["linear"]

	get_dimensions: =>
		@get_width!, @get_height!

	get_filter: =>
		min_filter = @texture\getMinFilter!
		mag_filter = @texture\getMagFilter!
		c.filtercodes[min_filter], c.filtercodes[mag_filter]

	get_format: =>
		texture_data = @texture\getTextureData!
		format = texture_data\getFormat!
		c.formatcodes[format]

	get_height: =>
		@texture\getHeight!

	get_width: =>
		@texture\getWidth!

	get_wrap: =>
		c.wraps[@texture\getUWrap!], c.wraps[@texture\getVWrap!]

	set_filter: (min, mag) =>
		@texture\setFilter c.filters[min], c.filters[mag]

	set_wrap: (horiz, vert) =>
		@texture\setWrap c.wraps[horiz], c.wraps[vert]

	free: =>
		@texture\dispose!

return Image