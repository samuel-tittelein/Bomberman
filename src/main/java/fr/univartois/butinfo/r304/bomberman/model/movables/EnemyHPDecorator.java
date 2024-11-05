package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.MovableHPDecorator;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

public class EnemyHPDecorator extends MovableHPDecorator {
  private long lastHitTime = 0; // Timestamp pour le cooldown de collision
  private static final int COOLDOWN_TIME = 3000; // 3 secondes de cooldown (sinon il perd trop de pdv trop rapidement)

  public EnemyHPDecorator(int hp, BombermanGame game,
      IMovable movable) {
    super(hp, game, movable);
  }

  public EnemyHPDecorator(IMovable movable, BombermanGame game) {
    super(movable, game);
  }

  @Override
  public boolean isEnemy() {
    return getMovable().isEnemy();
  }

  @Override
  public int getWidth() {
    return getMovable().getWidth();
  }

  @Override
  public int getHeight() {
    return getMovable().getHeight();
  }

  @Override
  public void setX(int xPosition) {
    getMovable().setX(xPosition);
  }

  @Override
  public int getX() {
    return getMovable().getX();
  }

  @Override
  public DoubleProperty getXProperty() {
    return getMovable().getXProperty();
  }

  @Override
  public void setY(int yPosition) {
    getMovable().setY(yPosition);
  }

  @Override
  public int getY() {
    return getMovable().getY();
  }

  @Override
  public DoubleProperty getYProperty() {
    return getMovable().getYProperty();
  }

  @Override
  public void consume() {
    getMovable().consume();
  }

  @Override
  public boolean isConsumed() {
    return getMovable().isConsumed();
  }

  @Override
  public BooleanProperty isConsumedProperty() {
    return getMovable().isConsumedProperty();
  }

  @Override
  public void setHorizontalSpeed(double speed) {
    getMovable().setHorizontalSpeed(speed);
  }

  @Override
  public double getHorizontalSpeed() {
    return getMovable().getHorizontalSpeed();
  }

  @Override
  public void setVerticalSpeed(double speed) {
    getMovable().setVerticalSpeed(speed);
  }

  @Override
  public double getVerticalSpeed() {
    return getMovable().getVerticalSpeed();
  }

  @Override
  public void setSprite(Sprite sprite) {
    getMovable().setSprite(sprite);
  }

  @Override
  public Sprite getSprite() {
    return getMovable().getSprite();
  }

  @Override
  public ObjectProperty<Sprite> getSpriteProperty() {
    return getMovable().getSpriteProperty();
  }

  @Override
  public boolean move(long timeDelta) {
    return getMovable().move(timeDelta);
  }

  @Override
  public boolean isCollidingWith(IMovable other) {
    return getMovable().isCollidingWith(other);
  }

  @Override
  public void collidedWith(IMovable other) {
    getMovable().collidedWith(other);
  }

  @Override
  public void explode() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastHitTime >= COOLDOWN_TIME) {
      decreaseHp();
      lastHitTime = currentTime;

      if (getHp() <= 0) {
        getGame().enemyIsDead(this);
      }
    }
  }

  @Override
  public void hitEnemy() {
    getMovable().hitEnemy();
  }

  @Override
  public IMovable self() {
    return this;
  }
}
