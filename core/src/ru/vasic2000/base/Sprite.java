package ru.vasic2000.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.math.Rect;

public class Sprite extends Rect {

    protected static final float LEN = 0.5f;
    protected Vector2 touch;
    protected Vector2 v;
    protected Vector2 pos;
    protected Vector2 buf;

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        this.v = new Vector2(0,0);
        this.pos = new Vector2();
        this.buf = new Vector2();

        this.regions = new TextureRegion[1];
        this.regions[0] = region;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void update(float delta) {

    }

    public void resize(Rect wordBounds) {

    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        buf.set(touch);
        if (buf.sub(pos).len() <= LEN) {
            pos.set(touch);
        } else {
            pos.add(v);
        }
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
