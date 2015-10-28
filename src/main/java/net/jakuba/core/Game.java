package net.jakuba.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import net.jakuba.input.MapInputProcessor;
import net.jakuba.model.Asteroid;
import net.jakuba.model.SpaceShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends ApplicationAdapter {
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 900;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer boxRenderer;
    private OrthographicCamera camera;
    private SpaceShip ship;
    private List<Asteroid> asteroids;
    private SpriteBatch hudBatch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        Box2D.init();

        world = new World(new Vector2(0, 0), true);

        ship = SpaceShip.createIn(world, 5, 5);

        boxRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
        camera.zoom = 0.15f;
        camera.update();

        asteroids = new ArrayList<>();

        MapInputProcessor inputProcessor = new MapInputProcessor(camera, ship);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        camera.position.set(ship.getPosition().x, ship.getPosition().y, 0);

        ship.fixture.getBody().setAngularVelocity(0);

        Vector2 direction = null;

//        System.out.println(Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction = new Vector2(-1, 0);
            ship.applyForce(direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction = new Vector2(1, 0);
            ship.applyForce(direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction = new Vector2(0, -1);
            ship.applyForce(direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction = new Vector2(0, 1);
            ship.applyForce(direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            ship.stop();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            Random random = new Random();
            float size = random.nextFloat() * 30 + 20;
            float x = random.nextFloat() * (WINDOW_WIDTH / 2 - size);
            float y = random.nextFloat() * (WINDOW_HEIGHT / 2 - size);

            System.out.printf("Spawned %f at %f %f\n", size, x, y);

            final Asteroid asteroid = Asteroid.createIn(world, size, x, y);

            asteroids.add(asteroid);
        }

        if (direction != null) {
            // TODO - camera handling
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        ship.draw(batch);

        for (Asteroid asteroid : asteroids) {
            asteroid.draw(batch);
        }

        BitmapFont font = new BitmapFont();
        Body body = ship.fixture.getBody();
        Vector2 velocity = body.getLinearVelocity();

        batch.end();

        hudBatch.begin();

        String state = String.format("Velocity: %f %f\nPosition: %f %f\nZoom: %f",
                velocity.x, velocity.y,
                body.getPosition().x, body.getPosition().y,
                camera.zoom);

        font.draw(hudBatch, state, 50, 50);

        hudBatch.end();

        boxRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
