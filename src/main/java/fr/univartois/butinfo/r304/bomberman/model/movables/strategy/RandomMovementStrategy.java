package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;

import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();
    private final int changeDirectionProbability;
    private final BombermanGame game;

    public RandomMovementStrategy(int changeDirectionProbability, BombermanGame game) {
        this.changeDirectionProbability = changeDirectionProbability;
        this.game = game;
    }

    @Override
    public void calculateMovement(Enemy enemy) {
        double horizontalSpeed;
        double verticalSpeed;

        if (RANDOM.nextInt(100) < changeDirectionProbability) {
            horizontalSpeed = getRandomSpeed();
            verticalSpeed = getRandomSpeed();
        } else {
            horizontalSpeed = enemy.getHorizontalSpeed();
            verticalSpeed = enemy.getVerticalSpeed();
        }

        if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY())) {
            horizontalSpeed = 0;
        }
        if (isObstacleInDirection(enemy.getX(), enemy.getY() + verticalSpeed)) {
            verticalSpeed = 0;
        }

        enemy.setHorizontalSpeed(horizontalSpeed);
        enemy.setVerticalSpeed(verticalSpeed);
    }

    private boolean isObstacleInDirection(double x, double y) {
        // Vérifie si les coordonnées x et y sont dans les limites de la carte
        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            return true; // Considère toute position hors limites comme un obstacle
        }

        // Récupère la cellule cible et vérifie s'il y a un mur
        Cell targetCell = game.getCellAt((int) x, (int) y);
        return targetCell != null && targetCell.getWall() != null;
    }

    private double getRandomSpeed() {
        double speed;
        do {
            speed = -100 + (200) * RANDOM.nextDouble();
        } while (speed > -10 && speed < 10); // Évite les vitesses trop faibles
        return speed;
    }
}
