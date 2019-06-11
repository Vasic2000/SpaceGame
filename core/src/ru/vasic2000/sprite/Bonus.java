package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.BonusType;
import ru.vasic2000.base.Sprite;

public class Bonus extends Sprite {
    public BonusType type;

    public void set(
        TextureRegion[] regions,
        Vector2 v0,
        BonusType type
    ) {
            this.regions = regions;
            this.pos.set(v0);
            this.type = type;
    }


}
