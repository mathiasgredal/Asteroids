package asteroids.collisionsystem;

import asteroids.common.components.Collider;
import asteroids.common.components.Position;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionSystemTest {

    @Test
    void process() {
        // Set up a world with 2 intersecting entities
        Entity entity1 = new Entity();
        entity1.add(new Position(0, 0, 0));
        entity1.add(new Collider(10));
        Entity entity2 = new Entity();
        entity2.add(new Position(5, 0, 0));
        entity2.add(new Collider(10));

        World world = new World();
        world.addEntity(entity1);
        world.addEntity(entity2);

        // Run the collision method
        CollisionSystem collisionSystem = new CollisionSystem();
        collisionSystem.process(null, world);

        assertFalse(entity1.getComponent(Collider.class).getCollisionSet().contains(entity1));
        assertTrue(entity1.getComponent(Collider.class).getCollisionSet().contains(entity2));

        assertFalse(entity2.getComponent(Collider.class).getCollisionSet().contains(entity2));
        assertTrue(entity2.getComponent(Collider.class).getCollisionSet().contains(entity1));
    }
}