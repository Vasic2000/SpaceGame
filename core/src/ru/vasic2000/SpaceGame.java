package ru.vasic2000;

import com.badlogic.gdx.Game;

import ru.vasic2000.screen.MenuScreen;

public class SpaceGame extends Game {
		@Override

		public void create() {
			setScreen(new MenuScreen());
		}
}
