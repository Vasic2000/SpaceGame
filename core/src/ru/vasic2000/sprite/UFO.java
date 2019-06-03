package ru.vasic2000.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class UFO extends Sprite {

    private static final int INVALID_POINTER = -1;
    private int frequencyOfBullets;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    private Vector2 v;
    private final Vector2 v0;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    private Rect worldBounds;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    Sound lazer;

    //Счётчик кадров до выстрела
    int ii = 0;

    public UFO(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("ship"), 1, 1, 1);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("Laser");
        v = new Vector2();
        v0 = new Vector2(0.5f, 0);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        lazer = Gdx.audio.newSound(Gdx.files.internal("sound/laser.mp3"));
    }

    @Override
    public void update(float delta) {
        ii++;
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        } else if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if(ii > frequencyOfBullets) {
            shoot();
            ii = 0;
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        this.worldBounds = wordBounds;
//        int high = Gdx.graphics.getHeight();
//        float fps = Gdx.graphics.getDeltaTime();
//        frequencyOfBullets = Math.round(fps * high);
        frequencyOfBullets = 25;
        setHeightProportion(0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        ii++;
        switch (keycode) {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                shoot();
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet1 = bulletPool.obtain();
        Bullet bullet2 = bulletPool.obtain();
        bulletPos.set(pos);
        bulletPos.x += 0.035f;
        bulletPos.y += getHalfHeight() - 0.035f;
        bullet1.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, 1);
        bulletPos.x -= 0.07f;
        bullet2.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, 1);
        lazer.play();
    }
}
