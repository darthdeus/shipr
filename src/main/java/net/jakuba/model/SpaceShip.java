package net.jakuba.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class SpaceShip extends com.badlogic.gdx.math.Circle {
    private Texture texture = new Texture("spaceship.png");
    private Fixture fixture;

    public static final int SHIP_SIZE = 64;

    public void applyForce(float x, float y) {
        applyForce(new Vector2(x, y));
    }

    public void applyForce(Vector2 direction) {
        final Body body = fixture.getBody();
//        body.applyLinearImpulse(direction, body.getPosition(), true);
        body.applyForceToCenter(direction, true);
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

        return body;
    }

    public void draw(SpriteBatch batch) {
        final Vector2 position = fixture.getBody().getPosition();

        final int radius = SHIP_SIZE / 2;
        batch.draw(texture, position.x - radius, position.y - radius, SHIP_SIZE, SHIP_SIZE);
    }
}
