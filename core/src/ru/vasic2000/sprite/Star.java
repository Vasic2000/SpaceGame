package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;
import ru.vasic2000.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vX = Rnd.nextFloat(-0.005f, 0.005f);
        float vY = Rnd.nextFloat(-0.03f, -0.01f);
        v = new Vector2(vX, vY);
        setHeightProportion(0.01f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        this.worldBounds = wordBounds;
        float posX = Rnd.nextFloat(wordBounds.getLeft(), wordBounds.getRight());
        float posY = Rnd.nextFloat(wordBounds.getBottom(), wordBounds.getTop());
        pos.set(posX, posY);
    }
}
