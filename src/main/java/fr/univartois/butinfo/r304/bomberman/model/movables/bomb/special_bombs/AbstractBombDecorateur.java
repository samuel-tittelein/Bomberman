package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;

import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.IBomb;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

public abstract class AbstractBombDecorateur implements IBomb {
    protected SpriteStore spritestore = new SpriteStore();
    protected IBomb bomb;

    public AbstractBombDecorateur(Bomb bomb) {
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
        return bomb.move(timeDelta);
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
    public void explode() {
        bomb.explode();
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
