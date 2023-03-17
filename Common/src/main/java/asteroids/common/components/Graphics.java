package asteroids.common.components;

import asteroids.common.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Graphics extends Component {
    public List<Vector2> shape = new ArrayList<>();

    public List<Vector2> getShape() {
        return this.shape;
    }

    public void setShape(List<Vector2> shape) {
        this.shape = shape;
    }
}
