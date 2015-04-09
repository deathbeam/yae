package non.luan.module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;
import non.luan.obj.LuanObjFont;
import non.luan.obj.LuanObjImage;
import non.luan.obj.LuanObjQuad;

public class LuanGraphics extends LuanBase {
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Transform transform;
    private LuanObjFont font;
    private ShaderProgram shader;
    private String blendMode;
    private Color backgroundColor, color;

    public LuanGraphics(NonVM vm) {
        super(vm, "NonGraphics", true);

        font = new LuanObjFont(this);
        shader = SpriteBatch.createDefaultShader();
        batch = new SpriteBatch(1000, shader);
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
        transform = new Transform();
        color = new Color(1, 1, 1, 1);
        backgroundColor = new Color(0.4f, 0.3f, 0.4f, 1);
        blendMode = "alpha";
        shapes.setColor(color);
        batch.setColor(color);
        font.getFont().setColor(color);

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(true);
        batch.setProjectionMatrix(cam.combined);
        shapes.setProjectionMatrix(cam.combined);
    }

    public void dispose() {
        if (isDisposed()) return;
        batch.dispose();
        shapes.dispose();
        font.dispose();
        shader.dispose();
    }

    @Override
    public void init() {
        // non.graphics.arc(mode, x, y, radius, angle1, angle2)
        set("arc", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                changeMode(getArgString(args, 1));
                shapes.arc(getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4), getArgFloat(args, 5), getArgFloat(args, 6) * MathUtils.radDeg);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.circle(mode, x, y, radius)
        set("circle", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                changeMode(getArgString(args, 1));
                shapes.circle(getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.clear()
        set("clear", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            return NONE;
        }});

        // non.graphics.draw(drawable, quad, x, y, r, sx, sy, ox, oy, kx, ky)
        set("draw", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkBatch();
                final LuanObjImage image = (LuanObjImage)getArgData(args, 1);

                if (args.istable(2)) {
                    final LuanObjQuad quad = (LuanObjQuad)getArgData(args, 2);
                    batch.draw(
                        image.getTexture(),
                        getArgFloat(args, 3, 0f),
                        getArgFloat(args, 4, 0f),
                        getArgFloat(args, 8, 0f),
                        getArgFloat(args, 9, 0f),
                        quad.width,
                        quad.height,
                        getArgFloat(args, 6, 1f),
                        getArgFloat(args, 7, 1f),
                        getArgFloat(args, 5, 0f),
                        (int)quad.x,
                        (int)quad.y,
                        (int)quad.sw,
                        (int)quad.sh,
                        false, true);
                } else {
                    batch.draw(
                        image.getTexture(),
                        getArgFloat(args, 2, 0f),
                        getArgFloat(args, 3, 0f),
                        getArgFloat(args, 7, 0f),
                        getArgFloat(args, 8, 0f),
                        image.getTexture().getWidth(),
                        image.getTexture().getHeight(),
                        getArgFloat(args, 5, 1f),
                        getArgFloat(args, 6, 1f),
                        getArgFloat(args, 4, 0f),
                        0,
                        0,
                        (int)image.getTexture().getWidth(),
                        (int)image.getTexture().getHeight(),
                        false, true);
                }
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.ellipse(mode, x, y, width, height)
        set("ellipse", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                changeMode(getArgString(args, 1));
                shapes.ellipse(getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4), getArgFloat(args, 5));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // r, g, b, a = non.graphics.getBackgroundColor()
        set("getBackgroundColor", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(new LuaValue[] {
                valueOf(backgroundColor.r * 255),
                valueOf(backgroundColor.g * 255),
                valueOf(backgroundColor.b * 255),
                valueOf(backgroundColor.a * 255)});
        }});

