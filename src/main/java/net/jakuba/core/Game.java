package net.jakuba.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter {
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    private SpriteBatch batch;
    private Rectangle ship;
    private Texture shipTexture;
    private World world;
    private Box2DDebugRenderer boxRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        ship = new Rectangle(0, 0, 64, 64);
        shipTexture = new Texture("ship.png");

        Box2D.init();

        world = new World(new Vector2(0, 0), false);
        boxRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);

//        ship.setSize(64, 64);
//        ship.setPosition(20, 20);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ship.y -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ship.y += 200 * Gdx.graphics.getDeltaTime();
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(shipTexture, ship.x, ship.y, ship.width, ship.height);
        batch.end();

        world.step(1/60f, 6, 2);
        boxRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
