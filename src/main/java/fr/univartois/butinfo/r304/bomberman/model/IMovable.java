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

package fr.univartois.butinfo.r304.bomberman.model;

import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

/**
 * L'interface {@link IMovable} définit le contrat des éléments du jeu capables
 * de se déplacer.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface IMovable {

    /**
     * Vérifie si cet objet est un ennemi.
     * @return true si l'objet est un ennemi, false sinon.
     */
    default boolean isEnemy() {
        return false;
    }

    /**
     * Donne la largeur de cet objet.
     *
     * @return La largeur de cet objet.
     */
    int getWidth();

    /**
     * Donne la hauteur de cet objet.
     *
     * @return La hauteur de cet objet.
     */
    int getHeight();

    /**
     * Modifie la position en x de cet objet.
     *
     * @param xPosition La nouvelle position en x de cet objet.
     */
    void setX(int xPosition);

    /**
     * Donne la position en x de cet objet.
     *
     * @return La position en x de cet objet.
     */
    int getX();

    /**
     * Donne la propriété liée à la position en x de cet objet.
     *
     * @return La propriété liée à la position en x de cet objet.
     */
    DoubleProperty getXProperty();

    /**
     * Modifie la position en y de cet objet.
     *
     * @param yPosition La nouvelle position en y de cet objet.
     */
    void setY(int yPosition);

    /**
     * Donne la position en y de cet objet.
     *
     * @return La position en y de cet objet.
     */
    int getY();

    /**
     * Donne la propriété liée à la position en y de cet objet.
     *
     * @return La propriété liée à la position en y de cet objet.
     */
    DoubleProperty getYProperty();

    /**
     * Consomme cet objet pour qu'il ne soit plus affiché dans le jeu.
     */
    void consume();

    /**
     * Vérifie si cet objet a été consommé.
     * Un objet consommé disparaît de l'affichage.
     *
     * @return Si cet objet a été consommé.
     */
    boolean isConsumed();

    /**
     * Donne la propriété de cet objet liée au fait qu'il a été ou non consommé.
     *
     * @return La propriété de cet objet liée au fait qu'il a été ou non consommé.
     *
     * @see #isConsumed()
     */
    BooleanProperty isConsumedProperty();

    /**
     * Modifie la vitesse horizontale de cet objet.
     *
     * @param speed La nouvelle vitesse horizontale de cet objet (en pixels/s).
     */
    void setHorizontalSpeed(double speed);

    /**
     * Donne la vitesse horizontale de cet objet.
     *
     * @return La vitesse horizontale de cet objet (en pixels/s).
     */
    double getHorizontalSpeed();

    /**
     * Modifie la vitesse verticale de cet objet.
     *
     * @param speed La nouvelle vitesse verticale de cet objet (en pixels/s).
     */
    void setVerticalSpeed(double speed);

    /**
     * Donne la vitesse verticale de cet objet.
     *
     * @return La vitesse verticale de cet objet (en pixels/s).
     */
    double getVerticalSpeed();

    /**
     * Modifie l'instance de {@link Sprite} représentant cet objet.
     *
     * @param sprite La nouvelle instance de {@link Sprite} représentant cet objet.
     */
    void setSprite(Sprite sprite);

    /**
     * Donne l'instance de {@link Sprite} représentant cet objet.
     *
     * @return L'instance de {@link Sprite} représentant cet objet.
     */
    Sprite getSprite();

    /**
     * Donne la propriété de cet objet correspondant au {@link Sprite} qui le représente.
     *
     * @return La propriété de cet objet correspondant à son {@link Sprite}.
     */
    ObjectProperty<Sprite> getSpriteProperty();

    /**
     * Déplace cet objet pour le mettre à sa nouvelle position, calculée à partir du temps
     * écoulé depuis son dernier déplacement et sa vitesse actuelle.
     *
     * @param timeDelta Le temps écoulé depuis le dernier déplacement de cet objet (en
     *        millisecondes).
     *
     * @return Si l'objet a pu être déplacé.
     *         Si ce n'est pas le cas, il a atteint le bord de la fenêtre, et est donc
     *         bloqué.
     */
    boolean move(long timeDelta);

    /**
     * Vérifie si cet objet est entré en collision avec une autre instance de
     * {@link IMovable}.
     *
     * @param other L'objet avec lequel la collision doit être vérifiée.
     *
     * @return Si cet objet est entré en collision avec {@code other}.
     */
    boolean isCollidingWith(IMovable other);

    /**
     * Informe cet objet qu'il est entré en collision avec une autre instance de
     * {@link IMovable}.
     *
     * @param other L'objet avec lequel cet objet est entré en collision.
     */
    void collidedWith(IMovable other);

    /**
     * Fait exploser cet objet.
     */
    void explode();

    /**
     * Informe cet objet qu'il a touché un ennemi.
     */
    void hitEnemy();

    /**
     * Donne l'objet réel qui implémente cette interface.
     *
     * @return L'objet réel.
     */
    IMovable self();

}
