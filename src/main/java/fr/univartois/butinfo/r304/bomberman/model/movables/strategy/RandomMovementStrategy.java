package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();
    private final int changeDirectionProbability;
    private final Player player;
    private final boolean targetPlayer;
    private final double speedFactor;
    private final BombermanGame game;

    public RandomMovementStrategy(int changeDirectionProbability, Player player, boolean targetPlayer, double speedFactor, BombermanGame game) {
        this.changeDirectionProbability = changeDirectionProbability;
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.speedFactor = speedFactor;
        this.game = game;
    }

    @Override
    public void calculateMovement(Enemy enemy) {
        double horizontalSpeed = 0;
        double verticalSpeed = 0;

        if (targetPlayer && player != null) {
            // Calcule la direction vers le joueur
            double deltaX = player.getXPosition() - enemy.getX();
            double deltaY = player.getYPosition() - enemy.getY();

            // Priorité sur l'axe le plus éloigné du joueur
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                horizontalSpeed = Math.signum(deltaX) * speedFactor;
                verticalSpeed = 0;
            } else {
                verticalSpeed = Math.signum(deltaY) * speedFactor;
                horizontalSpeed = 0;
            }

            // Si l'ennemi rencontre un obstacle en ligne droite, il cherche à "glisser" le long du mur
            if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY() + verticalSpeed)) {
                if (horizontalSpeed != 0) {
                    // Glissement vertical le long du mur s'il est bloqué horizontalement
                    horizontalSpeed = 0;
                    verticalSpeed = speedFactor * (deltaY != 0 ? Math.signum(deltaY) : getRandomDirection());
                } else if (verticalSpeed != 0) {
                    // Glissement horizontal le long du mur s'il est bloqué verticalement
                    verticalSpeed = 0;
                    horizontalSpeed = speedFactor * (deltaX != 0 ? Math.signum(deltaX) : getRandomDirection());
                }

                // Dernier recours : essaye d'autres directions perpendiculaires si toujours bloqué
                if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY() + verticalSpeed)) {
                    horizontalSpeed = speedFactor * getRandomDirection();
                    verticalSpeed = speedFactor * getRandomDirection();
                }
            }

        } else {
            // Mouvement aléatoire pour les ennemis non agressifs
            if (RANDOM.nextInt(100) < changeDirectionProbability) {
                horizontalSpeed = getRandomSpeed();
                verticalSpeed = getRandomSpeed();
            } else {
                horizontalSpeed = enemy.getHorizontalSpeed();
                verticalSpeed = enemy.getVerticalSpeed();
            }

            // Évite les obstacles pour les ennemis non agressifs
            if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY())) {
                horizontalSpeed = 0;
            }
            if (isObstacleInDirection(enemy.getX(), enemy.getY() + verticalSpeed)) {
                verticalSpeed = 0;
            }
        }

        // Applique les vitesses ajustées pour éviter les obstacles
        enemy.setHorizontalSpeed(horizontalSpeed);
        enemy.setVerticalSpeed(verticalSpeed);
    }

    private boolean isObstacleInDirection(double x, double y) {
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

    private int getRandomDirection() {
        return RANDOM.nextBoolean() ? 1 : -1;
    }
}
