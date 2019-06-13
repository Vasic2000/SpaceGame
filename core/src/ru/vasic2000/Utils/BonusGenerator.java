package ru.vasic2000.Utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BonusPool;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Bonus;

public class BonusGenerator {
    private Rect worldBounds;
    private BonusPool bonusPool;
    private final TextureRegion[] aidRegion;
    private final TextureRegion[] deathMediumRegion;
    private final TextureRegion[] laser2Region;

    private final Vector2 bonusV = new Vector2(0f, -0.1f);

    public BonusGenerator(Rect worldBounds, BonusPool bonusPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.bonusPool = bonusPool;
        TextureRegion textureRegion0 = atlas.findRegion("AID");
        this.aidRegion = Regions.split(textureRegion0, 1, 1, 1);
        TextureRegion textureRegion1 = atlas.findRegion("DEATH");
        this.deathMediumRegion = Regions.split(textureRegion1, 1, 1, 1);
        TextureRegion textureRegion2 = atlas.findRegion("LASER2");
        this.laser2Region = Regions.split(textureRegion2, 1, 1, 1);
    }

    public void generate(Vector2 pos) {
        float type = (float) Math.random();
        Bonus bonus = bonusPool.obtain();;
        if (type > 0.85f)
            bonus.set(laser2Region,  pos, bonusV, worldBounds, Bonus.Tipe.Laser2);
        else if (type > 0.7f)
            bonus.set(deathMediumRegion,  pos, bonusV, worldBounds, Bonus.Tipe.DEATH);
        else if (type > 0.45f)
            bonus.set(aidRegion,  pos, bonusV, worldBounds, Bonus.Tipe.AID);
        else bonus.destroy();
    }

}
