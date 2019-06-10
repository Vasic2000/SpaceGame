package ru.vasic2000.Pool;

import com.badlogic.gdx.audio.Sound;

import ru.vasic2000.base.SpritePool;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Enemy;
import ru.vasic2000.sprite.UFO;

public class EnemyPool extends SpritePool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound bulletSound;
    private Rect worldBounds;
    private UFO mainShip;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound, Rect worldBounds, UFO mainShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, bulletSound, worldBounds, mainShip);
    }
}
