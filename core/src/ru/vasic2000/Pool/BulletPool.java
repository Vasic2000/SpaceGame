package ru.vasic2000.Pool;

import ru.vasic2000.base.SpritePool;
import ru.vasic2000.sprite.Bullet;

public class BulletPool extends SpritePool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
