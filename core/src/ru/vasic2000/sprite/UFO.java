package ru.vasic2000.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class UFO extends Sprite {
    private static final float LEN = 0.01f;
    private Vector2 v;
    private Vector2 touch;
    private Vector2 buf;

    float LeftBorder;
    float RightBorder;

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
            touch.set(0,0);
        } else {
            pos.add(v);
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.05f);
        setLeft(wordBounds.getLeft() + wordBounds.getHalfWidth() - 0.025f);
        setBottom(wordBounds.getBottom() + 0.03f);
        LeftBorder = wordBounds.getLeft() + this.getHalfWidth();
        RightBorder = wordBounds.getRight() - this.getHalfWidth();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        if(touch.x < LeftBorder)
            touch.set(LeftBorder, pos.y);
        else if(touch.x > RightBorder)
            touch.set(RightBorder, pos.y);
        else
            touch.set(touch.x, pos.y);
        v.set(touch.cpy().sub(pos)).setLength(LEN);
        return false;
    }

    public boolean keyDown(int keycode) {
        if(keycode == 131)
            Gdx.app.exit();
        if (keycode == 21)
            v.set(-0.01f, 0);
        if(pos.x > LeftBorder)
            pos.add(v);
        if (keycode == 22)
            v.set(0.01f, 0);
        if(pos.x < RightBorder)
            pos.add(v);
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
