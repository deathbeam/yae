package non.luan.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanObjImage extends LuanBase {
	private Texture texture;

	public LuanObjImage(LuanBase base, FileHandle file, String format) { 
		super(base.vm, "NonImage");
		texture = new Texture(file, getFormat(format), false);
	}

	public Texture getTexture() {
		return texture;
	}

	public void dispose() {
		texture.dispose();
	}

	private LuanObjImage self (Varargs args) {
		try {
			return (LuanObjImage)getArgData(args, 1);
		} catch (Exception e) {
			handleError(e);
			return null;
		}
	}

	public void init() {
		// width, height = Image:getDimensions()
		set("getDimensions", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return LuaValue.varargsOf(
				valueOf(self(args).getTexture().getWidth()),
				valueOf(self(args).getTexture().getHeight()));
		}});

		// min, mag = Image:getFilter()
		set("getFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return LuaValue.varargsOf(
				valueOf(getFilter(self(args).getTexture().getMinFilter())),
				valueOf(getFilter(self(args).getTexture().getMagFilter())));
		}});

		// format = Image:getFormat()
		set("getFormat", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(getFormat(self(args).getTexture().getTextureData().getFormat()));
		}});

		// height = Image:getHeight()
		set("getHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(self(args).getTexture().getHeight());
		}});

		// width = Image:getWidth()
		set("getWidth", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(self(args).getTexture().getWidth());
		}});

		// horiz, vert = Image:getWrap()
		set("getWrap", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return LuaValue.varargsOf(
				valueOf(getWrap(self(args).getTexture().getUWrap())),
				valueOf(getWrap(self(args).getTexture().getVWrap())));
		}});

		// Image:setFilter(min, mag)
		set("setFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				self(args).getTexture().setFilter(
					getFilter(getArgString(args, 2)),
					getFilter(getArgString(args, 3)));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return LuaValue.NONE;
			}
		}});

		// Image:setWrap(horiz, vert)
		set("setWrap", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				self(args).getTexture().setWrap(
					getWrap(getArgString(args, 2)),
					getWrap(getArgString(args, 3)));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return LuaValue.NONE;
			}
		}});

		set("type", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf("Image");
		}});

		set("typeOf", new VarArgFunction() { @Override public Varargs invoke(Varargs args) { 
			try {
				String s = getArgString(args, 2); 
				return valueOf(s.equals("Object") || s.equals("Image"));
			} catch (Exception e) {
				handleError(e);
				return LuaValue.FALSE;
			}
		}});
	}

	public static String getWrap(TextureWrap wrap) {
		if (wrap.equals(TextureWrap.MirroredRepeat)) return "mirroredrepeat";
		else if (wrap.equals(TextureWrap.ClampToEdge)) return "clamp";
		else if (wrap.equals(TextureWrap.Repeat)) return "repeat";

		return "clamp";
	}

	public static TextureWrap getWrap(String wrap) {
		if (wrap.equals("mirroredrepeat")) return TextureWrap.MirroredRepeat;
		else if (wrap.equals("clamp")) return TextureWrap.ClampToEdge;
		else if (wrap.equals("repeat")) return TextureWrap.Repeat;

		return TextureWrap.ClampToEdge;
	}

	public static String getFormat(Format format) {
		if (format.equals(Format.RGBA8888)) return "rgba8";
		else if (format.equals(Format.RGB888)) return "rgb8";
		else if (format.equals(Format.RGBA4444)) return "rgba4";
		else if (format.equals(Format.RGB565)) return "rgb565";
		else if (format.equals(Format.Alpha)) return "alpha";
		else if (format.equals(Format.Intensity)) return "intensity";
		else if (format.equals(Format.LuminanceAlpha)) return "luminancealpha";

		return "normal";
	}

	public static Format getFormat(String format) {
		if ("normal".equals(format) || "rgba8".equals(format)) {
			return Format.RGBA8888;
		} else if ("rgb8".equals(format)) {
			return Format.RGB888;
		} else if ("rgba4".equals(format)) {
			return Format.RGBA4444;
		} else if ("rgb565".equals(format)) {
			return Format.RGB565;
		} else if ("alpha".equals(format)) {
			return Format.Alpha;
		} else if ("intensity".equals(format)) {
			return Format.Intensity;
		} else if ("luminancealpha".equals(format)) {
			return Format.LuminanceAlpha;
		}

		return Format.RGBA8888;
	}

	public static String getFilter(TextureFilter filter) {
		if (filter.equals(TextureFilter.Linear)) return "linear";
		else if (filter.equals(TextureFilter.Nearest)) return "nearest";
		else if (filter.equals(TextureFilter.MipMap)) return "mipmap";
		else if (filter.equals(TextureFilter.MipMapLinearLinear)) return "mipmaplinearlinear";
		else if (filter.equals(TextureFilter.MipMapNearestNearest)) return "mipmapnearestnearest";
		else if (filter.equals(TextureFilter.MipMapLinearNearest)) return "mipmaplinearnearest";
		else if (filter.equals(TextureFilter.MipMapNearestLinear)) return "mipmapnearestlinear";

		return "linear";
	}

	public static TextureFilter getFilter(String filter) {
		if ("linear".equals(filter)) {
			return TextureFilter.Linear;
		} else if ("nearest".equals(filter)) {
			return TextureFilter.Nearest;
		} else if ("mipmap".equals(filter)) {
			return TextureFilter.MipMap;
		} else if ("mipmaplinearlinear".equals(filter)) {
			return TextureFilter.MipMapLinearLinear;
		} else if ("mipmapnearestnearest".equals(filter)) {
			return TextureFilter.MipMapNearestNearest;
		} else if ("mipmaplinearnearest".equals(filter)) {
			return TextureFilter.MipMapLinearNearest;
		} else if ("mipmapnearestlinear".equals(filter)) {
			return TextureFilter.MipMapNearestLinear;
		}

		return TextureFilter.Linear;
	}
}