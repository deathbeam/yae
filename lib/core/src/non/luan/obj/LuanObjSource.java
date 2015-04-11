package non.luan.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanObjSource extends LuanBase {
    private Music music;
    private Sound sound;
    private long soundID;
    private boolean isMusic;
    private float volume = 1.0f;
    private boolean isLooping, isPaused, isStopped;

    public LuanObjSource(LuanBase base, FileHandle file, String type) { 
        super(base.vm, "NonSource", true);
        isMusic = "stream".equals(type);

        if (isMusic) {
            music = Gdx.audio.newMusic(file);
        } else {
            sound = Gdx.audio.newSound(file);
        }
    }

    public void play() {
        if (isMusic) music.play();
        else soundID = sound.play();
        isStopped = false;
    }

    public void pause() {
        if (isMusic) music.pause();
        else sound.pause(soundID);
        isPaused = true;
    }

    public void resume() {
        if (isMusic && isPaused) {
            music.play();
            isPaused = false;
        } else if (isPaused) {
            sound.play(soundID);
            isPaused = false;
        }
    }

    public void stop() {
        if (isMusic) music.stop();
        else sound.stop(soundID);
        isStopped = true;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isStatic() {
        return !isMusic;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setLooping (boolean isLooping) {
        if (isMusic) music.setLooping(isLooping);
        else sound.setLooping(soundID, isLooping);
        this.isLooping = isLooping;
    }

    public void setVolume(float volume) {
        if (isMusic) music.setVolume(volume);
        else sound.setVolume(soundID, volume);
        this.volume = volume;
    }

    public void dispose() {
        if (isDisposed()) return;
        if (isMusic) music.dispose();
        else sound.dispose();
    }

    public void init() {
        // volume = Source:getVolume()
        set("getVolume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(getVolume());
        }});

        // is_looping = Source:isLooping()
        set("isLooping", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(isLooping());
        }});

        // is_paused = Source:isPaused()
        set("isPaused", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(isPaused());
        }});

        // is_static = Source:isStatic()
        set("isStatic", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(isStatic());
        }});

        // is_stopped = Source:isStopped()
        set("isStopped", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(isStopped());
        }});

        // Source:pause()
        set("pause", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            pause();
            return LuaValue.NONE;
        }});

        // Source:play()
        set("play", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            play();
            return LuaValue.NONE;
        }});

        // Source:resume()
        set("resume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            resume();
            return LuaValue.NONE;
        }});

        // Source:setLooping(is_looping)
        set("setLooping", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                setLooping(getArgBoolean(args, 2));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return LuaValue.NONE;
            }
        }});

        // Source:setVolume(volume)
        set("setVolume", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                setVolume(getArgFloat(args, 2));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return LuaValue.NONE;
            }
        }});

        // Source:stop()
        set("stop", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            stop();
            return LuaValue.NONE;
        }});

        set("type", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf("Source");
        }});

        set("typeOf", new VarArgFunction() { @Override public Varargs invoke(Varargs args) { 
            try {
                String s = getArgString(args, 2); 
                return valueOf(s.equals("Object") || s.equals("Source"));
            } catch (Exception e) {
                handleError(e);
                return LuaValue.FALSE;
            }
        }});
    }
}