package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class Bonus extends Sprite {

    public enum Tipe {AID, DEATH, Laser2}
    public Tipe tipe;
    private Vector2 v0 = new Vector2();
    private Rect worldBounds;

    public Bonus() {
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v0, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(TextureRegion[] regions, Vector2 pos0, Vector2 v0, Rect worldBounds, Tipe tipe) {
        this.pos.set(pos0);
        this.regions = regions;
        this.worldBounds = worldBounds;
        this.v0.set(v0);
        this.tipe = tipe;
        setHeightProportion(0.05f);
    }

}
