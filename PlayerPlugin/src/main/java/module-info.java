import asteroids.common.services.IPlugin;
import asteroids.common.services.IProcessing;

module PlayerPlugin {
    requires Common;
    provides IPlugin with asteroids.playersystem.PlayerPlugin;
    provides IProcessing with asteroids.playersystem.PlayerControlSystem;
}