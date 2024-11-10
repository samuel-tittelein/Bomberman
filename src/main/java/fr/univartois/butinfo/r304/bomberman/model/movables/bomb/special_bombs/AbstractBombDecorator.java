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

import static java.lang.System.currentTimeMillis;

public abstract class AbstractBombDecorator implements IBomb {
    protected SpriteStore spritestore = new SpriteStore();
    protected Bomb bomb;
    private long timeWhenDropped;

    public AbstractBombDecorator(Bomb bomb) {
        this.bomb = bomb;
    }

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
    public void setSprite(Sprite sprite) {
        bomb.setSprite(sprite);
    }

    @Override
    public Sprite getSprite() {
        return bomb.getSprite();
    }

    @Override
    public ObjectProperty<Sprite> getSpriteProperty() {
        return bomb.getSpriteProperty();
    }

    @Override
    public boolean move(long timeDelta) {
        // Vérifie si le temps écoulé depuis le dépôt de la bombe dépasse BOMB_LIFESPAN
        long elapsedTime = currentTimeMillis() - timeWhenDropped;
        if (elapsedTime > Bomb.BOMB_LIFESPAN) {
            explode(); // Déclenche l'explosion si le temps est écoulé
        }
        return true; // La bombe ne se déplace pas, donc toujours vrai
    }

    @Override
    public boolean isCollidingWith(IMovable other) {
        return bomb.isCollidingWith(other);
    }

    @Override
    public void collidedWith(IMovable other) {
        bomb.collidedWith(other);
    }

    public void explode() {
        bomb.explode();
        bomb.getGame().removeMovable(this);
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

    @Override
    public void drop(Cell cell) {
        setTimeWhenDropped(currentTimeMillis());

        int x = cell.getColumn() * cell.getWidth();
        int y = cell.getRow() * cell.getHeight();

        setX(x);
        setY(y);

        bomb.getGame().addMovable(this);
    }

    @Override
    public void setTimeWhenDropped(long time) {
        this.timeWhenDropped = time;
    }

    @Override
    public long getTimeWhenDropped() {
        return this.timeWhenDropped;
    }
}
