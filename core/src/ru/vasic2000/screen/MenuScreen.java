package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.BaseScreen;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Background;
import ru.vasic2000.sprite.BadLogic;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture badLogicTexture;
    private Background background;
    private BadLogic badLogic;


    @Override
    public void show() {
        super.show();
        bg = new Texture("nebo2.jpg");
        background = new Background(new TextureRegion(bg));
        badLogicTexture = new Texture("ship.png");
        badLogic = new BadLogic(new TextureRegion(badLogicTexture));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        badLogic.update(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        badLogic.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        badLogicTexture.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        badLogic.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        badLogic.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        badLogic.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        badLogic.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

}
