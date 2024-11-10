package fr.univartois.butinfo.r304.bomberman.model.movables.state;

import fr.univartois.butinfo.r304.bomberman.model.movables.Player;

/**
 * La classe {@link VulnerableState} représente l'état de vulnérabilité du joueur.
 * Dans cet état, le joueur peut subir des dommages, ce qui réduit ses points de vie
 * et le rend temporairement invulnérable.
 */
public class VulnerableState implements PlayerState {
    private static final int INVULNERABILITY_DURATION = 3000; // Durée de l'invulnérabilité en ms

    @Override
    public void takeDamage(Player player) {
        player.decreaseLives(1); // Réduit la vie du joueur
        player.setState(new InvulnerableState(System.currentTimeMillis())); // Change l'état à invulnérable
    }

    @Override
    public void updateAppearance(Player player) {
        player.setAppearance(player.getOriginalAppearance()); // Apparence normale
    }
    // Getter pour l'invulnérabilité
    public static int getInvulnerabilityDuration() {
        return INVULNERABILITY_DURATION;
    }
}