package non.luan.module;

import java.util.Set;
import java.util.HashSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.LuaEngine;
import non.NonVM;
import non.luan.LuanBase;
import non.luan.obj.LuanObjSource;

public class LuanAudio extends LuanBase {
	private final Set sources = new HashSet<LuanObjSource>();
	private float volume = 1.0f;

	public LuanAudio(NonVM vm) {
		super(vm, "NonAudio");
	}

	@Override
	public void init() {
		// volume = non.audio.getVolume()
		set("getNumSources", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(sources.size());
		}});

		// volume = non.audio.getVolume()
		set("getVolume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(volume);
		}});

		// source = non.audio.newSource(filename, type, filetype)
		set("newSource", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				FileHandle file = LuanFilesystem.newFile(getArgString(args, 1), getArgString(args, 3, "internal"));
				String type = getArgString(args, 2, "static");
				LuanObjSource source = new LuanObjSource(LuanAudio.this, file, type);
				sources.add(source);
				return source;
			} catch (Exception e) {
				handleError(e);
				return NONE;
			}
		}});

		// non.audio.pause(source)
		set("pause", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				LuanObjSource source = (LuanObjSource)getArgData(args, 1);
				source.pause();
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.audio.play(source)
		set("play", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				LuanObjSource source = (LuanObjSource)getArgData(args, 1);
				source.setVolume(volume);
				source.play();
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.audio.resume(source)
		set("resume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				LuanObjSource source = (LuanObjSource)getArgData(args, 1);
				source.resume();
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.audio.setVolume(volume)
		set("setVolume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				volume = getArgFloat(args, (1));

				for (Object source : sources) {
					((LuanObjSource)source).setVolume(volume);
				}
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.audio.stop(source)
		set("stop", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				if (isArgSet(args, 1)) {
					LuanObjSource source = (LuanObjSource)getArgData(args, 1);
					source.stop();
				} else {
					for (Object source : sources) {
						((LuanObjSource)source).stop();
					}
				}
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});
	}

	public void dispose() {
		for (Object source : sources) {
			((LuanObjSource)source).stop();
		}

		sources.clear();
	}
}