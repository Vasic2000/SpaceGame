package ru.vasic2000.Pool;

import ru.vasic2000.base.SpritePool;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Bonus;
import ru.vasic2000.sprite.UFO;

public class BonusPool extends SpritePool<Bonus> {
    private Rect worldBounds;
    private UFO mainShip;

    public BonusPool(Rect worldBounds, UFO mainShip) {
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected Bonus newObject() {
        return new Bonus();
    }
}
