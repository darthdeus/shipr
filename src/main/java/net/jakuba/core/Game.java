package net.jakuba.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.jakuba.model.SpaceShip;

public class Game extends ApplicationAdapter {
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer boxRenderer;
    private OrthographicCamera camera;
    private SpaceShip ship;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Box2D.init();

        world = new World(new Vector2(0, 0), true);

        ship = SpaceShip.createIn(world, 100, 100);

        boxRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);

//        ship.setSize(64, 64);
//        ship.setPosition(20, 20);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float userInputForce = 1000000;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.applyForce(-userInputForce, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.applyForce(userInputForce, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ship.applyForce(0, -userInputForce);
//            body.applyLinearImpulse(new Vector2(0, -2000f), body.getPosition(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ship.applyForce(0, userInputForce);
//            body.applyForceToCenter(0, 20000, true);
//            ship.y += 200 * Gdx.graphics.getDeltaTime();
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        ship.draw(batch);
        batch.end();

        world.step(1/60f, 6, 2);
        boxRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
