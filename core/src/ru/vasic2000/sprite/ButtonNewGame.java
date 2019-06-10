package ru.vasic2000.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.vasic2000.base.ScaledTouchUpButton;
import ru.vasic2000.math.Rect;
import ru.vasic2000.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.05f);
        setLeft(worldBounds.getLeft() + 0.03f);
        setTop(worldBounds.getTop() - 0.1f);
    }
    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
