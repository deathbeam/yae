package non;

import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Non implements ApplicationListener {
    private boolean ready;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background, logo, bar;
    private final Vector2 backgroundPos = new Vector2();
    private final Vector2 backgroundSize = new Vector2();
    private final Vector2 logoPos = new Vector2();
    private final Vector2 barPos = new Vector2();
    private final Vector2 textPos = new Vector2();
    private int loadState;
    private String text;
    private final Map config;

    public Non(Map config) {
        this.config = config;
        
        if (config.containsKey("logging")) {
            String logLevel = (String)config.get("logging");
                
            if ("".equalsIgnoreCase(logLevel))
                Gdx.app.setLogLevel(0);
            else if ("error".equalsIgnoreCase(logLevel))
                Gdx.app.setLogLevel(1);
            else if ("info".equalsIgnoreCase(logLevel))
                Gdx.app.setLogLevel(2);
            else if ("debug".equalsIgnoreCase(logLevel))
                Gdx.app.setLogLevel(3);
        }
    }
    
    public void create () {    
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        background = new Texture(Gdx.files.internal("non/background.png"));
        logo = new Texture(Gdx.files.internal("non/logo.png"));
        bar = new Texture(Gdx.files.internal("non/bar.png"));
        text = "";
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
        batch.draw(background, backgroundPos.x, backgroundPos.y, backgroundSize.x, backgroundSize.y);   
        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(bar, barPos.x, barPos.y);
        font.draw(batch, text, textPos.x, textPos.y);
        batch.end();
    }

    public void resize(int width, int height) { 
        if (ready) {
            JModule.resize(width, height);
            JModule.call("resize", width, height);
            return;
        }
        
        if (batch == null) return;
        
        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));

        float scale = (float)width / (float)background.getWidth();
        backgroundPos.set(0, height - (background.getHeight() * scale));
        backgroundSize.set(background.getWidth() * scale, background.getHeight() * scale);
        logoPos.set((width - logo.getWidth())/2, (height - logo.getHeight())/2);
        barPos.set((width - bar.getWidth())/2, logoPos.y - bar.getHeight());
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
        textPos.set((Gdx.graphics.getWidth() - font.getBounds(text).width) /2, barPos.y + (bar.getHeight() + 12) /2);
    }
    
    private void disposeLoading() {
        batch.dispose();
        batch = null;
        font.dispose();
        font = null;
        background.dispose();
        background = null;
        logo.dispose();
        logo = null;
        bar.dispose();
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