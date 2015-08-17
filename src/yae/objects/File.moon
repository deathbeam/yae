-------------------------------------------------------------------------------
-- Represents a file on the filesystem.
-------------------------------------------------------------------------------
-- @classmod yae.File

Gdx = yae.java.require "com.badlogic.gdx.Gdx"
YaeVM = yae.java.require "yae.YaeVM"

class
  new: (filename, filetype="internal") =>
    switch filetype
      when "internal"
        @file = Gdx.files\internal filename
      when "local"
        @file = YaeVM.util\localfile filename
      when "external"
        @file = Gdx.files\external
      when "classpath"
        @file = Gdx.files\classpath
      when "absolute"
        @file = Gdx.files\absolute

  ---
  -- Append data to an existing file.
  -- @tparam File self
  -- @string text The string data to append to the file.
  -- @usage
  -- File\append text
  append: (text) =>
    @file\writeString text, true

  ---
  -- Copy file.
  -- @tparam File self
  -- @tparam File to The destination file.
  -- @usage
  -- File\copy to
  copy: (to) =>
    @file\copyTo to

  createDirectory: =>
    @file\mkdirs!

  exists: =>
    @file\exists!

  getDirectoryItems: =>
    children = @file\list!
    paths = {}

    for i = 0, children.length
      paths[i + 1] = children[i]\path!

    paths

  getLastModified: =>
    @file\lastModified!

  getSize: =>
    @file\length!

  isDirectory: =>
    @file\isDirectory!

  isFile: =>
    not @is_directory!

  move: (to_file) =>
    @file\moveTo to_file

  read: =>
    @file\readString!

  remove: =>
    @file\deleteDirectory!

  write: (text) =>
    @file\writeString text
