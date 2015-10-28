package net.jakuba.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class SpaceShip {
    private Texture texture = new Texture("spaceship.png");
    public Fixture fixture;

//    public static final float SCALE_FACTOR = 1;
    public static final float SHIP_SIZE = 5;

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

        Fixture fixture = body.createFixture(fixtureDef);

        final SpaceShip ship = new SpaceShip();

        ship.fixture = fixture;

        return ship;
    }

    private static BodyDef bodyAt(float x, float y) {
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.DynamicBody;
        body.position.set(x, y);
        body.linearDamping = 0;

        return body;
    }

    public void draw(SpriteBatch batch) {
        Vector2 position = fixture.getBody().getPosition();

        float radius = SHIP_SIZE / 2;
        float x = (position.x - radius);
        float y = (position.y - radius);
        float real_size = SHIP_SIZE;

//        System.out.printf("%f %f %f %f\n", radius, x, y, real_size);
//        batch.draw(texture, x, y, real_size, real_size);
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
        body().setTransform(body().getPosition(), direction.angleRad());
    }
}
