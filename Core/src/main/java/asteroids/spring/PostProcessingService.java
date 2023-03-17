package asteroids.spring;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.services.IPostProcessing;
import asteroids.common.util.PluginManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("postProcessingService")
public class PostProcessingService {
    public void runPostProcessors(GameData gameData, World world) {
        for (IPostProcessing postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private Collection<? extends IPostProcessing> getPostEntityProcessingServices() {
        return PluginManager.locateAll(IPostProcessing.class);
    }
}
