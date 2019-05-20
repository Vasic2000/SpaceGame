package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private SpriteBatch batch;
    private Texture img;
    private Texture ship;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 pos;
    private float topBorder;
    private float rightBorder;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("space.jpg");
        ship = new Texture("ship.png");

        touch = new Vector2();
        v = new Vector2(0.7f,0.7f);
        pos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0 , 0);
        batch.draw(ship, pos.x, pos.y);
        batch.end();
        topBorder = pos.x + ship.getWidth();
        rightBorder = pos.y + ship.getHeight();
        if (topBorder < Gdx.graphics.getWidth()
                && rightBorder < Gdx.graphics.getHeight()) {
            pos.add(v);
        } else {
            v.set(-0.7f, -0.7f);
            pos.add(v);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        ship.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

}
