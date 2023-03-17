package asteroids.collisionsystem;

import asteroids.common.components.Collider;
import asteroids.common.components.Position;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.common.services.IPostProcessing;

public class CollisionSystem implements IPostProcessing {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntitiesWithComponent(Collider.class)) {
            for (Entity entity2 : world.getEntitiesWithComponent(Collider.class)) {
                if (entity1.equals(entity2))
                    continue;

                // Get collider
                Collider collider1 = entity1.getComponent(Collider.class);
                Collider collider2 = entity2.getComponent(Collider.class);

                // Get position
                Position pos1 = entity1.getComponent(Position.class);
                Position pos2 = entity2.getComponent(Position.class);

                double distance = Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));

                if (collider1.getRadius() + collider2.getRadius() > distance) {
                    // We have a collision
                    collider1.getCollisionSet().add(entity2);
                    collider2.getCollisionSet().add(entity1);
                }
            }
        }
    }
}
