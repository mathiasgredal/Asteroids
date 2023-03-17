package asteroids.common.processors;

import asteroids.common.components.Life;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.common.services.IProcessing;

public class LifeProcessor implements IProcessing {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntitiesWithComponents(Life.class)) {
            Life life = entity.getComponent(Life.class);
            if (life.isHit()) {
                life.setLife(life.getLife() - 1);
                life.setIsHit(false);
            }

            if (life.getLife() <= 0) {
                life.setDead(true);
            }

            if (life.isDead()) {
                world.removeEntity(entity);
            }
        }
    }
}
