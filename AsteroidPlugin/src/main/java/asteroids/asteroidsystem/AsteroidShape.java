package asteroids.asteroidsystem;

import asteroids.common.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum AsteroidShape {
    SMALL(12, 7),
    MEDIUM(18, 9),
    LARGE(28, 12);

    private final float diameter;
    private final List<Vector2> segments;

    AsteroidShape(float diameter, int segments) {
        this.diameter = diameter;
        this.segments = new ArrayList<>(segments);

        // Compute all the segments from the diameter
        for (int i = 0; i < segments; i++) {
            Random random = new Random();
            var x = (float) (Math.cos(2 * Math.PI / segments * i) * (random.nextFloat() * diameter + diameter / 4.f));
            var y = (float) (Math.sin(2 * Math.PI / segments * i) * (random.nextFloat() * diameter + diameter / 4.f));
            this.segments.add(new Vector2(x, y));
        }
    }

    public List<Vector2> getSegments() {
        return new ArrayList<>(segments);
    }

    public float getDiameter() {
        return diameter;
    }
}
