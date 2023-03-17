package asteroids.asteroidsystem;

import asteroids.common.components.Collider;
import asteroids.common.data.World;
import asteroids.common.entities.Asteroid;
import asteroids.common.entities.Bullet;
import asteroids.common.entities.Entity;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AsteroidSplitterTest {

    Entity createMockedAsteroid(World world, AsteroidShape asteroidShape) {
        // We want a world with an asteroid
        Entity asteroid = AsteroidPlugin.createAsteroid(100, 100, asteroidShape);
        world.addEntity(asteroid);

        // We want to mock the collider, such that the collision set contains a bullet
        Collider mockedCollider = mock(Collider.class);
        Set<Entity> mockedCollisionSet = new HashSet<>(List.of(
                new Bullet()
        ));
        when(mockedCollider.getCollisionSet()).thenReturn(mockedCollisionSet);

        asteroid.remove(Collider.class);
        asteroid.add(mockedCollider);

        return asteroid;
    }

    @Test
    void asteroidSplit() {
        // We want a world with an asteroid
        World world = new World();
        Entity asteroid = createMockedAsteroid(world, AsteroidShape.LARGE);

        // We want to verify that after running the processor the original asteroid is deleted and 2 new have been spawned
        assertEquals(1, world.getEntities().size());
        assertNotNull(world.getEntity(asteroid.getID()));

        AsteroidProcessor asteroidProcessor = new AsteroidProcessor();
        asteroidProcessor.process(null, world);

        assertEquals(2, world.getEntities().size());
        assertNull(world.getEntity(asteroid.getID()));

        // Verify that the asteroid size is correct
        assertFalse(world.getEntities(Asteroid.class).stream().allMatch(e -> e.getComponent(AsteroidComponent.class).getSize() == AsteroidShape.LARGE));
        assertTrue(world.getEntities(Asteroid.class).stream().allMatch(e -> e.getComponent(AsteroidComponent.class).getSize() == AsteroidShape.MEDIUM));
        assertFalse(world.getEntities(Asteroid.class).stream().allMatch(e -> e.getComponent(AsteroidComponent.class).getSize() == AsteroidShape.SMALL));
    }

    @Test
    void asteroidDestroy() {
        // We want a world with an asteroid
        World world = new World();
        Entity asteroid = createMockedAsteroid(world, AsteroidShape.SMALL);

        // We want to verify that the asteroid was exists before and that the asteroid is removed after
        assertEquals(1, world.getEntities().size());
        assertNotNull(world.getEntity(asteroid.getID()));

        AsteroidProcessor asteroidProcessor = new AsteroidProcessor();
        asteroidProcessor.process(null, world);

        assertEquals(0, world.getEntities().size());
        assertNull(world.getEntity(asteroid.getID()));
    }
}
