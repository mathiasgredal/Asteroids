package asteroids.spring;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.services.IProcessing;
import asteroids.common.util.PluginManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("processingService")
public class ProcessingService {
    public void runProcessors(GameData gameData, World world) {
        for (IProcessing entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
    }

    private Collection<? extends IProcessing> getEntityProcessingServices() {
        return PluginManager.locateAll(IProcessing.class);
    }
}
