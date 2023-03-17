import asteroids.common.services.ISystemProcessing;
import asteroids.inspector.InspectorProcessor;

module InspectorPlugin {
    requires Common;
    requires imgui.binding;

    provides ISystemProcessing with InspectorProcessor;
}