package asteroids.common.services;

import asteroids.common.data.GameData;
import asteroids.common.data.World;

/**
 * This is the interface for the processor, and will be called on every frame the game is running.
 */
public interface IProcessing {
    void process(GameData gameData, World world);
}
