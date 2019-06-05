package ru.vasic2000.Pool;

import com.badlogic.gdx.audio.Sound;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.base.SpritePool;
import ru.vasic2000.sprite.Enemy;

public class EnemyPool extends SpritePool {

    private BulletPool bulletPool;
    private Sound bulletSound;

    public EnemyPool(BulletPool bulletPool, Sound bulletSound) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
    }

    @Override
    protected Sprite newObject() {
        return new Enemy(bulletPool, bulletSound);
    }
}
