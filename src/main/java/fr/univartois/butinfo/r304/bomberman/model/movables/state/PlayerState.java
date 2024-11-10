package fr.univartois.butinfo.r304.bomberman.model.movables.state;

import fr.univartois.butinfo.r304.bomberman.model.movables.Player;

/**
 * L'interface {@link PlayerState} définit les comportements de l'état du joueur
 * lorsqu'il prend des dommages et met à jour son apparence en fonction de son état.
 */
public interface PlayerState {

    /**
     * Applique les effets de dommages au joueur, selon l'état courant.
     *
     * @param player Le joueur recevant les dommages.
     */
    void takeDamage(Player player);

    /**
     * Met à jour l'apparence du joueur selon son état courant.
     *
     * @param player Le joueur dont l'apparence est à mettre à jour.
     */
    void updateAppearance(Player player);
}