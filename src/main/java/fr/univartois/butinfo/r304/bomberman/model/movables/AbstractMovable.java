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

package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

/**
 * La classe {@link AbstractMovable} fournit une implantation de base pour tous les objets
 * élémentaires pouvant se déplacer dans le jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public abstract class   AbstractMovable implements IMovable {

    /**
     * La marge de sécurité pour les obstacles (en pixels).
     */
    private static final int MARGIN = 5;

    /**
     * Le jeu dans lequel cet objet évolue.
     */
    protected final BombermanGame game;

    /**
     * La position en x de cet objet.
     */
    protected final DoubleProperty xPosition;

    /**
     * La position en y de cet objet.
     */
    protected final DoubleProperty yPosition;

    /**
     * Si cet objet a été consommé.
     */
    protected final BooleanProperty consumed;

    /**
     * La vitesse horizontale actuelle de cet objet (en pixels/s).
     */
    protected double horizontalSpeed;

    /**
     * La vitesse verticale actuelle de cet objet (en pixels/s).
     */
    protected double verticalSpeed;

    /**
     * L'instance de {@link Sprite} représentant cet objet.
     */
    protected final ObjectProperty<Sprite> sprite;

    /**
     * Crée une nouvelle instance de AbstractMovable.
     *
     * @param game Le jeu dans lequel l'objet évolue.
     * @param xPosition La position en x initiale de l'objet.
     * @param yPosition La position en y initiale de l'objet.
     * @param sprite L'instance de {@link Sprite} représentant l'objet.
     */
    protected AbstractMovable(BombermanGame game, double xPosition,
            double yPosition, Sprite sprite) {
        this.game = game;
        this.xPosition = new SimpleDoubleProperty(xPosition);
        this.yPosition = new SimpleDoubleProperty(yPosition);
        this.consumed = new SimpleBooleanProperty(false);
        this.sprite = new SimpleObjectProperty<>(sprite);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getWidth()
     */
    @Override
    public int getWidth() {
        return sprite.get().getWidth();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getHeight()
     */
    @Override
    public int getHeight() {
        return sprite.get().getHeight();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#setX(int)
     */
    @Override
    public void setX(int xPosition) {
        this.xPosition.set(xPosition);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getX()
     */
    @Override
    public int getX() {
        return xPosition.intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getXProperty()
     */
    @Override
    public DoubleProperty getXProperty() {
        return xPosition;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#setY(int)
     */
    @Override
    public void setY(int yPosition) {
        this.yPosition.set(yPosition);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getY()
     */
    @Override
    public int getY() {
        return yPosition.intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getYProperty()
     */
    @Override
    public DoubleProperty getYProperty() {
        return yPosition;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#consume()
     */
    @Override
    public void consume() {
        consumed.set(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#isConsumed()
     */
    @Override
    public boolean isConsumed() {
        return consumed.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#isConsumedProperty()
     */
    @Override
    public BooleanProperty isConsumedProperty() {
        return consumed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#setHorizontalSpeed(double)
     */
    @Override
    public void setHorizontalSpeed(double speed) {
        this.horizontalSpeed = speed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getHorizontalSpeed()
     */
    @Override
    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#setVerticalSpeed(double)
     */
    @Override
    public void setVerticalSpeed(double speed) {
        this.verticalSpeed = speed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getVerticalSpeed()
     */
    @Override
    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.r304.bomberman.model.IMovable#setSprite(fr.univartois.butinfo
     * .r304.bomberman.view.Sprite)
     */
    @Override
    public void setSprite(Sprite sprite) {
        this.sprite.set(sprite);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getSprite()
     */
    @Override
    public Sprite getSprite() {
        return sprite.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#getSpriteProperty()
     */
    @Override
    public ObjectProperty<Sprite> getSpriteProperty() {
        return sprite;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#move(long)
     */
    @Override
    public boolean move(long delta) {
        // On met à jour la position de l'objet sur l'axe x.
        int limitMaxX = game.getWidth() - getWidth();
        double newX = xPosition.get() + (horizontalSpeed * delta) / 1000;
        if ((newX < 0) || (newX > limitMaxX)) {
            // L'objet a atteint la limite sur l'axe x.
            return false;
        }

        // On met à jour la position de l'objet sur l'axe y.
        int limitMaxY = game.getHeight() - getHeight();
        double newY = yPosition.get() + (verticalSpeed * delta) / 1000;
        if ((newY < 0) || (newY > limitMaxY)) {
            // L'objet a atteint la limite sur l'axe y.
            return false;
        }

        // On vérifie qu'il n'y a pas un obstacle.
        if (isOnWall((int) newX, (int) newY)) {
            // L'objet a atteint un mur.
            return false;
        }

        // L'objet n'a atteint aucun obstacle
        xPosition.set(newX);
        yPosition.set(newY);
        return true;
    }

    /**
     * Vérifie si la nouvelle position de l'objet est sur un mur.
     *
     * @param x La nouvelle position en x de l'objet.
     * @param y La nouvelle position en y de l'objet.
     *
     * @return Si la nouvelle position de l'objet est sur un mur.
     */
    private boolean isOnWall(int x, int y) {
        if (game.getCellAt(x, y).getWall() != null) {
            // Le coin supérieur gauche de l'objet a atteint un mur.
            return true;
        }

        if (game.getCellAt(x, y + getHeight() - MARGIN).getWall() != null) {
            // Le coin inférieur gauche de l'objet a atteint un mur.
            return true;
        }

        if (game.getCellAt(x + getWidth() - MARGIN, y).getWall() != null) {
            // Le coin supérieur droit de l'objet a atteint un mur.
            return true;
        }

        if (game.getCellAt(x + getWidth() - MARGIN, y + getHeight() - MARGIN).getWall() != null) {
            // Le coin inférieur droit de l'objet a atteint un mur.
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.r304.bomberman.model.IMovable#isCollidingWith(fr.univartois.
     * butinfo.r304.bomberman.model.IMovable)
     */
    @Override
    public boolean isCollidingWith(IMovable other) {
        if (isConsumed() || other.isConsumed()) {
            // L'un des deux objets au moins est déjà consommé.
            // Il ne peut donc pas y avoir de collision.
            return false;
        }

        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        return rectangle.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.bomberman.model.IMovable#self()
     */
    @Override
    public IMovable self() {
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            // Les deux objets sont forcément différents.
            return false;
        }

        if (obj == this) {
            // Les deux objets sont strictement identiques.
            return true;
        }

        if (obj instanceof IMovable other) {
            // On compare les "vrais objets".
            return other.self() == self();
        }

        // L'objet donné n'est pas d'une classe compatible.
        return false;
    }

}
