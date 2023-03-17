package asteroids.asteroidsystem;

import asteroids.common.components.Collider;
import asteroids.common.components.Graphics;
import asteroids.common.components.Movement;
import asteroids.common.components.Position;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Asteroid;
import asteroids.common.entities.Entity;
import asteroids.common.services.IPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidPlugin implements IPlugin {
    private static final int NUM_ASTEROIDS = 10;
    private static final Random random = new Random();
    private final List<Entity> asteroids = new ArrayList<>();

    public static Entity createAsteroid(float x, float y, AsteroidShape size) {

        float radians = random.nextFloat() * 2 * 3.1415f;
        float speed = random.nextFloat() * 10.0f;

        Entity asteroid = new Asteroid();

        // Add asteroid parts
        asteroid.add(new Graphics());
        asteroid.add(new Position(x, y, radians));
        asteroid.add(new Movement(0, 1000, speed, 0));
        asteroid.add(new AsteroidComponent(size));
        asteroid.add(new Collider(size.getDiameter() / 2));

        asteroid.getComponent(Movement.class).setUp(true);

        return asteroid;
    }

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            float x = random.nextFloat() * gameData.getDisplayWidth();
            float y = random.nextFloat() * gameData.getDisplayHeight();
            Entity asteroid = createAsteroid(x, y, AsteroidShape.LARGE);
            asteroids.add(asteroid);
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class))
            world.removeEntity(asteroid);
    }
}
