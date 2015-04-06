package non.luan.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

public class LuanObjFont extends LuanBase {
	private BitmapFont font;

	public LuanObjFont(LuanBase base) {
		this(base, new BitmapFont());
	}

	public LuanObjFont(LuanBase base, FileHandle file, int size) { 
		this(base, new FreeTypeFontGenerator(file).generateFont(size));
	}

	public LuanObjFont(LuanBase base, BitmapFont font) {
		super(base.vm, "NonFont");
		this.font = font;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void dispose() {
		font.dispose();
	}

	private LuanObjFont self (Varargs args) {
		try {
			return (LuanObjFont)getArgData(args, 1);
		} catch (Exception e) {
			handleError(e);
			return null;
		}
	}

	public void init() {
		// ascent = Font:getAscent()
		set("getAscent", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(self(args).getFont().getAscent());
		}});

		// width, height = Font:getWidth(text, wrap)
		set("getBounds", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			if (isArgSet(args, 3)) {
				TextBounds bounds = self(args).getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3));
				return LuaValue.varargsOf(valueOf(bounds.width), valueOf(bounds.height));
			}

			TextBounds bounds = self(args).getFont().getMultiLineBounds(getArgString(args, 2));
			return LuaValue.varargsOf(valueOf(bounds.width), valueOf(bounds.height));
		}});

		// descent = Font:getDescent()
		set("getDescent", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(self(args).getFont().getDescent());
		}});

		// min, mag = Font:getFilter()
		set("getFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return LuaValue.varargsOf(
				valueOf(LuanObjImage.getFilter(self(args).getFont().getRegion(0).getTexture().getMinFilter())),
				valueOf(LuanObjImage.getFilter(self(args).getFont().getRegion(0).getTexture().getMagFilter())));
		}});

		// height = Font:getHeight(text, wrap)
		set("getHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			if (isArgSet(args, 3)) {
				return valueOf(self(args).getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3)).height);
			}

			return valueOf(self(args).getFont().getMultiLineBounds(getArgString(args, 2)).height);
		}});

		// line_height = Font:getLineHeight()
		set("getLineHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(self(args).getFont().getLineHeight());
		}});

		// width = Font:getWidth(text, wrap)
		set("getWidth", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			if (isArgSet(args, 3)) {
				return valueOf(self(args).getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3)).width);
			}
			
			return valueOf(self(args).getFont().getMultiLineBounds(getArgString(args, 2)).width);
		}});

		// hasglyphs = Font:hasGlyphs(glyph1, glyph2, ...)
		set("hasGlyphs", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            boolean hasGlyph = true;

            for (int i = 2; i <= args.narg(); ++i) {
	            hasGlyph &= self(args).getFont().containsCharacter(getArgString(args, i).charAt(0));
	        }

			return valueOf(hasGlyph);
        }});

        // Font:setFilter(min, mag)
		set("setFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				self(args).getFont().getRegion(0).getTexture().setFilter(
					LuanObjImage.getFilter(getArgString(args, 2)),
					LuanObjImage.getFilter(getArgString(args, 3)));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return LuaValue.NONE;
			}
		}});

        // Font:setLineHeight(line_height)
		set("setLineHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			self(args).getFont().getData().setLineHeight(getArgFloat(args, 2));
			return LuaValue.NONE;
		}});

		set("type", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf("Font");
		}});

		set("typeOf", new VarArgFunction() { @Override public Varargs invoke(Varargs args) { 
			try {
				String s = getArgString(args, 2); 
				return valueOf(s.equals("Object") || s.equals("Font"));
			} catch (Exception e) {
				handleError(e);
				return LuaValue.FALSE;
			}
		}});
	}
}