package asteroids.bulletsystem;

import asteroids.common.components.Collider;
import asteroids.common.components.Graphics;
import asteroids.common.components.Life;
import asteroids.common.components.Position;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Bullet;
import asteroids.common.entities.Entity;
import asteroids.common.math.Vector2;
import asteroids.common.services.IBulletFactory;
import asteroids.common.services.IProcessing;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IProcessing, IBulletFactory {
    private static final int BULLET_RADIUS = 3;
    private static final int BULLET_SEGMENTS = 12;
    private static final float BULLET_SPEED = 15;

    @Override
    public void spawnBullet(World world, Vector2 pos, float radians) {
        Entity bullet = new Bullet();

        bullet.add(new Position((float) (pos.x + Math.cos(radians) * 7), (float) (pos.y + Math.sin(radians) * 7), radians));
        bullet.add(new Graphics());
        bullet.add(new Collider(BULLET_RADIUS));

        world.addEntity(bullet);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Move bullet forward
            Position bulletPos = bullet.getComponent(Position.class);

            bulletPos.setX((float) (bulletPos.getX() + Math.cos(bulletPos.getRadians()) * BULLET_SPEED));
            bulletPos.setY((float) (bulletPos.getY() + Math.sin(bulletPos.getRadians()) * BULLET_SPEED));

            // Render bullet
            renderBullet(bullet);

            // If bullet is outside the scene remove it
            if (bulletPos.getX() < 0 || bulletPos.getX() > gameData.getDisplayWidth() || bulletPos.getY() < 0 || bulletPos.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }

            // If the bullet has collided with an entity that has a Life component, mark it as hit
            Collider bulletCollider = bullet.getComponent(Collider.class);
            if (bulletCollider.getCollisionSet().size() != 0) {
                for (Entity collision :  bulletCollider.getCollisionSet()) {
                    if (collision.getComponent(Life.class) != null) {
                        collision.getComponent(Life.class).setIsHit(true);
                    }
                }

                // Bullet is destroyed if it collides with something
                world.removeEntity(bullet);
            }
        }
    }

    private void renderBullet(Entity bullet) {
        Position bulletPos = bullet.getComponent(Position.class);
        Graphics graphics = bullet.getComponent(Graphics.class);
        List<Vector2> shape = new ArrayList<>(BULLET_SEGMENTS);

        for (int segment = 0; segment < BULLET_SEGMENTS; segment++) {
            float radians = (float) (segment * (2 * Math.PI / BULLET_SEGMENTS));
            var x = (float) (bulletPos.getX() + Math.cos(radians) * BULLET_RADIUS);
            var y = (float) (bulletPos.getY() + Math.sin(radians) * BULLET_RADIUS);
            shape.add(new Vector2(x, y));
        }

        graphics.setShape(shape);
    }
}
