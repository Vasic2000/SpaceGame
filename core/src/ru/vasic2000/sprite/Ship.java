package ru.vasic2000.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class Ship extends Sprite {
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;
    protected float bulletHeight;

    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int damage;
    protected int hp;

    protected Sound bulletSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    private void shoot() {
        bulletSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }
}
