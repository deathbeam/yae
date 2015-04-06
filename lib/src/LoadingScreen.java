package non;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class LoadingScreen {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Sprite background, logo;
    private final Vector2 textPos = new Vector2();
    private String text = "";

    public LoadingScreen() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Sprite(new Texture(Gdx.files.internal("non/splash.png")));
        background.setOrigin(0, 0);
        logo = new Sprite(new Texture(Gdx.files.internal("non/logo.png")));
        font.setColor(1, 1, 1, 0.6f);
        logo.setColor(1, 1, 1, 0.6f);
    }

    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        font.draw(batch, text, textPos.x, textPos.y);
        batch.end();
    }

    public void resize(int width, int height) { 
        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
        float scale = (float)width / (float)background.getWidth();
        background.setScale(scale);
        logo.setCenter(width/2, height/2);
        textPos.set((Gdx.graphics.getWidth() - font.getBounds(text).width) /2, logo.getY() - 18);
    }

    public void setText(String text) {
        this.text = text;
        textPos.set((Gdx.graphics.getWidth() - font.getBounds(text).width) /2, logo.getY() - 18);
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}