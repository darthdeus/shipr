package net.jakuba.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sun.javafx.geom.Vec2d;
import net.jakuba.core.Game;
import net.jakuba.model.SpaceShip;

public class MapInputProcessor implements InputProcessor {
    private final OrthographicCamera camera;
    private final SpaceShip ship;

    public MapInputProcessor(OrthographicCamera camera, SpaceShip ship) {
        this.camera = camera;
        this.ship = ship;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int origScreenX, int origScreenY) {
        float screenX = (float) origScreenX;
        float screenY = Game.WINDOW_HEIGHT - (float) origScreenY;

        Vector3 shipPosition3D = camera.project(new Vector3(ship.getPosition().x, ship.getPosition().y, 0));

        Vector2 direction = new Vector2(screenX, screenY).sub(shipPosition3D.x, shipPosition3D.y);

        ship.rotate(direction);

        System.out.printf("%f %f\t%f %f\t%f %f\n",
                direction.x, direction.y,
                (float)screenX, (float)screenY,
                shipPosition3D.x, shipPosition3D.y);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount > 0) {
            camera.zoom += 0.04;
        } else {
            camera.zoom -= 0.04;
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.01f, 1f);
        return false;
    }
}
