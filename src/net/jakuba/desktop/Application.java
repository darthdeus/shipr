package net.jakuba.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.jakuba.core.Game;

public class Application {
    public static void main(String[] args) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Shipr";
        config.width = Game.WINDOW_WIDTH;
        config.height = Game.WINDOW_HEIGHT;

        new LwjglApplication(new Game(), config);
    }
}
