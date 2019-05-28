package ru.vasic2000.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class UFO extends Sprite {
    private static final float LEN = 0.005f;
    private Vector2 v;
    private Vector2 touch;
    private Vector2 buf;

    public UFO(TextureRegion region) {
        super(region);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        v = new Vector2();
        touch = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buf.set(touch);

        if (buf.sub(pos).len() <= LEN) {
            pos.set(touch);
            v.set(0,0);
        } else {
            pos.add(v);

        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.05f);
        setLeft(wordBounds.getLeft() + wordBounds.getHalfWidth() - 0.025f);
        setBottom(wordBounds.getBottom() + 0.03f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("pos.x = " + pos.x + " pos.y " + pos.y);
        System.out.println("X = " + touch.x + " pos.y " + pos.y);
        this.touch = touch;
        touch.set(touch.x, pos.y);
        System.out.println("X = " + touch.x + " Y " + touch.y);
         v.set(touch.cpy().sub(pos)).setLength(LEN);
        return false;
    }

    public boolean keyDown(int keycode) {
        if(keycode == 131)
            Gdx.app.exit();
        if(keycode == 21)
            v.set(-0.005f, 0);
        if(keycode == 22)
            v.set(0.005f, 0);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped keycode = " + character);
        return false;
    }

    public boolean keyUp(int keycode) {
        if(keycode == 21)
            v.set(0, 0);
        if(keycode == 22)
            v.set(0, 0);
        return false;
    }
}
