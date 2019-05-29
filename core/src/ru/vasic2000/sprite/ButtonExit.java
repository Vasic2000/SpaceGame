package ru.vasic2000.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vasic2000.base.ScaledTouchUpButton;
import ru.vasic2000.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {
    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.18f);
    }

    @Override
    public void resize(Rect wordlBounds) {
        setLeft(wordlBounds.getLeft() + 0.03f);
        setBottom(wordlBounds.getBottom() + 0.03f);
    }
    @Override
    public void action() {
        Gdx.app.exit();
    }
}
