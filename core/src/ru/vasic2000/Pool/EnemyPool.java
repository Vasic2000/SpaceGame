package ru.vasic2000.Pool;

import com.badlogic.gdx.audio.Sound;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.base.SpritePool;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Enemy;

public class EnemyPool extends SpritePool<Enemy> {

    private BulletPool bulletPool;
    private Sound bulletSound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, bulletSound, worldBounds);
    }
}