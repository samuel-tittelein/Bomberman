package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;

import java.util.Random;

public class ChaseMovementStrategy implements MovementStrategy {

    private final Player player;
    private final BombermanGame game;
    private static final double SPEED = 50.0;
    private static final int MAX_BLOCKED_ATTEMPTS = 3;
    private int failedAttempts = 0;
    private final Random random = new Random();

    public ChaseMovementStrategy(Player player, BombermanGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void calculateMovement(Enemy enemy) {
        double horizontalSpeed = 0;
        double verticalSpeed = 0;
        boolean moved;

        // Calcule les distances vers le joueur
        double deltaX = player.getXPosition() - enemy.getX();
        double deltaY = player.getYPosition() - enemy.getY();

        // Choisit une direction prioritaire pour se rapprocher du joueur
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            horizontalSpeed = Math.signum(deltaX) * SPEED;
            if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY())) {
                moved = true;
                verticalSpeed = 0;
                resetAttempts();
            } else {
                moved = tryAlternativeDirections(enemy, "horizontal");
            }
        } else {
            verticalSpeed = Math.signum(deltaY) * SPEED;
            if (isObstacleInDirection(enemy.getX(), enemy.getY() + verticalSpeed)) {
                moved = true;
                horizontalSpeed = 0;
                resetAttempts();
            } else {
                moved = tryAlternativeDirections(enemy, "vertical");
            }
        }

        // Si l'ennemi est bloqué, essaie une direction aléatoire
        if (!moved) {
            failedAttempts++;
            if (failedAttempts >= MAX_BLOCKED_ATTEMPTS) {
                failedAttempts = 0;
                horizontalSpeed = SPEED * getRandomDirection();
                verticalSpeed = 0;
            }
        }

        enemy.setHorizontalSpeed(horizontalSpeed);
        enemy.setVerticalSpeed(verticalSpeed);
    }

    private boolean tryAlternativeDirections(Enemy enemy, String primaryDirection) {
        double horizontalSpeed = 0;
        double verticalSpeed = 0;

        // Essaye une direction perpendiculaire
        if (primaryDirection.equals("horizontal")) {
            verticalSpeed = SPEED * getRandomDirection();
        } else {
            horizontalSpeed = SPEED * getRandomDirection();
        }

        // Vérifie si la direction perpendiculaire est bloquée ou non
        if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY() + verticalSpeed)) {
            enemy.setHorizontalSpeed(horizontalSpeed);
            enemy.setVerticalSpeed(verticalSpeed);
            return true;
        }
        return false;
    }

    private boolean isObstacleInDirection(double x, double y) {
        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            return false;
        }
        Cell targetCell = game.getCellAt((int) x, (int) y);
        return targetCell == null || targetCell.getWall() == null;
    }

    private int getRandomDirection() {
        return random.nextBoolean() ? 1 : -1;
    }

    private void resetAttempts() {
        failedAttempts = 0;
    }
}
