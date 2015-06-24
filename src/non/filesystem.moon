Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  newFile: (filename, filetype="internal") ->
    switch filename
      when "internal"
        return Gdx.files\internal filename
      when "local"
        return NonVM.util\localfile filename
      when "external"
        return Gdx.files\external filename
      when "classpath"
        return Gdx.files\classpath filename
      when "absolute"
        return Gdx.files\absolute filename

  append: (filename, text, filetype) ->
    file = @newFile filename, filetype
    file\writeString text, true
    return true

  copy: (from_filename, to_filename, from_filetype, to_filetype) ->
    from_file = @newFile from_filename, from_filetype
    to_file = @newFile from_filename, from_filetype
    from_file\copyTo to_file
    return true

  createDirectory: (filename, filetype) ->
    file = @newFile filename, filetype
    @newFile\mkdirs!
    return true

  exists: (filename, filetype) ->
    file = @newFile filename, filetype
    return @newFile\exists!

  getDirectoryItems: (filename, filetype) ->
    children = @newFile(filename, filetype)\list!
    paths = {}

    for i = 0, children.length
      paths[i + 1] = children[i]\path!

    return paths

  getExternalDirectory: ->
    return Gdx.files\getExternalStoragePath!

  getLocalDirectory: ->
    return Gdx.files\getLocalStoragePath!

  getWorkingDirectory: ->
    file = @newFile(".")\file!
    return file\getAbsolutePath!

  getLastModified: (filename, filetype) ->
    file = @newFile filename, filetype
    return file\lastModified!

  getSize: (filename, filetype) ->
    file = @newFile filename, filetype
    return file\length!

  isDirectory: (filename, filetype) ->
    file = @newFile filename, filetype
    return file\isDirectory!

  isFile: (filename, filetype) ->
    return not @isDirectory filename, filetype

  load: (filename, filetype) ->
    file = @newFile filename, filetype
    return NonVM.lua\load file\reader!, filename

  move: (from_filename, to_filename, from_filetype, to_filetype) ->
    from_file = @newFile from_filename, from_filetype
    to_file = @newFile from_filename, from_filetype
    from_file\moveTo to_file
    return true

  read: (filename, filetype) ->
    file = @newFile filename, filetype
    return file\readString!

  remove: (filename, filetype) ->
    file = @newFile filename, filetype
    file\deleteDirectory!
    return true

  write: (filename, text, filetype) ->
    file = @newFile filename, filetype
    file\writeString text
    return true
}