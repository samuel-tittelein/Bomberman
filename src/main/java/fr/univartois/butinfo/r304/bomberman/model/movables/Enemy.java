package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.strategy.MovementStrategy;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import java.util.Random;

public class Enemy extends AbstractMovable {

  private static final Random RANDOM = new Random();

  // Attribut pour la stratégie de mouvement
  private MovementStrategy movementStrategy;

  @Override
  public boolean isEnemy() {
    return true;
  }

  /**
   * Crée une nouvelle instance d'Enemy.
   *
   * @param game      Le jeu dans lequel l'objet évolue.
   * @param xPosition La position en x initiale de l'objet.
   * @param yPosition La position en y initiale de l'objet.
   * @param sprite    L'instance de {@link Sprite} représentant l'objet.
   */
  public Enemy(BombermanGame game, double xPosition, double yPosition, Sprite sprite) {
    super(game, xPosition, yPosition, sprite);
  }

  // Méthode pour définir la stratégie de mouvement
  public void setMovementStrategy(MovementStrategy movementStrategy) {
    this.movementStrategy = movementStrategy;
  }

  @Override
  public void collidedWith(IMovable other) {
    other.hitEnemy();
  }

  @Override
  public void explode() {
    game.enemyIsDead(this);
  }

  @Override
  public void hitEnemy() {}

  @Override
  public boolean move(long delta) {
    // Utilise la stratégie de mouvement pour calculer les vitesses
    if (movementStrategy != null) {
      movementStrategy.calculateMovement(this);
    }
    return super.move(delta);
  }
}
