import asteroids.common.services.IPlugin;
import asteroids.common.services.IProcessing;
import asteroids.enemysystem.EnemyControlSystem;
import asteroids.enemysystem.EnemyPlugin;

module EnemyPlugin {
    requires Common;
    provides IPlugin with EnemyPlugin;
    provides IProcessing with EnemyControlSystem;
}