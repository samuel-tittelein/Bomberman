package fr.univartois.butinfo.r304.bomberman.model.movables.state;

import fr.univartois.butinfo.r304.bomberman.model.movables.Player;

/**
 * La classe {@link InvulnerableState} représente l'état d'invulnérabilité temporaire du joueur.
 * Pendant cet état, le joueur ne peut pas subir de dommages, et il a une apparence spéciale.
 */
public class InvulnerableState implements PlayerState {
    private final long startTime;

    /**
     * Crée une nouvelle instance d'InvulnerableState.
     *
     * @param startTime Le moment où l'état d'invulnérabilité a commencé.
     */
    public InvulnerableState(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void takeDamage(Player player) {
        // Aucun effet car le joueur est invulnérable
    }

    @Override
    public void updateAppearance(Player player) {
        player.setAppearance(player.getInvulnerableAppearance()); // Apparence spéciale d'invulnérabilité
        if (System.currentTimeMillis() - startTime >= VulnerableState.getInvulnerabilityDuration()) {
            player.setState(new VulnerableState()); // Retour à l'état vulnérable après la durée définie
        }
    }
}
