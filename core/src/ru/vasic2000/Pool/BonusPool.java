package ru.vasic2000.Pool;

import ru.vasic2000.base.SpritePool;
import ru.vasic2000.sprite.Bonus;

public class BonusPool extends SpritePool<Bonus> {

    @Override
    protected Bonus newObject() {
        return new Bonus();
    }
}
