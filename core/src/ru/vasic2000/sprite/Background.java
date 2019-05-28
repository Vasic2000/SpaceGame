package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect wordBounds) {
        if(wordBounds.getHeight() > wordBounds.getWidth())
            setHeightProportion(1f);
        else setHeightProportion(wordBounds.getWidth()/wordBounds.getHeight());
        pos.set(wordBounds.pos);
    }
}
