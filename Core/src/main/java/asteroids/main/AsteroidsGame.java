package asteroids.main;

import asteroids.common.components.Graphics;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.spring.PluginService;
import asteroids.spring.PostProcessingService;
import asteroids.spring.ProcessingService;
import asteroids.spring.SystemProcessingService;
import asteroids.util.Configuration;
import asteroids.util.Window;
import imgui.internal.ImGui;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static asteroids.util.Color.rgba;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.nanovg.NanoVG.*;

public class AsteroidsGame {
    private final Window window;
    private final long nvgContext;
    private final GameData gameData = new GameData();
    private final World world = new World();
    private AnnotationConfigApplicationContext services;

    public AsteroidsGame(Configuration config) {
        this.window = new Window(config, this::run);
        this.nvgContext = this.window.getNvgContext();
    }

    public void start() {
        // Load spring services
        this.services = new AnnotationConfigApplicationContext();
        this.services.scan("asteroids.spring");
        this.services.refresh();

        // Capture window size
        int[] width = new int[1];
        int[] height = new int[1];

        glfwGetWindowSize(window.getHandle(), width, height);

        gameData.setDisplayWidth(width[0]);
        gameData.setDisplayHeight(height[0]);

        // Start run loop
        this.window.run();
    }

    private void run(Window window) {
        gameData.setDeltaTime(ImGui.getIO().getDeltaTime());

        // System processors run even when the game is paused
        ((SystemProcessingService) services.getBean("systemProcessingService")).runSystemProcessors(gameData, world);

        update();

        draw();
    }

    private void update() {
        if (gameData.isPaused())
            return;

        // Use spring to load plugins and run the processors
        ((PluginService) services.getBean("pluginService")).startPlugins(gameData, world);
        ((ProcessingService) services.getBean("processingService")).runProcessors(gameData, world);
        ((PostProcessingService) services.getBean("postProcessingService")).runPostProcessors(gameData, world);
    }

    private void draw() {
        for (Entity entity : world.getEntitiesWithComponent(Graphics.class)) {
            var graphics = entity.getComponent(Graphics.class);

            if (graphics == null || graphics.shape.size() == 0) {
                continue;
            }

            nvgBeginPath(nvgContext);
            nvgMoveTo(nvgContext, graphics.shape.get(0).x, graphics.shape.get(0).y);
            for (int i = 1; i < graphics.shape.size(); i++) {
                nvgLineTo(nvgContext, graphics.shape.get(i).x, graphics.shape.get(i).y);
            }
            nvgLineTo(nvgContext, graphics.shape.get(0).x, graphics.shape.get(0).y);
            nvgStrokeColor(nvgContext, rgba(1, 1, 1, 1));
            nvgStroke(nvgContext);
        }
    }
}
