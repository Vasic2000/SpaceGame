package ru.vasic2000;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import javax.xml.soap.Text;

public class SpaceGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture ship;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		try {
			img = new Texture("space.jpg");
			ship = new Texture("ship.png");
		}
		catch (Exception e){
			img = new Texture("C:\\Users\\User\\Desktop\\SpaceGame\\android\\assets\\space.jpg");
			ship = new Texture("C:\\Users\\User\\Desktop\\SpaceGame\\android\\assets\\ship.png");
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(ship, 250, 250);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		ship.dispose();
	}
}
