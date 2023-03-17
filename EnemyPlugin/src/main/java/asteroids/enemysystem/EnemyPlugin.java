package asteroids.enemysystem;

import asteroids.common.components.*;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Enemy;
import asteroids.common.entities.Entity;
import asteroids.common.services.IPlugin;

public class EnemyPlugin implements IPlugin {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData, world);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (var enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

    private Entity createEnemyShip(GameData gameData, World world) {
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2.0f;
        float y = gameData.getDisplayHeight() / 2.0f;
        float radians = 3.1415f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.add(new Graphics());
        enemyShip.add(new Movement(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new Position(x, y, radians));
        enemyShip.add(new Collider(10));
        enemyShip.add(new Life(3));

        return enemyShip;
    }
}
