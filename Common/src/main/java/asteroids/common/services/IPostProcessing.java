package asteroids.common.services;

import asteroids.common.data.GameData;
import asteroids.common.data.World;

/**
 * This is like the IProcessing, but will be called when all the normal processors have been run.
 */
public interface IPostProcessing {
    void process(GameData gameData, World world);
}
