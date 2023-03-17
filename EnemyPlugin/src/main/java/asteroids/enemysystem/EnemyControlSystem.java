package asteroids.enemysystem;

import asteroids.common.components.Graphics;
import asteroids.common.components.Movement;
import asteroids.common.components.Position;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Enemy;
import asteroids.common.entities.Entity;
import asteroids.common.math.Vector2;
import asteroids.common.services.IBulletFactory;
import asteroids.common.services.IProcessing;
import asteroids.common.util.PluginManager;

import java.util.List;
import java.util.Random;

public class EnemyControlSystem implements IProcessing {
    private final Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            var position = enemy.getComponent(Position.class);
            var movement = enemy.getComponent(Movement.class);
            movement.setLeft(random.nextBoolean());
            movement.setRight(random.nextBoolean());
            movement.setUp(random.nextBoolean());

            // Spawn bullet
            if (random.nextFloat() > 0.98f) {
                var bulletFactory = PluginManager.locateAll(IBulletFactory.class);
                if (bulletFactory.size() > 0) {
                    bulletFactory.get(0).spawnBullet(world, new Vector2((float) (position.getX() + Math.cos(position.getRadians()) * 10), (float )(position.getY() + Math.sin(position.getRadians()) * 10)), position.getRadians());
                }
            }

            var graphics = enemy.getComponent(Graphics.class);
            updateShape(graphics, position);
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
