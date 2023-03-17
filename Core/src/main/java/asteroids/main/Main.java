package asteroids.main;

import asteroids.util.Configuration;


class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setWidth(800);
        configuration.setHeight(600);
        configuration.setTitle("Asteroids");
        configuration.setFullScreen(false);
        configuration.setVsync(true);

        new AsteroidsGame(configuration).start();
    }
}
