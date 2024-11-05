package fr.univartois.butinfo.r304.bomberman.model.movables.strategy;

import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;

public interface MovementStrategy {

    /**
     * Calcule les vitesses horizontale et verticale à appliquer à l'ennemi.
     *
     * @param enemy L'ennemi pour lequel on calcule les vitesses.
     */
    void calculateMovement(Enemy enemy);
}
