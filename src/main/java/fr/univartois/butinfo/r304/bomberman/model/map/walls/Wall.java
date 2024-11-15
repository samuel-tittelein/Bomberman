/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022-2024 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.r304.bomberman.model.map.walls;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.state.*;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;


/**
 * La classe {@link Wall} représente un mur de briques sur la carte du jeu.
 * Il s'agit d'un élément qui ne peut pas être traversé, mais qui peut être détruit par
 * une explosion.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Wall {

    /**
     * Le sprite représentant ce mur sur la carte.
     */
    private Sprite sprite;
    private IWallState state;

    /**
     * Crée une nouvelle instance de Wall.
     *
     * @param state l'état du mur
     */
    public Wall(State state) {
        switch (state) {
            case INDESTRUCTIBLE:
                this.state = new IndestructibleWallState();
                break;
            case INTACT:
                this.state = new IntactWallState();
                break;
            case DEGRADED:
                this.state = new DegradedWallState();
                break;
            case BROKEN:
                this.state = new BrokenWallState();
                break;
            default:
                this.state = new IntactWallState();
        }
        this.sprite = this.state.getSprite();
    }

    public Wall() {
        this(State.INTACT);
    }

    /**
     * Donne le sprite représentant ce mur sur la carte.
     *
     * @return Le sprite représentant ce mur sur la carte.
     */
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void nextState() {
        state = state.next();
        if (state != null) {
            setSprite(state.getSprite());
        }
    }

    public IWallState getState() {
        return state;
    }
}
