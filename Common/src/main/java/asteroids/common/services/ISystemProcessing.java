package asteroids.common.services;

import asteroids.common.data.GameData;
import asteroids.common.data.World;

/**
 * Like the normal IProcessing, but also runs when the game is paused
 */
public interface ISystemProcessing {
    void process(GameData gameData, World world);
}
