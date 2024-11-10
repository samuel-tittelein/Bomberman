package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;

import java.util.Random;

/**
 * La classe {@link ChaseMovementStrategy} représente une stratégie de mouvement pour un ennemi
 * qui poursuit activement le joueur en ligne droite, mais essaie également d'éviter les obstacles.
 * <p>
 * Si l'ennemi est bloqué par un obstacle, il tente de contourner en prenant une direction
 * perpendiculaire. En cas d'échec répété, il choisit une direction aléatoire pour se débloquer.
 * </p>
 */
public class ChaseMovementStrategy implements MovementStrategy {

    private final Player player;
    private final BombermanGame game;
    private static final double SPEED = 50.0;
    private static final int MAX_BLOCKED_ATTEMPTS = 3;
    private int failedAttempts = 0;
    private final Random random = new Random();

    /**
     * Initialise une nouvelle instance de {@link ChaseMovementStrategy}.
     *
     * @param player Le joueur que l'ennemi doit poursuivre.
     * @param game   Le jeu dans lequel l'ennemi évolue.
     */
    public ChaseMovementStrategy(Player player, BombermanGame game) {
        this.player = player;
        this.game = game;
    }

    /**
     * Calcule le mouvement de l'ennemi en direction du joueur.
     * Si un obstacle est rencontré, il tente une direction alternative.
     *
     * @param enemy L'ennemi dont le mouvement doit être calculé.
     */
    @Override
    public void calculateMovement(Enemy enemy) {
        double horizontalSpeed = 0;
        double verticalSpeed = 0;
        boolean moved;

        // Calcule la distance entre l'ennemi et le joueur
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

        // Si l'ennemi est bloqué, essaie une direction aléatoire après plusieurs échecs
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

    /**
     * Tente une direction alternative lorsque la direction principale est bloquée.
     *
     * @param enemy            L'ennemi qui tente de se déplacer.
     * @param primaryDirection La direction initiale (horizontal ou vertical).
     * @return {@code true} si un mouvement alternatif est possible, sinon {@code false}.
     */
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

    /**
     * Vérifie si une cellule contient un obstacle dans la direction donnée.
     *
     * @param x La position en x de la cellule à vérifier.
     * @param y La position en y de la cellule à vérifier.
     * @return {@code true} si un obstacle est présent, sinon {@code false}.
     */
    private boolean isObstacleInDirection(double x, double y) {
        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            return false;
        }
        Cell targetCell = game.getCellAt((int) x, (int) y);
        return targetCell == null || targetCell.getWall() == null;
    }

    /**
     * Génère une direction aléatoire de +1 ou -1.
     *
     * @return 1 ou -1 pour indiquer la direction.
     */
    private int getRandomDirection() {
        return random.nextBoolean() ? 1 : -1;
    }

    /**
     * Réinitialise le compteur d'échecs lorsque l'ennemi se déplace sans être bloqué.
     */
    private void resetAttempts() {
        failedAttempts = 0;
    }
}
