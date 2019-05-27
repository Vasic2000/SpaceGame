package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.BaseScreen;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Background;
import ru.vasic2000.sprite.ButtonExit;
import ru.vasic2000.sprite.Star;
import ru.vasic2000.sprite.UFO;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;

    private Texture badLogicTexture;
    private UFO badLogic;

    @Override
    public void show() {
        super.show();
        bg = new Texture("nebo2.jpg");
        background = new Background(new TextureRegion(bg));
        badLogicTexture = new Texture("ship.png");
        badLogic = new UFO(new TextureRegion(badLogicTexture));

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star : starArray)
            star.update(delta);

        badLogic.update(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray)
            star.draw(batch);

        badLogic.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        badLogic.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        badLogicTexture.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode != 131) {
            badLogic.keyDown(keycode);
            return super.keyDown(keycode);
        }
        else {
            Gdx.app.exit();
            return true;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        badLogic.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        badLogic.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
