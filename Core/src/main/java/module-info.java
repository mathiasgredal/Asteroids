import asteroids.common.services.IPlugin;
import asteroids.common.services.IPostProcessing;
import asteroids.common.services.IProcessing;

module Core {
    requires Common;

    uses IPlugin;
    uses IProcessing;
    uses IPostProcessing;
    uses asteroids.common.services.IBulletFactory;

    requires spring.context;
    exports asteroids.spring to spring.beans;

    requires imgui.lwjgl3;
    requires imgui.binding;

    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.nanovg;
    requires org.lwjgl.bgfx;

    requires org.lwjgl.natives;
    requires org.lwjgl.glfw.natives;
    requires org.lwjgl.bgfx.natives;
    requires org.lwjgl.nanovg.natives;
}