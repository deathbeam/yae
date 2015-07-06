import java from non
Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

class File
  new: (filename, filetype="internal") =>
    switch filetype
      when "internal"
        @file = Gdx.files\internal filename
      when "local"
        @file = NonVM.util\localfile filename
      when "external"
        @file = Gdx.files\external
      when "classpath"
        @file = Gdx.files\classpath
      when "absolute"
        @file = Gdx.files\absolute

  append: (text) =>
    @file\writeString text, true
    return true

  copy: (to_file) =>
    @file\copyTo to_file
    return true

  createDirectory: () =>
    @file\mkdirs!
    return true

  exists: () =>
    return @file\exists!

  getDirectoryItems: () =>
    children = @file\list!
    paths = {}

    for i = 0, children.length
      paths[i + 1] = children[i]\path!

    return paths

  getLastModified: () =>
    return @file\lastModified!

  getSize: () =>
    return @file\length!

  isDirectory: () =>
    return @file\isDirectory!

  isFile: () =>
    return not @is_directory!

  move: (to_file) =>
    @file\moveTo to_file
    return true

  read: () =>
    return @file\readString!

  remove: () =>
    @file\deleteDirectory!
    return true

  write: (text) =>
    @file\writeString text
    return true

return File