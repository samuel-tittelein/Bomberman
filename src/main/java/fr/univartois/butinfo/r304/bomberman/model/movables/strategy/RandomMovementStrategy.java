package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();

    @Override
    public void calculateMovement(Enemy enemy) {
        // 50% de chance de changer la vitesse horizontale
        if (RANDOM.nextBoolean()) {
            enemy.setHorizontalSpeed(getRandomSpeed());
        }
        // 50% de chance de changer la vitesse verticale
        if (RANDOM.nextBoolean()) {
            enemy.setVerticalSpeed(getRandomSpeed());
        }
    }

    private double getRandomSpeed() {
        double speed;
        do {
            speed = -100 + (200) * RANDOM.nextDouble();
        } while (speed > -10 && speed < 10); // Évite des vitesses trop lentes
        return speed;
    }
}
