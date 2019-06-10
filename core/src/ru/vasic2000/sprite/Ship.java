package ru.vasic2000.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.Pool.ExplosionPool;
import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class Ship extends Sprite {
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;

    protected Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;
    protected float bulletHeight;

    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int damage;

    public void setHp(int hp) {
        this.hp = hp;
    }

    protected int hp;

    protected Sound bulletSound;

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        bulletSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
