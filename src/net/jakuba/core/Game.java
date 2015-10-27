package net.jakuba.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter {
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render() {
        super.render();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
