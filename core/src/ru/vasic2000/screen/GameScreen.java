package ru.vasic2000.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.vasic2000.Pool.BulletPool;
import ru.vasic2000.Pool.EnemyPool;
import ru.vasic2000.Pool.ExplosionPool;
import ru.vasic2000.Utils.EnemyGenerator;
import ru.vasic2000.base.BaseScreen;
import ru.vasic2000.base.Font;
import ru.vasic2000.math.Rect;
import ru.vasic2000.sprite.Background;
import ru.vasic2000.sprite.Bullet;
import ru.vasic2000.sprite.ButtonExit;
import ru.vasic2000.sprite.ButtonNewGame;
import ru.vasic2000.sprite.Enemy;
import ru.vasic2000.sprite.MessageGameOver;
import ru.vasic2000.sprite.Star;
import ru.vasic2000.sprite.UFO;


public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final int STAR_COUNT = 64;

    public static State state;
    private enum State {PLAY, PAUSE, GAME_OVER}

    private Texture bg;
    private Background background;
    private TextureAtlas atlas, atlas2, atlas3;
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
    private MessageGameOver messageGameOver;

    private ButtonNewGame buttonNewGame;
    private ButtonExit buttonExit;

    private int frags;
    private Font font;

    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;

    @Override
    public void show() {
        super.show();

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.03f);

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
        atlas3 = new TextureAtlas("textures/menuAtlas.tpack");
        explosionPool = new ExplosionPool(atlas, explosionSound);

        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        mainShip = new UFO(atlas2, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds, mainShip);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        frags = 0;

        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();

        state = State.PLAY;
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        buttonExit = new ButtonExit(atlas3);
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PLAY;
        music.play();
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
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAY) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAY) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.damage(mainShip.getHp());
                state = State.GAME_OVER;
                music.stop();
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                    music.stop();
                }
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

        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAY) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonExit.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonExit.resize(worldBounds);
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
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAY)
            mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY)
            mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAY)
            mainShip.touchDown(touch, pointer);
        else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer);
            buttonExit.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAY)
            mainShip.touchUp(touch, pointer);
        else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
            buttonExit.touchUp(touch, pointer);
        }
        return false;
    }

    public void startNewGame() {
        state = State.PLAY;
        mainShip.startNewGame();
        frags = 0;
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        music.play();
    }

}
