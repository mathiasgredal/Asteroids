import asteroids.common.services.IPostProcessing;

module CollisionPlugin {
    requires Common;
    provides IPostProcessing with asteroids.collisionsystem.CollisionSystem;
}