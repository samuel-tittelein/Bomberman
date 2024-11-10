package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;

import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.IBomb;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

import static fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb.BOMB_LIFESPAN;
import static java.lang.System.currentTimeMillis;

/**
 * Define methods of decorator for Bomb
 * It implement the interface IBomb that extends the interface IMovable
 */
public abstract class AbstractBombDecorator implements IBomb {
    protected SpriteStore spritestore = new SpriteStore();
    protected Bomb bomb; // the decorated Bomb
    private long timeWhenDropped; //We don't drop the decorated bomb, so we need it

    public AbstractBombDecorator(Bomb bomb) {
        this.bomb = bomb;
    }

    /**
     * We need to Override this method because we don't drop the decorated bomb.
     * We need to set the timeWhenDropped
     * @param cell the cell where the bomb is dropped
     */
    @Override
    public void drop(Cell cell) {
        setTimeWhenDropped(currentTimeMillis());

        int x = cell.getColumn() * cell.getWidth();
        int y = cell.getRow() * cell.getHeight();

        setX(x);
        setY(y);

        bomb.getGame().addMovable(this);
    }

    /**
     * We need to Override this method because we don't drop the decorated bomb
     * @param timeDelta Le temps écoulé depuis le dernier déplacement de cet objet (en
     *        millisecondes).
     *
     * @return Always true, because the bomb doesn't move but can explode
     */
    @Override
    public boolean move(long timeDelta) {
        // Vérifie si le temps écoulé depuis le dépôt de la bombe dépasse BOMB_LIFESPAN
        long elapsedTime = currentTimeMillis() - timeWhenDropped;
        if (elapsedTime > BOMB_LIFESPAN) {
            explode(); // Déclenche l'explosion si le temps est écoulé
        }
        return true; // La bombe ne se déplace pas, donc toujours vrai
    }

    /**
     * The default way to explode is to explose the decorated bomb and remove the decorator
     */
    public void explode() {
        bomb.explode();
        bomb.getGame().removeMovable(this);
    }

    @Override
    public void setTimeWhenDropped(long time) {
        this.timeWhenDropped = time;
    }

    @Override
    public long getTimeWhenDropped() {
        return this.timeWhenDropped;
    }

    @Override
    public void setSprite(Sprite sprite) {
        bomb.setSprite(sprite);
    }

    @Override
    public Sprite getSprite() {
        return bomb.getSprite();
    }

    //the following methods are for the interface IBomb but from the interface IMovable
    //Basically we call the methods of the decorated bomb

    @Override
    public int getExplosionSize() {
        return bomb.getExplosionSize();
    }

    @Override
    public Sprite getBombSprite() {
        return bomb.getBombSprite();
    }

    @Override
    public int getWidth() {
        return bomb.getWidth();
    }

    @Override
    public int getHeight() {
        return bomb.getHeight();
    }

    @Override
    public void setX(int xPosition) {
        bomb.setX(xPosition);
    }

    @Override
    public int getX() {
        return bomb.getX();
    }

    @Override
    public DoubleProperty getXProperty() {
        return bomb.getXProperty();
    }

    @Override
    public void setY(int yPosition) {
        bomb.setY(yPosition);
    }

    @Override
    public int getY() {
        return bomb.getY();
    }

    @Override
    public DoubleProperty getYProperty() {
        return bomb.getYProperty();
    }

    @Override
    public void consume() {
        bomb.consume();
    }

    @Override
    public boolean isConsumed() {
        return bomb.isConsumed();
    }

    @Override
    public BooleanProperty isConsumedProperty() {
        return bomb.isConsumedProperty();
    }

    @Override
    public void setHorizontalSpeed(double speed) {
        bomb.setHorizontalSpeed(speed);
    }

    @Override
    public double getHorizontalSpeed() {
        return bomb.getHorizontalSpeed();
    }

    @Override
    public void setVerticalSpeed(double speed) {
        bomb.setVerticalSpeed(speed);
    }

    @Override
    public double getVerticalSpeed() {
        return bomb.getVerticalSpeed();
    }

    @Override
    public ObjectProperty<Sprite> getSpriteProperty() {
        return bomb.getSpriteProperty();
    }

    @Override
    public boolean isCollidingWith(IMovable other) {
        return bomb.isCollidingWith(other);
    }

    @Override
    public void collidedWith(IMovable other) {
        bomb.collidedWith(other);
    }

    @Override
    public void hitEnemy() {
        bomb.hitEnemy();
    }

    @Override
    public IMovable self() {
        return this;
    }

    @Override
    public void setBombSprite(Sprite sprite) {
        bomb.setBombSprite(sprite);
    }

    @Override
    public void setExplosionSize(int size) {
        bomb.setExplosionSize(size);
    }
}
