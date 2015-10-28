package net.jakuba.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class SpaceShip {
    private static final short PROJECTILE_CATEGORY = 2;
    private static final short IGNORE_PROJECTILE_MASK = (short) (0xFFFF & ~2);
    private Texture texture = new Texture("spaceship.png");
    public Fixture fixture;

//    public static final float SCALE_FACTOR = 1;
    public static final float SHIP_SIZE = 1;
    private World world;
    private Vector2 direction;
    private ArrayList<Fixture> bullets;

    private SpaceShip() {
        direction = new Vector2(1, 0);
        bullets = new ArrayList<>();
    }

    public void applyForce(float x, float y) {
        applyForce(new Vector2(x, y));
    }

    public void applyForce(Vector2 direction) {
        final Body body = fixture.getBody();
//        body.applyLinearImpulse(direction, body.getPosition(), true);
        Vector2 current = body.getLinearVelocity();

        float thrusterForce = 3;
        Vector2 newForce = current.add(direction.scl(thrusterForce));

        float maxSpeed = 50;

        if (newForce.len() > maxSpeed) {
            newForce.setLength(maxSpeed);
        }

        body.setLinearVelocity(newForce);
//        body.applyForceToCenter(newForce, true);
    }

    public static SpaceShip createIn(World world, float x, float y) {
        BodyDef bodyDef = bodyAt(x, y);
        Body body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(SHIP_SIZE / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.filter.maskBits = IGNORE_PROJECTILE_MASK;

        Fixture fixture = body.createFixture(fixtureDef);

        final SpaceShip ship = new SpaceShip();

        ship.world = world;
        ship.fixture = fixture;

        return ship;
    }

    private static BodyDef bodyAt(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.linearDamping = 0;

        return bodyDef;
    }

    public void draw(SpriteBatch batch) {
        Vector2 position = fixture.getBody().getPosition();

        float radius = SHIP_SIZE / 2;
        float x = (position.x - radius);
        float y = (position.y - radius);
        float real_size = SHIP_SIZE;

        batch.draw(texture, x, y, 1, 1);
    }

    private Body body() { return fixture.getBody(); }

    public void stop() {
        body().setLinearVelocity(0, 0);
        body().setAngularVelocity(0);
    }

    public Vector2 getPosition() {
        return body().getPosition();
    }

    public void rotate(Vector2 direction) {
        this.direction = direction.nor();
        body().setTransform(body().getPosition(), direction.angleRad());
    }

    public void shoot() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getPosition());
        bodyDef.linearDamping = 0;

        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.filter.categoryBits = PROJECTILE_CATEGORY;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.2f); // TODO - maybe make this even smaller?
        fixtureDef.shape = circleShape;

        Fixture fixture = body.createFixture(fixtureDef);

        bullets.add(fixture);

        body.applyLinearImpulse(direction.scl(1), body.getPosition(), true);
    }

    public void mouseMoved(OrthographicCamera camera, float screenX, float screenY) {
        Vector3 shipPosition3D = camera.project(new Vector3(getPosition().x, getPosition().y, 0));

        Vector2 direction = new Vector2(screenX, screenY).sub(shipPosition3D.x, shipPosition3D.y);

        rotate(direction);
    }
}
