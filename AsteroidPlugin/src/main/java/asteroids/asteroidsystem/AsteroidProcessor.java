package asteroids.asteroidsystem;

import asteroids.common.components.Collider;
import asteroids.common.components.Graphics;
import asteroids.common.components.Position;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Bullet;
import asteroids.common.entities.Entity;
import asteroids.common.math.Vector2;
import asteroids.common.services.IProcessing;

import java.util.ArrayList;
import java.util.List;

public class AsteroidProcessor implements IProcessing {
    @Override
    public void process(GameData gameData, World world) {
        splitter(gameData, world);
        render(gameData, world);
    }

    private void splitter(GameData gameData, World world) {
        for (Entity asteroid : world.getEntitiesWithComponents(AsteroidComponent.class, Collider.class, Position.class)) {
            Collider collider = asteroid.getComponent(Collider.class);

            for (Entity other : collider.getCollisionSet()) {
                if (other.getClass().equals(Bullet.class)) {
                    world.removeEntity(other);
                    splitAsteroid(world, asteroid);
                    world.removeEntity(asteroid);
                }
            }
        }
    }

    private void render(GameData gameData, World world) {
        for (Entity asteroid : world.getEntitiesWithComponents(AsteroidComponent.class, Graphics.class, Collider.class, Position.class)) {
            AsteroidComponent asteroidComponent = asteroid.getComponent(AsteroidComponent.class);
            Graphics graphics = asteroid.getComponent(Graphics.class);
            Collider collider = asteroid.getComponent(Collider.class);
            Position position = asteroid.getComponent(Position.class);

            collider.setRadius(asteroidComponent.getSize().getDiameter());

            var segments = asteroidComponent.getSize().getSegments();
            List<Vector2> shape = new ArrayList<>();
            // Transform segments to the current position and rotation
            for (Vector2 segment : segments) {
                var updatedSegment = new Vector2(position.getX() + segment.x, position.getY() + segment.y);
                shape.add(updatedSegment);
            }

            // Apply the new shape
            graphics.setShape(shape);
        }
    }

    private void splitAsteroid(World world, Entity asteroid) {
        Position pos = asteroid.getComponent(Position.class);
        AsteroidComponent asteroidSize = asteroid.getComponent(AsteroidComponent.class);
        AsteroidShape newSize = null;

        switch (asteroidSize.getSize()) {
            case LARGE:
                newSize = AsteroidShape.MEDIUM;
                break;
            case MEDIUM:
                newSize = AsteroidShape.SMALL;
                break;
            case SMALL:
                break;
        }

        if (newSize != null) {
            var asteroid1 = AsteroidPlugin.createAsteroid(pos.getX(), pos.getY(), newSize);
            var asteroid2 = AsteroidPlugin.createAsteroid(pos.getX(), pos.getY(), newSize);
            world.addEntity(asteroid1);
            world.addEntity(asteroid2);
        }

        world.removeEntity(asteroid);
    }
}
