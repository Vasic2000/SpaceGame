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
    private float downBorder;
    private float leftBorder;


    private float goalX;
    private float goalY;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("space.jpg");
        ship = new Texture("ship.png");

        touch = new Vector2();
        v = new Vector2(0,0);
        pos = new Vector2(150 - ship.getWidth()/2, 150 - ship.getHeight()/2);
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

        rightBorder = pos.x + ship.getWidth();
        topBorder = pos.y + ship.getHeight();
        leftBorder = pos.x;
        downBorder = pos.y;

        pos.add(v);

        if((Math.abs(pos.x - goalX) < 1) && (Math.abs(pos.y - goalY) < 1)) v.set(0,0);

        if(pos.x <= 0) pos.x = 0;
        if(pos.x >= Gdx.graphics.getWidth() - ship.getWidth()) pos.x = Gdx.graphics.getWidth() - ship.getWidth();

        if(pos.y <= 0) pos.y = 0;
        if(pos.y >= Gdx.graphics.getHeight() - ship.getHeight()) pos.y = Gdx.graphics.getHeight() - ship.getHeight();

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
        goalX = touch.x - ship.getWidth()/2;
        goalY = touch.y - ship.getHeight()/2;;

        if(goalX < 0) goalX = 0;
        if(goalX > (Gdx.graphics.getWidth() - ship.getWidth())) goalX = Gdx.graphics.getWidth() - ship.getWidth();

        if(goalY < 0) goalY = 0;
        if(goalY > (Gdx.graphics.getHeight() - ship.getHeight())) goalY = Gdx.graphics.getHeight() - ship.getHeight();

        System.out.println("X = " + goalX + ", Y = " + goalY);

        v.set((goalX - pos.x)/120, (goalY - pos.y)/120);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 19) v.set(0, 10);
        if(keycode == 20) v.set(0, -10);
        if(keycode == 21) v.set(-10, 0);
        if(keycode == 22) v.set(10, 0);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == 19) v.set(0, 0);
        if(keycode == 20) v.set(0, 0);
        if(keycode == 21) v.set(0, 0);
        if(keycode == 22) v.set(0, 0);
        return false;
    }

}
