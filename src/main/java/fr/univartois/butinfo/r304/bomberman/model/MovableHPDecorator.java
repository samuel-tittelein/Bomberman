package fr.univartois.butinfo.r304.bomberman.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class MovableHPDecorator implements IMovable {

  private final IMovable movable;
  private final BombermanGame game;

  private final IntegerProperty hp;

  protected MovableHPDecorator(int hp, BombermanGame game, IMovable movable) {
    this.hp = new SimpleIntegerProperty(hp);
    this.movable = movable;
    this.game = game;
  }

  protected MovableHPDecorator(IMovable movable, BombermanGame game) {
    this.hp = new SimpleIntegerProperty(1);
    this.movable = movable;
    this.game = game;
  }

  public BombermanGame getGame() {
    return game;
  }

  public int getHp() {
    return hp.get();
  }

  public void decreaseHp() {
    hp.setValue(hp.getValue() - 1);
  }

  public IMovable getMovable() {
    return movable;
  }
}
