package asteroids.common.services;

import asteroids.common.data.GameData;
import asteroids.common.data.World;

public interface IGamePluginService {

    /**
     * This will be called when the plugin is initialized
     * @param gameData
     * @param world
     */
    void start(GameData gameData, World world);

    /**
     * This will be called when the plugin is unloaded or the game stops.
     * @param gameData
     * @param world
     */
    void stop(GameData gameData, World world);
}
