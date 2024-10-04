package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

public class Enemy extends AbstractMovable {

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
}
