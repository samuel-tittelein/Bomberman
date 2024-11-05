package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

public interface IBomb {
    BombermanGame getGame();
    void drop(Cell cell);
    int getWidth();
    int getHeight();
    int getExplosionSize();
    Sprite getBombSprite();

}
