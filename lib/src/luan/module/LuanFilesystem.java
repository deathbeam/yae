package non.luan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanFilesystem extends LuanBase {
	public LuanFilesystem(NonVM vm) {
		super(vm, "NonFilesystem");
	}

	@Override
	public void init() {
		// success = non.filesystem.append(filename, text, filetype)
		set("append", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				newFile(args, 1, 3).writeString(getArgString(args, 2), true);
				return TRUE;
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// success = non.filesystem.copy(fromfilename, tofilename, fromfiletype, tofiletype)
		set("copy", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				newFile(args, 1, 3).copyTo(newFile(args, 2, 4));
				return TRUE;
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// success = non.filesystem.createDirectory(filename, filetype)
		set("createDirectory", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				newFile(args, 1, 2).mkdirs();
				return TRUE;
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// exists = non.filesystem.exists(filename, filetype)
		set("exists", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).exists());
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// items = non.filesystem.getDirectoryItems(filename, filetype)
		set("getDirectoryItems", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				FileHandle[] children = newFile(args, 1, 2).list();
				LuaTable t = tableOf();

				for (int i = 0; i < children.length; i++) {
					t.rawset(i + 1, children[i].path());
				}

				return t;
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// path = love.filesystem.getExternalDirectory()
		set("getExternalDirectory", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.files.getExternalStoragePath());
		}});

		// path = love.filesystem.getLocalDirectory()
		set("getLocalDirectory", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.files.getLocalStoragePath());
		}});

		// path = love.filesystem.getWorkingDirectory()
		set("getWorkingDirectory", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.files.internal(".").file().getAbsolutePath());
		}});

		// modtime = non.filesystem.getLastModified(filename, filetype)
		set("getLastModified", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).lastModified());
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// size = non.filesystem.getSize(filename, filetype)
		set("getSize", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).length());
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// is_dir = non.filesystem.isDirectory(filename, filetype)
		set("isDirectory", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).isDirectory());
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// is_file = non.filesystem.isFile(filename, filetype)
		set("isFile", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(!newFile(args, 1, 2).isDirectory());
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// chunk = non.filesystem.load(filename, filetype)
		set("load", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return vm.getLua().getContext().getGlobals().load(newFile(args, 1, 2).reader(), getArgString(args, 1));
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// success = non.filesystem.move(fromfilename, tofilename, fromfiletype, tofiletype)
		set("move", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				newFile(args, 1, 3).moveTo(newFile(args, 2, 4));
				return TRUE;
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// contents = non.filesystem.read(filename, filetype)
		set("read", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).readString());
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// success = non.filesystem.remove(filename, filetype)
		set("remove", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				return valueOf(newFile(args, 1, 2).deleteDirectory());
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// success = non.filesystem.write(filename, text, filetype)
		set("write", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				newFile(args, 1, 3).writeString(getArgString(args, 2), false);
				return TRUE;
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});
	}

	private FileHandle newFile(Varargs args, int filename, int filetype) {
		String name = getArgString(args, filename);
		String type = getArgString(args, filetype, "internal");
		return newFile(name, type);
	}

	public static FileHandle newFile(String filename, String filetype) {
        if ("internal".equals(filetype)) return Gdx.files.internal(filename);
        else if ("local".equals(filetype)) return Gdx.files.local(filename);
        else if ("external".equals(filetype)) return Gdx.files.external(filename);
        else if ("classpath".equals(filetype)) return Gdx.files.classpath(filename);
        else if ("absolute".equals(filetype)) return Gdx.files.absolute(filename);
        return null;
    }
}