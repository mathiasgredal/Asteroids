package asteroids.spring;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.services.IPlugin;
import asteroids.common.util.PluginManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("pluginService")
public class PluginService {
    private final List<IPlugin> initializedPlugins = new ArrayList<>();

    public void startPlugins(GameData gameData, World world) {
        for (IPlugin iGamePlugin : getPluginServices()) {
            if (!initializedPlugins.contains(iGamePlugin)) {
                iGamePlugin.start(gameData, world);
                initializedPlugins.add(iGamePlugin);
            }
        }
    }

    private Collection<? extends IPlugin> getPluginServices() {
        return PluginManager.locateAll(IPlugin.class);
    }
}
