package ru.vasic2000;

import com.badlogic.gdx.Game;

public class SpaceGame extends Game {
		@Override

		public void create() {
			setScreen(new ru.vasic2000.screen.MenuScreen());
		}
}
