package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.Pool.EnemyPool;
import ru.vasic2000.Pool.ExplosionPool;
import ru.vasic2000.Utils.EnemyGenerator;
import ru.vasic2000.base.BaseScreen;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Background;
import ru.vasic2000.sprite.Enemy;
import ru.vasic2000.sprite.Explosion;
import ru.vasic2000.sprite.Star;
import ru.vasic2000.sprite.UFO;


public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas, atlas2;
    private Star[] starArray;

    private UFO mainShip;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private Music music;
    private Sound laserSound;
    private Sound explosionSound;
    private Sound bulletSound;

    private EnemyGenerator enemyGenerator;

    @Override
    public void show() {
        super.show();

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/Butterfly.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sound/laser.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sound/bullet.wav"));

        bg = new Texture("nebo2.jpg");
        background = new Background(new TextureRegion(bg));
        atlas2 = new TextureAtlas("textures/ufo2.pack");

        bulletPool = new BulletPool();

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);
        mainShip = new UFO(atlas2, bulletPool, explosionPool, laserSound);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void freeAllDestroyedActiveObjects() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void update(float delta) {
        for (Star star : starArray)
            star.update(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
        }
        bulletPool.updateActiveSprites(delta, mainShip,  explosionPool);
        explosionPool.updateActiveSprites(delta, mainShip,  explosionPool);
        enemyPool.updateActiveSprites(delta, mainShip,  explosionPool);
        enemyGenerator.generate(delta);
    }

    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.destroy();
            }
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray)
            star.draw(batch);
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return false;
    }
}
