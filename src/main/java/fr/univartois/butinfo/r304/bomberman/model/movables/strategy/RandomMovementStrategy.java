package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;

import java.util.Random;

/**
 * La classe {@link RandomMovementStrategy} représente une stratégie de mouvement aléatoire pour un ennemi.
 * Elle permet à l'ennemi de se déplacer de manière imprévisible, en changeant de direction
 * avec une certaine probabilité.
 */
public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();
    private final int changeDirectionProbability;
    private final BombermanGame game;

    /**
     * Construit une nouvelle instance de {@link RandomMovementStrategy}.
     *
     * @param changeDirectionProbability La probabilité (en pourcentage) que l'ennemi change de direction.
     * @param game                       Le jeu dans lequel l'ennemi évolue.
     */
    public RandomMovementStrategy(int changeDirectionProbability, BombermanGame game) {
        this.changeDirectionProbability = changeDirectionProbability;
        this.game = game;
    }

    /**
     * Calcule le mouvement de l'ennemi selon un modèle aléatoire.
     * L'ennemi a une chance de changer de direction, et ajuste son mouvement pour éviter
     * les obstacles détectés.
     *
     * @param enemy L'ennemi dont le mouvement doit être calculé.
     */
    @Override
    public void calculateMovement(Enemy enemy) {
        double horizontalSpeed;
        double verticalSpeed;

        // Détermine la nouvelle vitesse de déplacement selon la probabilité
        if (RANDOM.nextInt(100) < changeDirectionProbability) {
            horizontalSpeed = getRandomSpeed();
            verticalSpeed = getRandomSpeed();
        } else {
            horizontalSpeed = enemy.getHorizontalSpeed();
            verticalSpeed = enemy.getVerticalSpeed();
        }

        // Ajuste les vitesses pour éviter les obstacles
        if (isObstacleInDirection(enemy.getX() + horizontalSpeed, enemy.getY())) {
            horizontalSpeed = 0;
        }
        if (isObstacleInDirection(enemy.getX(), enemy.getY() + verticalSpeed)) {
            verticalSpeed = 0;
        }

        enemy.setHorizontalSpeed(horizontalSpeed);
        enemy.setVerticalSpeed(verticalSpeed);
    }

    /**
     * Vérifie s'il y a un obstacle dans la direction spécifiée.
     *
     * @param x La position en x à vérifier.
     * @param y La position en y à vérifier.
     * @return {@code true} si un obstacle est présent, sinon {@code false}.
     */
    private boolean isObstacleInDirection(double x, double y) {
        // Vérifie que les coordonnées sont dans les limites de la carte
        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            return true; // Considère toute position hors limites comme un obstacle
        }

        // Récupère la cellule cible et vérifie s'il y a un mur
        Cell targetCell = game.getCellAt((int) x, (int) y);
        return targetCell != null && targetCell.getWall() != null;
    }

    /**
     * Génère une vitesse aléatoire pour l'ennemi.
     * Cette vitesse est comprise entre -100 et 100, avec une exclusion des petites valeurs proches de 0.
     *
     * @return Une valeur de vitesse aléatoire.
     */
    private double getRandomSpeed() {
        double speed;
        do {
            speed = -100 + (200) * RANDOM.nextDouble();
        } while (speed > -10 && speed < 10); // Évite les vitesses trop faibles pour des mouvements visibles
        return speed;
    }
}
