c = require "non.internal.constants"

class File
  new: (filename, filetype="internal") =>
    @file = c.filetypes[filetype] filename

  append: (text) =>
    @file\writeString text, true
    return true

  copy: (to_file) =>
    @file\copyTo to_file
    return true

  create_directory: () =>
    @file\mkdirs!
    return true

  exists: () =>
    return @file\exists!

  list: () =>
    children = @file\list!
    paths = {}

    for i = 0, children.length
      paths[i + 1] = children[i]\path!

    return paths

  last_modified: () =>
    return @file\lastModified!

  size: () =>
    return @file\length!

  is_directory: () =>
    return @file\isDirectory!

  is_file: () =>
    return not @is_directory!

  move: (to_file) =>
    @file\moveTo to_file
    return true

  read: () =>
    return @file\readString!

  remove: () =>
    @file\deleteDirectory!
    return true

  write: () =>
    @file\writeString text
    return true

return File