package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.vasic2000.base.Sprite;
import ru.vasic2000.math.Rect;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.07f);
        setBottom(0.009f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.07f);
        setBottom(0.009f);
    }
}
