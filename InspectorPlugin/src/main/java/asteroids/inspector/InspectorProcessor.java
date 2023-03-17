package asteroids.inspector;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.common.services.IPlugin;
import asteroids.common.services.ISystemProcessing;
import asteroids.common.util.Plugin;
import asteroids.common.util.PluginManager;
import imgui.ImDrawList;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InspectorProcessor implements ISystemProcessing {
    private Entity selectedEntity = null;

    @Override
    public void process(GameData gameData, World world) {

        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(gameData.getDisplayWidth() * 0.3f, gameData.getDisplayHeight());
        ImGui.setNextWindowCollapsed(true, ImGuiCond.FirstUseEver);

        ImGui.begin("Debug Menu", ImGuiWindowFlags.NoResize);

        if (ImGui.collapsingHeader("Inspector")) {
            if (selectedEntity == null) {
                ImGui.text("No entity selected.");
            } else {
                ImGui.text("ID: " + selectedEntity.getID());

                if (ImGui.treeNode("Components")) {
                    for (var component : selectedEntity.getComponents()) {
                        if (ImGui.treeNode(component.getClass().getSimpleName() + "##" + component)) {
                            for (var field : component.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                try {
                                    ImGui.text(field.getName() + ": " + field.get(component));
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            ImGui.treePop();
                        }
                    }
                    ImGui.treePop();
                }
            }
        }

        if (ImGui.collapsingHeader("World")) {
            if (ImGui.beginListBox("## entities", Float.MIN_VALUE, 5 * ImGui.getTextLineHeightWithSpacing())) {
                Map<Class<? extends Entity>, List<Entity>> entitiesGrouped =
                        world.getEntities().stream().collect(Collectors.groupingBy(Entity::getClass));

                for (var entityType : entitiesGrouped.keySet()) {
                    if (ImGui.treeNode(entityType.getSimpleName() + " ## " + entityType.getName())) {
                        for (var entity : entitiesGrouped.get(entityType)) {
                            boolean isSelected = selectedEntity != null && selectedEntity.equals(entity);

                            if (ImGui.selectable(entity.getID(), isSelected)) {
                                selectedEntity = entity;
                            }

                            if (isSelected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                        ImGui.treePop();
                    }
                }
                ImGui.endListBox();
            }
        }

        if (ImGui.collapsingHeader("Plugins")) {
            if (ImGui.beginTable("plugins_table", 1, ImGuiTableFlags.Borders + ImGuiTableFlags.Resizable)) {
                for (Map.Entry<URL, Plugin> plugin : PluginManager.getPlugins().entrySet()) {
                    ImGui.tableNextRow();
                    ImGui.tableSetColumnIndex(0);
                    ImGui.text(Paths.get(plugin.getKey().getFile()).getFileName().toString());
                }
            }
            ImGui.endTable();

            if (ImGui.button("Update")) {
                var pluginsToBeUnloaded = PluginManager.updatePluginLayers();

                for (var pluginURL : pluginsToBeUnloaded) {
                    PluginManager.getPlugins().get(pluginURL).getServices(IPlugin.class).forEach(s -> {
                        s.stop(gameData, world);
                    });
                }

                PluginManager.reloadPlugins();
            }
        }

        ImDrawList drawList = ImGui.getWindowDrawList();

        drawList.addRectFilled(ImGui.getCursorScreenPosX() + 4, ImGui.getCursorScreenPosY() + 4, ImGui.getCursorScreenPosX() + 8, ImGui.getCursorScreenPosY() + 16, ImGui.getColorU32(gameData.isPaused() ? ImGuiCol.TextDisabled : ImGuiCol.Text));
        drawList.addRectFilled(ImGui.getCursorScreenPosX() + 12, ImGui.getCursorScreenPosY() + 4, ImGui.getCursorScreenPosX() + 16, ImGui.getCursorScreenPosY() + 16, ImGui.getColorU32(gameData.isPaused() ? ImGuiCol.TextDisabled : ImGuiCol.Text));

        ImGui.beginDisabled(gameData.isPaused());
        if (ImGui.button("## pause", 20, 20)) {
            gameData.setPaused(true);
        }
        ImGui.endDisabled();

        ImGui.sameLine();

        drawList.addTriangleFilled(ImGui.getCursorScreenPosX() + 4, ImGui.getCursorScreenPosY() + 4, ImGui.getCursorScreenPosX() + 16, ImGui.getCursorScreenPosY() + 10, ImGui.getCursorScreenPosX() + 4, ImGui.getCursorScreenPosY() + 16, ImGui.getColorU32(gameData.isPaused() ? ImGuiCol.Text : ImGuiCol.TextDisabled));

        ImGui.beginDisabled(!gameData.isPaused());
        if (ImGui.button("## play", 20, 20)) {
            gameData.setPaused(false);
        }
        ImGui.endDisabled();


        ImGui.text("FPS: " + (int) (1 / gameData.getDeltaTime()));

        ImGui.end();
    }
}
