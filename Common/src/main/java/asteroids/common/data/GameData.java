package asteroids.common.data;

/**
 * A simple data object, containing all the game data, that doesn't fit into the World.
 */
public class GameData {
    private boolean paused = false;
    private float deltaTime;
    private int displayWidth;
    private int displayHeight;

    /**
     * Get the number of milliseconds between frames.
     */
    public float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Update the deltaTime i.e. the time in milliseconds between frames.
     */
    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    /**
     * Get the width of the window in pixels.
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     * Set the width of the window in pixels.
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    /**
     * Get the height of the window in pixels.
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     * Set the height of the window in pixels.
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
