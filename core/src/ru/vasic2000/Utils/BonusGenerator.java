package ru.vasic2000.Utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BonusPool;
import ru.vasic2000.math.Rect;

public class BonusGenerator {
    private Rect worldBounds;
    private float generateInterval = 2f;
    private float generateTimer;
    private BonusPool bonusPool;

    private final TextureRegion[] aidRegion;
    private final TextureRegion[] deathMediumRegion;
    private final TextureRegion[] laser2Region;

    private final Vector2 bonusV = new Vector2(0f, -0.1f);

    public BonusGenerator(Rect worldBounds, BonusPool bonusPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.bonusPool = bonusPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.aidRegion = Regions.split(textureRegion0, 1, 2, 2);
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.deathMediumRegion = Regions.split(textureRegion1, 1, 2, 2);
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.laser2Region = Regions.split(textureRegion2, 1, 2, 2);

    }

}
