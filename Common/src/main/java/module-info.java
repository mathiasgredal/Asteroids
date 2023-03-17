import asteroids.common.services.IPlugin;
import asteroids.common.services.IPostProcessing;
import asteroids.common.services.IProcessing;
import asteroids.common.services.ISystemProcessing;

module Common {
    exports asteroids.common.data;
    exports asteroids.common.components;
    exports asteroids.common.entities;
    exports asteroids.common.util;
    exports asteroids.common.math;
    exports asteroids.common.services;
    
    opens asteroids.common.components;

    // Define all the service provider interfaces
    uses IPlugin;
    uses ISystemProcessing;
    uses IProcessing;
    uses IPostProcessing;
    uses asteroids.common.services.IBulletFactory;

    // Register built-in services
    provides IProcessing with asteroids.common.processors.MovementProcessor, asteroids.common.processors.LifeProcessor;

    // Dependencies
    requires imgui.binding;
}