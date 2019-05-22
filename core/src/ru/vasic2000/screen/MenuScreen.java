package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.BaseScreen;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Background;
import ru.vasic2000.sprite.BadLogic;

public class MenuScreen extends BaseScreen {

    private SpriteBatch batch;
    private Texture img;
    private Texture ship;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 pos;
    private Vector2 go;
    private float topBorder;
    private float rightBorder;
    private float downBorder;
    private float leftBorder;

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

        touch = new Vector2();
        v = new Vector2(0,0);
        pos = new Vector2(150 - ship.getWidth()/2, 150 - ship.getHeight()/2);
        go = pos;
        */

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

        /*
        batch.begin();
        batch.draw(img, 0 , 0);
        batch.draw(ship, pos.x, pos.y);
        batch.end();

        rightBorder = pos.x + ship.getWidth();
        topBorder = pos.y + ship.getHeight();
        leftBorder = pos.x;
        downBorder = pos.y;

        pos.add(v);

        if((Math.abs(pos.x - goalX) < 2) && (Math.abs(pos.y - goalY) < 2)) {
            v.set(0,0);
            pos.set(goalX, goalY);
        }

        if(pos.x <= 0) pos.x = 0;
        if(pos.x >= Gdx.graphics.getWidth() - ship.getWidth()) pos.x = Gdx.graphics.getWidth() - ship.getWidth();

        if(pos.y <= 0) pos.y = 0;
        if(pos.y >= Gdx.graphics.getHeight() - ship.getHeight()) pos.y = Gdx.graphics.getHeight() - ship.getHeight();
*/
        batch.begin();
        background.draw(batch);
        badLogic.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        /*
        batch.dispose();
        img.dispose();
        ship.dispose();
        */
        bg.dispose();
        badLogicTexture.dispose();
        super.dispose();
    }

    /*
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

//      Псевдоускорение, чтобы кораблик перемещался ~ за одно и то же время в зависимости от расстояния
        System.out.println("X = " + goalX + ", Y = " + goalY);
        if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 50)
            v.set((goalX - pos.x) / 69, (goalY - pos.y) / 69);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 100)
            v.set((goalX - pos.x) / 67, (goalY - pos.y) / 67);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 150)
            v.set((goalX - pos.x) / 63, (goalY - pos.y) / 63);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 200)
            v.set((goalX - pos.x) / 60, (goalY - pos.y) / 60);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 300)
            v.set((goalX - pos.x) / 55, (goalY - pos.y) / 55);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 500)
            v.set((goalX - pos.x) / 50, (goalY - pos.y) / 50);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 750)
            v.set((goalX - pos.x) / 40, (goalY - pos.y) / 40);
        else if ((Math.abs(goalX - pos.x) + Math.abs(goalY - pos.y)) < 1000)
            v.set((goalX - pos.x) / 35, (goalY - pos.y) / 35);
        else
            v.set((goalX - pos.x) / 30, (goalY - pos.y) / 30);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 19) v.set(v.x, 10);
        if(keycode == 20) v.set(v.x, -10);
        if(keycode == 21) v.set(-10, v.y);
        if(keycode == 22) v.set(10, v.y);
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
*/
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        badLogic.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

}
