import asteroids.common.services.IProcessing;

module BulletPlugin {
    requires Common;

    provides asteroids.common.services.IBulletFactory with asteroids.bulletsystem.BulletProcessor;
    provides IProcessing with asteroids.bulletsystem.BulletProcessor;
}