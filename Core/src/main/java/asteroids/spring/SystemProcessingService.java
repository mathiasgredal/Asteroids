package asteroids.spring;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.services.ISystemProcessing;
import asteroids.common.util.PluginManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("systemProcessingService")
public class SystemProcessingService {
    public void runSystemProcessors(GameData gameData, World world) {
        for (ISystemProcessing systemProcessorService : getSystemProcessingServices()) {
            systemProcessorService.process(gameData, world);
        }
    }

    private Collection<? extends ISystemProcessing> getSystemProcessingServices() {
        return PluginManager.locateAll(ISystemProcessing.class);
    }
}
