package ru.vasic2000.Utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.Pool.BonusPool;
import ru.vasic2000.base.BonusType;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Bonus;

public class BonusGenerator {
    private static final float AID_HEIGHT = 0.1f;
    private static final float AID_VY = -0.3f;

    private static final float DEATH_HEIGHT = 0.1f;
    private static final float DEATH_VY = -0.3f;

    private static final float Laser_2_HEIGHT = 0.1f;
    private static final float Laser_2_VY = -0.3f;

    private Rect worldBounds;

    private final TextureRegion[] aidRegion;
    private final TextureRegion[] deathRegion;
    private final TextureRegion[] laser2BigRegion;

    private final Vector2 aidV = new Vector2(0f, -0.05f);
    private final Vector2 deathV = new Vector2(0f, -0.05f);
    private final Vector2 laser2V = new Vector2(0f, -0.05f);

    private BonusPool bonusPool;

    public BonusGenerator(Rect worldBounds, BonusPool bonusPool, TextureAtlas atlas) {
        this.bonusPool = bonusPool;
        this.worldBounds = worldBounds;
        TextureRegion textureAidRegion = atlas.findRegion("AID");
        this.aidRegion = Regions.split(textureAidRegion, 1, 1, 1);
        TextureRegion textureDeathRegion = atlas.findRegion("Death");
        this.deathRegion = Regions.split(textureDeathRegion, 1, 1, 1);
        TextureRegion textureLaser2BigRegion = atlas.findRegion("Laser2");
        this.laser2BigRegion = Regions.split(textureLaser2BigRegion, 1, 1, 1);
    }

    public void generate(Vector2 pos1) {
            Bonus bonus = bonusPool.obtain();

//            float type = (float) Math.random();
            bonus.set(aidRegion, pos1, BonusType.AID);
//            if (type < 0.5f) {
//                bonus.set(aidRegion, pos1, BonusType.AID);
//            } else if (type < 0.8f) {
//                bonus.set(deathRegion, pos1, BonusType.DEATH);
//            } else {
//                bonus.set(laser2BigRegion, pos1, BonusType.LASER2);
//            }
    }
}
