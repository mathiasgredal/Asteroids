package asteroids.playersystem;

import asteroids.common.components.*;
import asteroids.common.data.GameData;
import asteroids.common.data.GameKeys;
import asteroids.common.data.World;
import asteroids.common.entities.Asteroid;
import asteroids.common.entities.Entity;
import asteroids.common.entities.Player;
import asteroids.common.math.Vector2;
import asteroids.common.services.IBulletFactory;
import asteroids.common.services.IProcessing;
import asteroids.common.util.PluginManager;

import java.util.List;

public class PlayerControlSystem implements IProcessing {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            var position = player.getComponent(Position.class);
            var movement = player.getComponent(Movement.class);
            movement.setLeft(GameKeys.isDown(GameKeys.Keys.LEFT));
            movement.setRight(GameKeys.isDown(GameKeys.Keys.RIGHT));
            movement.setUp(GameKeys.isDown(GameKeys.Keys.UP));

            // Spawn bullet
            if (GameKeys.isPressed(GameKeys.Keys.SPACE)) {
                var bulletFactory = PluginManager.locateAll(IBulletFactory.class);
                if (bulletFactory.size() > 0) {
                    bulletFactory.get(0).spawnBullet(world, new Vector2((float) (position.getX() + Math.cos(position.getRadians()) * 10), (float )(position.getY() + Math.sin(position.getRadians()) * 10)), position.getRadians());
                }
            }

            var graphics = player.getComponent(Graphics.class);
            updateShape(graphics, position);

            // Check collision
            Collider shipCollider = player.getComponent(Collider.class);
            if (shipCollider.getCollisionSet().size() != 0) {
                for (Entity collision : shipCollider.getCollisionSet()) {
                    if (collision instanceof Asteroid) {
                        world.removeEntity(player);
                    }
                }
            }
        }
    }


    private void updateShape(Graphics graphics, Position position) {
        float x = position.getX();
        float y = position.getY();
        float radians = position.getRadians();

        var p1 = new Vector2();
        p1.x = (float) (x + Math.cos(radians) * 8);
        p1.y = (float) (y + Math.sin(radians) * 8);

        var p2 = new Vector2();
        p2.x = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        p2.y = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        var p3 = new Vector2();
        p3.x = (float) (x + Math.cos(radians + 3.1415f) * 5);
        p3.y = (float) (y + Math.sin(radians + 3.1415f) * 5);

        var p4 = new Vector2();
        p4.x = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        p4.y = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        graphics.setShape(List.of(new Vector2[]{p1, p2, p3, p4}));
    }
}
