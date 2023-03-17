package asteroids.common.components;

import asteroids.common.entities.Entity;

import java.util.HashSet;
import java.util.Set;

public class Collider extends Component {


    private final Set<Entity> collisionSet = new HashSet<>();
    private float radius;

    public Collider(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Set<Entity> getCollisionSet() {
        return collisionSet;
    }
}
