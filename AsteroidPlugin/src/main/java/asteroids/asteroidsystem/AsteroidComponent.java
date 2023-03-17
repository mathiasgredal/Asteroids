package asteroids.asteroidsystem;

import asteroids.common.components.Component;

public class AsteroidComponent extends Component {
    private final AsteroidShape size;

    public AsteroidComponent(AsteroidShape size) {
        this.size = size;
    }

    public AsteroidShape getSize() {
        return size;
    }
}
