package ru.vasic2000.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import ru.vasic2000.sprite.Enemy;
import ru.vasic2000.sprite.UFO;

public abstract class SpritePool<T extends Sprite> extends Sprite {
    protected final List<T> activeObjects = new ArrayList<T>();
    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println(getClass().getName() + " active/free : " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float delta, UFO ship) {
        double distance, twoHalfMidleSize;
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                // Взрываю корабли
                if(sprite instanceof Enemy) {
                    distance = Math.sqrt(Math.pow(sprite.pos.x - ship.pos.x, 2) + Math.pow(sprite.pos.y - ship.pos.y, 2));
                    twoHalfMidleSize = Math.sqrt(sprite.getHalfHeight() * ship.getHalfHeight());
                    if(distance < twoHalfMidleSize) {
                        sprite.destroy();
                    }
                }
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    private void free(T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
        System.out.println(getClass().getName() + " active/free : " + activeObjects.size() + "/" + freeObjects.size());
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

}
