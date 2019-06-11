package ru.vasic2000.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.Pool.ExplosionPool;
import ru.vasic2000.math.Rect;

public class UFO extends Ship {

    Vector2 bulletPos;

    private static final int INVALID_POINTER = -1;

    public static int getMaHP() {
        return maHP;
    }

    private static final int maHP = 75;

    private static final int HP = 50;

    private boolean pressedLeft;
    private boolean pressedRight;

    public void set2laser(boolean is2laser) {
        this.is2laser = is2laser;
    }

    private boolean is2laser = false;

    private float is2laserInterval = 10f;
    private float is2laserTimer;

    public void setIs2laserTimer(float is2laserTimer) {
        this.is2laserTimer = is2laserTimer;
    }


    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public UFO(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(atlas.findRegion("ship2"), 1, 2, 2);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("Laser");
        v = new Vector2();
        v0 = new Vector2(0.5f, 0);
        bulletV = new Vector2(0, 0.5f);
        this.reloadInterval = 0.2f;
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.bulletSound = bulletSound;
        this.hp = HP;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        reloadTimer += delta;
        is2laserTimer +=delta;

        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }

        if (is2laserTimer >= is2laserInterval) {
            is2laser = false;
        }

        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        } else if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public void startNewGame() {
        this.hp = HP;
        this.pos.x = worldBounds.pos.x;
        setDestroyed(false);
        flushDestroy();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
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

    @Override
    protected void shoot() {
        bulletPos = new Vector2(pos);
        if(!is2laser)
            super.shoot();
        else {
            bulletSound.play();
            Bullet bullet1 = bulletPool.obtain();
            bulletPos.set(pos.x + getHalfWidth(), pos.y);
            bullet1.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
            Bullet bullet2 = bulletPool.obtain();
            bulletPos.set(pos.x - getHalfWidth(), pos.y);
            bullet2.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        }
    }

    public boolean keyDown(int keycode) {
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

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > pos.y
                        || bullet.getTop() < getBottom()
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        is2laser = false;
        stop();
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
}
