package non.luan.module;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;
import non.luan.obj.LuanObjFont;
import non.luan.obj.LuanObjImage;

public class LuanGraphics extends LuanBase {
	private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Transform transform;
    private LuanObjFont font;
    private ShaderProgram shader;
    private float rotation, scalex, scaley, translatex, translatey;
    private String blendMode;
    private Color backgroundColor, color;

	public LuanGraphics(NonVM vm) {
		super(vm, "NonGraphics");

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
	}

	public void dispose() {
        batch.dispose();
        shapes.dispose();
        font.dispose();
        shader.dispose();
	}

	@Override
	public void init() {
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

		// non.graphics.line(mode, x1, y1, x2, y2)
		set("line", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				checkShapes();
		        changeMode(getArgString(args, 1));
		        shapes.rectLine(getArgFloat(args, 2), getArgFloat(args, 3), getArgFloat(args, 4), getArgFloat(args, 5), 1);
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.graphics.origin()
		set("origin", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			scalex = 1;
			scaley = 1;
	        translatex = 0;
	        translatey = 0;
	        rotation = 0;
	        
	        transform.reset();
	        updateMatrices();
        	return NONE;
		}});

		// non.graphics.point(mode, x, y)
		set("point", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				checkShapes();
		        changeMode(getArgString(args, 1));
		        shapes.point(getArgFloat(args, 2), getArgFloat(args, 3), 0);
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

		// non.graphics.present()
		set("reset", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			scalex = 1;
			scaley = 1;
	        translatex = 0;
	        translatey = 0;
	        rotation = 0;

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
	        
	        transform.reset();
	        updateMatrices();
        	return NONE;
		}});

		// non.graphics.rotate(radians)
		set("rotate", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				rotation = getArgFloat(args, 1);
				transform.rotate(rotation);
				transform.update();
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
				scalex = getArgFloat(args, 1);
				scaley = getArgFloat(args, 2);
				transform.scale(1 / scalex, 1 / scaley);
				transform.update();
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
				translatex = getArgFloat(args, 1);
				translatey = getArgFloat(args, 2);
				transform.translate(-translatex, -translatey);
				transform.update();
				updateMatrices();
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});
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
        shapes.setProjectionMatrix(transform.combined);
        batch.setProjectionMatrix(transform.combined);
    }

    public class Transform extends OrthographicCamera {
    	private final Vector2 scale = new Vector2(1, 1);
    	private final Vector3 tmp = new Vector3();

    	public Transform() {
    		super();
    	}

    	@Override
		public void update (boolean updateFrustum) {
			projection.setToOrtho(scale.x * -viewportWidth / 2, scale.x * (viewportWidth / 2), scale.y * -(viewportHeight / 2), scale.y
				* viewportHeight / 2, near, far);
			view.setToLookAt(position, tmp.set(position).add(direction), up);
			combined.set(projection);
			Matrix4.mul(combined.val, view.val);

			if (updateFrustum) {
				invProjectionView.set(combined);
				Matrix4.inv(invProjectionView.val);
				frustum.update(invProjectionView);
			}
		}

		public void reset() {
			scale.set(1, 1);
			setToOrtho(true);
		}

		@Override
		public void rotate(float radians) {
			super.rotate((float)Math.toDegrees(radians));
		}

		public void scale (float x, float y) {
			scale.set(x, y);
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