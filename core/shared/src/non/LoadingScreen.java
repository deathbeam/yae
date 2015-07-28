package non;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class LoadingScreen implements Disposable {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Sprite background;
    private final GlyphLayout glyphs = new GlyphLayout();
    private String text = "";

    public LoadingScreen() {
        batch = new SpriteBatch();
        font = new FreeTypeFontGenerator(Gdx.files.internal("non/font.ttf")).generateFont(16);
        font.getRegion(0).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        background = new Sprite(new Texture(Gdx.files.internal("non/splash.png")));
        background.setOrigin(0, 0);
    }

    public void render () {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        font.draw(batch, text, (Gdx.graphics.getWidth() - glyphs.width) / 2, (Gdx.graphics.getHeight() - glyphs.height) / 2);
        batch.end();
    }

    public void resize(int width, int height) { 
        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
        float scale = (float)width / (float)background.getWidth();
        background.setScale(scale);
    }

    public void setText(String text) {
        this.text = text;
        glyphs.setText(font, text);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}