        // mode = non.graphics.getBlendMode()
        set("getBlendMode", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(blendMode);
        }});

        // r, g, b, a = non.graphics.getColor()
        set("getColor", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(new LuaValue[] {
                valueOf(color.r * 255),
                valueOf(color.g * 255),
                valueOf(color.b * 255),
                valueOf(color.a * 255)});
        }});

        // font = non.graphics.getFont()
        set("getFont", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return font;
        }});

        // non.graphics.line(x1, y1, x2, y2)
        set("line", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                shapes.line(getArgFloat(args, 1), getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // font = non.graphics.newFont(filename, size, filetype)
        set("newFont", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                FileHandle file = LuanFilesystem.newFile(getArgString(args, 1), getArgString(args, 3, "internal"));
                return new LuanObjFont(LuanGraphics.this, file, getArgInt(args, 2, 12));
            } catch (Exception e) {
                handleError(e);
                return NONE;
            }
        }});

        // image = non.graphics.newImage(filename, format, filetype)
        set("newImage", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                FileHandle file = LuanFilesystem.newFile(getArgString(args, 1), getArgString(args, 3, "internal"));
                return new LuanObjImage(LuanGraphics.this, file, getArgString(args, 2, "normal"));
            } catch (Exception e) {
                handleError(e);
                return NONE;
            }
        }});

        // quad = non.graphics.newQuad(x, y, width, height, sw, sh)
        set("newQuad", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                return new LuanObjQuad(LuanGraphics.this,
                    getArgFloat(args, 1),
                    getArgFloat(args, 2),
                    getArgFloat(args, 3),
                    getArgFloat(args, 4),
                    getArgFloat(args, 5),
                    getArgFloat(args, 6));
            } catch (Exception e) {
                handleError(e);
                return NONE;
            }
        }});

        // non.graphics.origin()
        set("origin", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            transform.identity();
            updateMatrices();
            return NONE;
        }});

        // non.graphics.point(mode, x, y)
        set("point", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                shapes.point(getArgFloat(args, 1), getArgFloat(args, 2), 0);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.polygon(mode, x1, y1, x2, y2, ...)
        set("polygon", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                changeMode(getArgString(args, 1));
                float[] vertices = new float[0];
                int start = 2;

                if (args.istable(2)) {
                    args = getArgData(args, 2).unpack();
                    start = 1;
                }

                for (int i = start; i <= args.narg(); i++) {
                    vertices = Arrays.copyOf(vertices, vertices.length + 1);
                    vertices[vertices.length - 1] = getArgFloat(args, i);
                }

                shapes.polygon(vertices);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.present()
        set("present", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            if (shapes.isDrawing()) shapes.end();
            if (batch.isDrawing()) batch.end();
            return NONE;
        }});

        // non.graphics.print(text, x, y, r, sx, sy, ox, oy, kx, ky)
        set("print", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkBatch();
                final String text = getArgString(args, 1);
                final float x = getArgFloat(args, 2, 0f);
                final float y = getArgFloat(args, 3, 0f);

                if (isArgSet(args, 4)) {
                    final float r = getArgFloat(args, 4, 0f);
                    final float ox = getArgFloat(args, 7, 0f);
                    final float oy = getArgFloat(args, 8, 0f);
                    final float sx = getArgFloat(args, 5, 1f);
                    final float sy = getArgFloat(args, 6, 1f);
                    final Matrix4 tmp = batch.getTransformMatrix();

                    batch.setTransformMatrix(transform.translate(x, y).rotate(r).translate(-x, -y).matrix);
                    font.getFont().setScale(sx, -sy);
                    font.getFont().draw(batch, text, x - ox * sx, y - oy * sy);
                    batch.setTransformMatrix(tmp);
                } else {
                    font.getFont().setScale(1, -1);
                    font.getFont().draw(batch, text, x, y);
                }
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.printf(text, x, y, wrap, align, r, sx, sy, ox, oy, kx, ky)
        set("printf", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkBatch();
                final String text = getArgString(args, 1);
                final float x = getArgFloat(args, 2, 0f);
                final float y = getArgFloat(args, 3, 0f);

                if (isArgSet(args, 6)) {
                    final float r = getArgFloat(args, 6, 0f);
                    final float sx = getArgFloat(args, 7, 1f);
                    final float sy = getArgFloat(args, 8, 1f);
                    final float ox = getArgFloat(args, 9, 0f);
                    final float oy = getArgFloat(args, 10, 0f);
                    final Matrix4 tmp = batch.getTransformMatrix();

                    batch.setTransformMatrix(transform.translate(x, y).rotate(r).translate(-x, -y).matrix);
                    font.getFont().setScale(sx, -sy);
                    font.getFont().draw(batch, text, x - ox * sx, y - oy * sy);
                    batch.setTransformMatrix(tmp);
                } else if (isArgSet(args, 4)) {
                    font.getFont().setScale(1, -1);
                    font.getFont().drawWrapped(batch, text, x, y, getArgFloat(args, 4), getAlign(getArgString(args, 5, "left")));
                }
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.rectangle(mode, x, y, width, height)
        set("rectangle", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                checkShapes();
                changeMode(getArgString(args, 1));
                shapes.rect(getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4), getArgFloat(args, 5));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.reset()
        set("reset", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            shader = SpriteBatch.createDefaultShader();
            batch.setShader(shader);

            backgroundColor.r = 0.4f;
            backgroundColor.g = 0.3f;
            backgroundColor.b = 0.4f;
            backgroundColor.a = 1;
            
            color.r = 1;
            color.g = 1;
            color.b = 1;
            color.a = 1;
            
            shapes.setColor(color);
            batch.setColor(color);
            font.getFont().setColor(color);
            
            transform.identity();
            updateMatrices();
            return NONE;
        }});

        // non.graphics.rotate(radians)
        set("rotate", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                transform.rotate(getArgFloat(args, 1));
                updateMatrices();
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.setBackgroundColor(r, g, b, a)
        set("setBackgroundColor", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                backgroundColor.r = getArgInt(args, 1) / 255f;
                backgroundColor.g = getArgInt(args, 2) / 255f;
                backgroundColor.b = getArgInt(args, 3) / 255f;
                backgroundColor.a = getArgInt(args, 4, 255) / 255f;
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.setBlendMode(mode)
        set("setBlendMode", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                blendMode = getArgString(args, 1);
                int[] mode = BlendMode.getOpenGLBlendMode(blendMode);
                batch.setBlendFunction(mode[0], mode[1]);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.setColor(r, g, b, a)
        set("setColor", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                color.r = getArgInt(args, 1) / 255f;
                color.g = getArgInt(args, 2) / 255f;
                color.b = getArgInt(args, 3) / 255f;
                color.a = getArgInt(args, 4, 255) / 255f;
                batch.setColor(color);
                shapes.setColor(color);
                font.getFont().setColor(color);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.setFont(font)
        set("setFont", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                font = (LuanObjFont)getArgData(args, 1);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.scale(sx, sy)
        set("scale", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                transform.scale(getArgFloat(args, 1), getArgFloat(args, 2));
                updateMatrices();
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // non.graphics.translate(tx, ty)
        set("translate", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                transform.translate(getArgFloat(args, 1), getArgFloat(args, 2));
                updateMatrices();
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});
    }

    private BitmapFont.HAlignment getAlign(String align) {
        if ("right".equals(align)) return HAlignment.RIGHT;
        else if ("center".equals(align)) return HAlignment.CENTER;
        return HAlignment.LEFT;
    }

    private void changeMode(String mode) {
        if (mode.equals("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equals("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
    }

    private void checkBatch() {
        if (shapes.isDrawing()) shapes.end();
        if (!batch.isDrawing()) batch.begin();
    }
    
    private void checkShapes() {
        if (batch.isDrawing()) batch.end();
        if (!shapes.isDrawing()) shapes.begin();
    }
    
    private void updateMatrices() {
        shapes.setTransformMatrix(transform.matrix);
        batch.setTransformMatrix(transform.matrix);
    }

    public class Transform {
        public final Matrix4 matrix = new Matrix4();

        public Transform identity() {
            matrix.idt();
            return this;
        }

        public Transform rotate(float radians) {
            matrix.rotate(0f, 0f, 1f, radians * MathUtils.radDeg);
            return this;
        }

        public Transform scale(float x, float y) {
            matrix.scale(x, y, 1f);
            return this;
        }

        public Transform translate(float x, float y) {
            matrix.translate(x, y, 0f);
            return this;
        }
    }

    public static class BlendMode {
        private static Map<String, int[]> blendMap;

        public static int[] getOpenGLBlendMode(String blendmode) {
            if (!blendMap.containsKey(blendmode)) return blendMap.get("alpha");
            return blendMap.get(blendmode);
        }

        public static void addBlendMode(String name, int sFactor, int dFactor) {
            blendMap.put(name, new int[]{sFactor, dFactor});
        }

        static {
            blendMap = new HashMap<String, int[]>();
            
            addBlendMode("alpha", GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA);
            addBlendMode("multiplicative", GL20.GL_DST_COLOR , GL20.GL_ZERO);
            addBlendMode("premultiplied", GL20.GL_ONE , GL20. GL_ONE_MINUS_SRC_ALPHA);
            addBlendMode("subtractive", GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE);
            addBlendMode("additive", GL20.GL_SRC_ALPHA , GL20.GL_ONE);
            addBlendMode("screen", GL20.GL_ONE , GL20.GL_ONE_MINUS_SRC_COLOR);
            addBlendMode("replace", GL20.GL_ONE , GL20.GL_ZERO);
        }
    }
}