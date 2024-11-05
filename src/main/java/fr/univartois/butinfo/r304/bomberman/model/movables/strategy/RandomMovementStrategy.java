package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();
    private final int changeDirectionProbability;

    /**
     * Crée une nouvelle instance de RandomMovementStrategy avec une probabilité de changement de direction.
     *
     * @param changeDirectionProbability La probabilité (en pourcentage) de changer de direction à chaque mouvement.
     */
    public RandomMovementStrategy(int changeDirectionProbability) {
        this.changeDirectionProbability = changeDirectionProbability;
    }

    @Override
    public void calculateMovement(Enemy enemy) {
        // Change la direction avec une probabilité donnée
        if (RANDOM.nextInt(100) < changeDirectionProbability) {
            enemy.setHorizontalSpeed(getRandomSpeed());
            enemy.setVerticalSpeed(getRandomSpeed());
        }
    }

    private double getRandomSpeed() {
        double speed;
        do {
            speed = -100 + (200) * RANDOM.nextDouble();
        } while (speed > -10 && speed < 10); // Évite des vitesses trop faibles
        return speed;
    }
}
