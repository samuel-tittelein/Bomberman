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

package fr.univartois.butinfo.r304.bomberman.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * La classe {@link Sprite} représente un élément graphique du jeu.
 * Il s'agit d'un objet encapsulant une image sans état interne, et qui peut être placé à
 * un endroit donné de la fenêtre.
 * De cette manière, il est possible d'utiliser la même instance de {@link Sprite} pour
 * représenter plusieurs éléments similaires en même temps.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Sprite {

    /**
     * L'image associée à cette instance de {@link Sprite}.
     */
    private Image image;

    /**
     * Crée une nouvelle instance de {@link Sprite}.
     *
     * @param image L'image associée à l'instance de {@link Sprite}.
     */
    public Sprite(Image image) {
        this.image = image;
    }

    /**
     * Donne la largeur de l'image associée, mesurée en nombre de pixels.
     *
     * @return La largeur de l'image associée.
     */
    public int getWidth() {
        return (int) image.getWidth();
    }

    /**
     * Donne la hauteur de l'image associée, mesurée en nombre de pixels.
     *
     * @return La hauteur de l'image associée.
     */
    public int getHeight() {
        return (int) image.getHeight();
    }

    /**
     * Donne l'image associée à cette instance de {@link Sprite}.
     *
     * @return L'image associée à cette instance de {@link Sprite}.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Dessine cette instance de {@link Sprite} sur un {@link GraphicsContext} donné.
     *
     * @param graphics Le {@link GraphicsContext} sur lequel dessiner l'image.
     * @param x La position en x à laquelle l'image doit être dessinée.
     * @param y La position en y à laquelle l'image doit être dessinée.
     */
    public void draw(GraphicsContext graphics, int x, int y) {
        graphics.drawImage(image, x, y);
    }

}
