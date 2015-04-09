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

    public LuanObjFont(LuanBase base, BitmapFont font) {
        super(base.vm, "NonFont", true);
        this.font = font;
    }

    public LuanObjFont(LuanBase base) {
        this(base, new BitmapFont());
    }

    public LuanObjFont(LuanBase base, FileHandle file, int size) { 
        this(base, new FreeTypeFontGenerator(file).generateFont(size));
    }

    public BitmapFont getFont() {
        return font;
    }

    public void dispose() {
        if (isDisposed()) return;
        font.dispose();
    }

    public void init() {
        // ascent = Font:getAscent()
        set("getAscent", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(getFont().getAscent());
        }});

        // width, height = Font:getBounds(text, wrap)
        set("getBounds", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                if (isArgSet(args, 3)) {
                    TextBounds bounds = getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3));
                    return LuaValue.varargsOf(valueOf(bounds.width), valueOf(bounds.height));
                }

                TextBounds bounds = getFont().getMultiLineBounds(getArgString(args, 2));
                return LuaValue.varargsOf(valueOf(bounds.width), valueOf(bounds.height));
            } catch (Exception e) {
                handleError(e);
                return varargsOf(ZERO, ZERO);
            }
        }});

        // descent = Font:getDescent()
        set("getDescent", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(getFont().getDescent());
        }});

        // min, mag = Font:getFilter()
        set("getFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return LuaValue.varargsOf(
                valueOf(LuanObjImage.getFilter(getFont().getRegion(0).getTexture().getMinFilter())),
                valueOf(LuanObjImage.getFilter(getFont().getRegion(0).getTexture().getMagFilter())));
        }});

        // height = Font:getHeight(text, wrap)
        set("getHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                if (isArgSet(args, 3)) {
                    return valueOf(getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3)).height);
                }

                return valueOf(getFont().getMultiLineBounds(getArgString(args, 2)).height);
            } catch (Exception e) {
                handleError(e);
                return ZERO;
            }
        }});

        // line_height = Font:getLineHeight()
        set("getLineHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(getFont().getLineHeight());
        }});

        // width = Font:getWidth(text, wrap)
        set("getWidth", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                if (isArgSet(args, 3)) {
                    return valueOf(getFont().getWrappedBounds(getArgString(args, 2), getArgFloat(args, 3)).width);
                }
                
                return valueOf(getFont().getMultiLineBounds(getArgString(args, 2)).width);
            } catch (Exception e) {
                handleError(e);
                return ZERO;
            }
        }});

        // hasglyphs = Font:hasGlyphs(glyph1, glyph2, ...)
        set("hasGlyphs", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            try {
                boolean hasGlyph = true;

                for (int i = 2; i <= args.narg(); i++) {
                    hasGlyph &= getFont().containsCharacter(getArgString(args, i).charAt(0));
                }

                return valueOf(hasGlyph);
            } catch (Exception e) {
                handleError(e);
                return FALSE;
            }
        }});

        // Font:setFilter(min, mag)
        set("setFilter", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                getFont().getRegion(0).getTexture().setFilter(
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
            try {
                getFont().getData().setLineHeight(getArgFloat(args, 2));
                return LuaValue.NONE;
            } catch (Exception e) {
                handleError(e);
            } finally {
                return LuaValue.NONE;
            }
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