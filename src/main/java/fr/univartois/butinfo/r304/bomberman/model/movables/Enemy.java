package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.strategy.MovementStrategy;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

/**
 * La classe {@link Enemy} représente un ennemi dans le jeu Bomberman.
 * Cet ennemi se déplace en fonction d'une stratégie de mouvement spécifique
 * implémentée via l'interface {@link MovementStrategy}.
 * <p>
 * Un ennemi peut entrer en collision avec d'autres objets mobiles et être détruit par une explosion.
 * </p>
 */
public class Enemy extends AbstractMovable {

  /**
   * La stratégie de mouvement de cet ennemi.
   */
  private MovementStrategy movementStrategy;

  /**
   * Initialise une nouvelle instance de la classe {@link Enemy}.
   *
   * @param game      Le jeu dans lequel l'ennemi évolue.
   * @param xPosition La position en x initiale de l'ennemi.
   * @param yPosition La position en y initiale de l'ennemi.
   * @param sprite    L'instance de {@link Sprite} représentant l'ennemi.
   */
  public Enemy(BombermanGame game, double xPosition, double yPosition, Sprite sprite) {
    super(game, xPosition, yPosition, sprite);
  }

  /**
   * Détermine si cet objet est un ennemi.
   *
   * @return {@code true} car cette instance représente un ennemi.
   */
  @Override
  public boolean isEnemy() {
    return true;
  }

  /**
   * Définit la stratégie de mouvement pour cet ennemi.
   *
   * @param movementStrategy La stratégie de mouvement que cet ennemi doit suivre.
   */
  public void setMovementStrategy(MovementStrategy movementStrategy) {
    this.movementStrategy = movementStrategy;
  }

  /**
   * Gère la collision de cet ennemi avec un autre objet mobile.
   *
   * @param other L'autre objet mobile avec lequel cet ennemi entre en collision.
   */
  @Override
  public void collidedWith(IMovable other) {
    other.hitEnemy();
  }

  /**
   * Déclenche l'explosion de cet ennemi, ce qui signale au jeu que cet ennemi
   * est détruit.
   */
  @Override
  public void explode() {
    game.enemyIsDead(this);
  }

  /**
   * Déclenche une action lorsque cet ennemi est frappé par un autre objet.
   * Cette méthode est actuellement vide car aucun comportement spécifique n'est défini.
   */
  @Override
  public void hitEnemy() {
    // Comportement défini si nécessaire
  }

  /**
   * Effectue le mouvement de cet ennemi en utilisant la stratégie de mouvement
   * définie, si elle est présente. La stratégie de mouvement ajuste les
   * vitesses horizontale et verticale de cet ennemi.
   *
   * @param delta Le temps écoulé depuis la dernière mise à jour du mouvement.
   * @return {@code true} si le mouvement a été effectué avec succès.
   */
  @Override
  public boolean move(long delta) {
    if (movementStrategy != null) {
      movementStrategy.calculateMovement(this);
    }
    return super.move(delta);
  }
}
