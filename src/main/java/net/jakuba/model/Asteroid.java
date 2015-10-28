package net.jakuba.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Asteroid {
    private final Fixture fixture;
    public final float size;

    public Asteroid(Fixture fixture, float size) {
        this.fixture = fixture;
        this.size = size;
    }

    public static Asteroid createIn(World world, float size, float x, float y) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        final Body body = world.createBody(bodyDef);

        final CircleShape shape = new CircleShape();
        shape.setRadius(size);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.3f;

        final Fixture fixture = body.createFixture(fixtureDef);

        return new Asteroid(fixture, size);
    }

    public void draw(SpriteBatch batch) {
        final Vector2 position = fixture.getBody().getPosition();
    }
}
