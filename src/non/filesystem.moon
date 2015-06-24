Gdx = java.require "com.badlogic.gdx.Gdx"
NonVM = java.require "non.NonVM"

{
  new_file: (filename, filetype="internal") ->
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
    file = @new_file filename, filetype
    file\writeString text, true
    return true

  copy: (from_filename, to_filename, from_filetype, to_filetype) ->
    from_file = @new_file from_filename, from_filetype
    to_file = @new_file from_filename, from_filetype
    from_file\copyTo to_file
    return true

  mkdir: (filename, filetype) ->
    file = @new_file filename, filetype
    @new_file\mkdirs!
    return true

  exists: (filename, filetype) ->
    file = @new_file filename, filetype
    return @new_file\exists!

  list: (filename, filetype) ->
    children = @new_file(filename, filetype)\list!
    paths = {}

    for i = 0, children.length
      paths[i + 1] = children[i]\path!

    return paths

  get_external_path: ->
    return Gdx.files\getExternalStoragePath!

  get_local_path: ->
    return Gdx.files\getLocalStoragePath!

  get_working_path: ->
    file = @new_file(".")\file!
    return file\getAbsolutePath!

  last_modified: (filename, filetype) ->
    file = @new_file filename, filetype
    return file\lastModified!

  size: (filename, filetype) ->
    file = @new_file filename, filetype
    return file\length!

  is_directory: (filename, filetype) ->
    file = @new_file filename, filetype
    return file\isDirectory!

  is_file: (filename, filetype) ->
    return not @is_directory filename, filetype

  load: (filename, filetype) ->
    file = @new_file filename, filetype
    return NonVM.lua\load file\reader!, filename

  move: (from_filename, to_filename, from_filetype, to_filetype) ->
    from_file = @new_file from_filename, from_filetype
    to_file = @new_file from_filename, from_filetype
    from_file\moveTo to_file
    return true

  read: (filename, filetype) ->
    file = @new_file filename, filetype
    return file\readString!

  remove: (filename, filetype) ->
    file = @new_file filename, filetype
    file\deleteDirectory!
    return true

  write: (filename, text, filetype) ->
    file = @new_file filename, filetype
    file\writeString text
    return true
}