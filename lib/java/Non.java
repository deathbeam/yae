package non;

import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class Non implements ApplicationListener {
    private boolean ready;
    private SpriteBatch batch;
    private BitmapFont font;
    private Sprite background, logo, bar;
    private final Vector2 textPos = new Vector2();
    private int loadState;
    private String text = "";
    private final Map config;

    public Non(Map config) {
        this.config = config;
        
        if (config.containsKey("logging")) {
            String logLevel = (String)config.get("logging");
            if ("error".equalsIgnoreCase(logLevel)) Gdx.app.setLogLevel(1);
            else if ("info".equalsIgnoreCase(logLevel)) Gdx.app.setLogLevel(2);
            else if ("debug".equalsIgnoreCase(logLevel)) Gdx.app.setLogLevel(3);
            else Gdx.app.setLogLevel(0);
        }
    }
    
    public void create () {    
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        background = new Sprite(new Texture(Gdx.files.internal("non/background.png")));
        background.setOrigin(0, 0);
        logo = new Sprite(new Texture(Gdx.files.internal("non/logo.png")));
        bar = new Sprite(new Texture(Gdx.files.internal("non/bar.png")));
    }

    public void render () {
        if (ready) {
            JModule.update(Gdx.graphics.getDeltaTime());
            JModule.call("update", Gdx.graphics.getDeltaTime());
            JModule.call("draw");
            JModule.updateAfter(Gdx.graphics.getDeltaTime());
            return;
        }
        
        if (loadCore()) {
            if (Gdx.input.isTouched()) {
                disposeLoading();
                JModule.call("ready");
                ready = true;
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                return;
            }
        }
        
        if (batch == null) return;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        bar.draw(batch);
        font.draw(batch, text, textPos.x, textPos.y);
        batch.end();
    }

    public void resize(int width, int height) { 
        if (ready) {
            JModule.call("resize", width, height);
            return;
        }
        
        if (batch == null) return;
        
        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));

        float scale = (float)width / (float)background.getWidth();
        background.setY(height - (background.getHeight() * scale));
        background.setScale(scale);
        logo.setCenter(width/2, height/2);
        bar.setCenterX(width/2);
        bar.setY(logo.getY() - bar.getHeight());
    }
	
    public void pause() {
        JModule.call("pause");
    }
	
    public void resume() {
        JModule.call("resume");
    }
    
    public void dispose() { 
        if (ready) {
            JModule.call("quit");
            JModule.dispose();
        } else if (batch != null) {
            disposeLoading();
        }

        if (Gdx.app.getType() == ApplicationType.Android) System.exit(0);
    }
    
    private void updateText(String text) {
        this.text = text;
        textPos.set((Gdx.graphics.getWidth() - font.getBounds(text).width) /2,
                    bar.getY() + (bar.getHeight() + 12) /2);
    }
    
    private void disposeLoading() {
        batch.dispose();
        batch = null;
        font.dispose();
        font = null;
        background = null;
        logo = null;
        bar = null;
    }

    private boolean loadCore() {
        if (loadState > 2) return true;
        
        switch (loadState) {
        case 0: updateText("Starting the engine"); break;
        case 1: 
            JModule.init(config);
            updateText("Initializing the game");
            break;
        case 2: 
            JModule.eval(Gdx.files.internal("non/initializer.lua"));
            Gdx.input.setInputProcessor(new JInput());
            updateText("Touch screen to continue");
            break;
        }

        loadState++;
        return false;
    }
}