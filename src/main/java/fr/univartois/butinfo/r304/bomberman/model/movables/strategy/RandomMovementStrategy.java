package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Random RANDOM = new Random();
    private final int changeDirectionProbability;
    private final Player player; // Référence vers le joueur
    private final boolean targetPlayer;
    private final double speedFactor;

    /**
     * Crée une nouvelle instance de RandomMovementStrategy.
     *
     * @param changeDirectionProbability La probabilité (en pourcentage) de changer de direction aléatoirement.
     * @param player La cible (le joueur) que l'ennemi peut décider de poursuivre.
     * @param targetPlayer Indique si l'ennemi doit tenter de poursuivre le joueur.
     * @param speedFactor La vitesse de déplacement (utilisée en cas de poursuite).
     */
    public RandomMovementStrategy(int changeDirectionProbability, Player player, boolean targetPlayer, double speedFactor) {
        this.changeDirectionProbability = changeDirectionProbability;
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.speedFactor = speedFactor;
    }

    @Override
    public void calculateMovement(Enemy enemy) {
        if (targetPlayer && player != null) {
            // Calcul des distances en x et y entre l'ennemi et le joueur
            double deltaX = player.getXPosition() - enemy.getX();
            double deltaY = player.getYPosition() - enemy.getY();

            // Ajuste les vitesses pour se rapprocher du joueur
            enemy.setHorizontalSpeed(Math.signum(deltaX) * speedFactor);
            enemy.setVerticalSpeed(Math.signum(deltaY) * speedFactor);

        } else {
            // Mouvement aléatoire si on ne cible pas le joueur
            if (RANDOM.nextInt(100) < changeDirectionProbability) {
                enemy.setHorizontalSpeed(getRandomSpeed());
                enemy.setVerticalSpeed(getRandomSpeed());
            }
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
