package asteroids.playersystem;

import asteroids.common.components.*;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.common.entities.Player;
import asteroids.common.services.IPlugin;

public class PlayerPlugin implements IPlugin {
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData, world);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (var player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }

    private Entity createPlayerShip(GameData gameData, World world) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2.0f;
        float y = gameData.getDisplayHeight() / 2.0f;
        float radians = 3.1415f / 2;

        Entity playerShip = new Player();
        playerShip.add(new Graphics());
        playerShip.add(new Movement(deacceleration, acceleration, maxSpeed, rotationSpeed));
        playerShip.add(new Position(x, y, radians));
        playerShip.add(new Collider(10));
        playerShip.add(new Life(3));

        return playerShip;
    }
}
