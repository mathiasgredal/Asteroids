package asteroids.common.services;

import asteroids.common.data.World;
import asteroids.common.math.Vector2;

/**
 * This is called when someone wants to spawn a bullet in the world
 */
public interface IBulletFactory {
    void spawnBullet(World world, Vector2 pos, float radians);
}
