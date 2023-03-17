import asteroids.common.services.IPlugin;
import asteroids.common.services.IProcessing;

module AsteroidPlugin {
    requires Common;

    provides IProcessing with asteroids.asteroidsystem.AsteroidProcessor;
    provides IPlugin with asteroids.asteroidsystem.AsteroidPlugin;
}