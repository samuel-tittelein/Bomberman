package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import java.util.Random;

public class Enemy extends AbstractMovable {

  private static final Random RANDOM = new Random();

  /**
   * Crée une nouvelle instance d'Enemy.
   *
   * @param game      Le jeu dans lequel l'objet évolue.
   * @param xPosition La position en x initiale de l'objet.
   * @param yPosition La position en y initiale de l'objet.
   * @param sprite    L'instance de {@link Sprite} représentant l'objet.
   */
  protected Enemy(BombermanGame game, double xPosition,
      double yPosition, Sprite sprite) {
    super(game, xPosition, yPosition, sprite);
  }

  @Override
  public void collidedWith(IMovable other) {
    other.hitEnemy();
  }

  @Override
  public void explode() {
    game.removeMovable(this);
  }

  @Override
  public void hitEnemy() {
    explode();
  }

  @Override
  public boolean move(long delta) {
    boolean moved = super.move(delta);
    if (!moved) {
      changeDirectionRandomly();
    }
    return moved;
  }

  private void changeDirectionRandomly() {
    // 50% de chance de changer la vitesse horizontale
    if (RANDOM.nextBoolean()) {
      setHorizontalSpeed(getRandomSpeed());
    }
    // 50% de chance de changer la vitesse verticale
    if (RANDOM.nextBoolean()) {
      setVerticalSpeed(getRandomSpeed());
    }
  }

  private double getRandomSpeed() {
    double speed;
    do {
      speed = -100 + (200) * RANDOM.nextDouble();
    } while (speed > -10 && speed < 10);
    return speed;
  }
}
