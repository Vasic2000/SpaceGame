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
/*
    private SpriteBatch batch;
    private Texture img;
    private Texture ship;
*/
//
//    private static final float LEN = 0.5f;
//    private Vector2 touch;
//    private Vector2 v;
//    private Vector2 pos;
//    private Vector2 buf;

  /*
    private float topBorder;
    private float rightBorder;
    private float downBorder;
    private float leftBorder;
*/
    private float goalX;
    private float goalY;

    private Texture bg;
    private Texture badLogicTexture;
    private Background background;
    private BadLogic badLogic;


    @Override
    public void show() {
        super.show();
        /*
        batch = new SpriteBatch();
        img = new Texture("nebo2.jpg");
        ship = new Texture("ship.png");
        */

//        touch = new Vector2();
//        v = new Vector2(0,0);
//        pos = new Vector2();
//        buf = new Vector2();


        bg = new Texture("nebo2.jpg");
        background = new Background(new TextureRegion(bg));
        badLogicTexture = new Texture("ship.png");
        badLogic = new BadLogic(new TextureRegion(badLogicTexture));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        badLogic.draw(batch);
        batch.end();

//      Условия невылета за экран
        if (badLogic.pos.x <= -0.5f + badLogic.getHalfWidth()) {
            v.set(0, v.y);
            badLogic.pos.set(-0.5f + badLogic.getHalfWidth() + 0.01f, badLogic.pos.y);
        }
        if (badLogic.pos.x >= 0.5f - badLogic.getHalfWidth()) {
            v.set(0, v.y);
            badLogic.pos.set(0.5f - badLogic.getHalfWidth() - 0.01f, badLogic.pos.y );
        }
        if (badLogic.pos.y <= -0.5f + badLogic.getHalfHeight()) {
            v.set(v.x, 0);
            badLogic.pos.set(badLogic.pos.x, -0.5f + badLogic.getHalfHeight() + 0.01f);
        }
        if (badLogic.pos.y >= 0.5f - badLogic.getHalfHeight()) {
            v.set(v.x, 0);
            badLogic.pos.set(badLogic.pos.x, 0.5f - badLogic.getHalfHeight() - 0.01f);
        }

        badLogic.pos.add(v);
        System.out.println("X = " + badLogic.pos.x + "; Y = " + badLogic.pos.y);

    }

    @Override
    public void dispose() {
        bg.dispose();
        badLogicTexture.dispose();
        super.dispose();
    }

     @Override
    public boolean keyDown(int keycode) {
        if(keycode == 19) v.set(v.x, 0.02f);
        if(keycode == 20) v.set(v.x, -0.02f);
        if(keycode == 21) v.set(-0.02f, v.y);
        if(keycode == 22) v.set(0.02f, v.y);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == 19) v.set(v.x, 0);
        if(keycode == 20) v.set(v.x, 0);
        if(keycode == 21) v.set(0, v.y);
        if(keycode == 22) v.set(0, v.y);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        badLogic.resize(worldBounds);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        System.out.println("Тач X = " + touch.x + "; Тач Y = " + touch.y);
        System.out.println("V X = " + v.x + "; V Y = " + v.y);
        return false;
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

}
