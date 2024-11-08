package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

public interface IBomb extends IMovable {
    void drop(Cell cell);

    void explode();
    int getExplosionSize();
    void setExplosionSize(int size);

    void setBombSprite(Sprite sprite);
    Sprite getBombSprite();

    void setSprite(Sprite sprite);
    long getTimeWhenDropped();

}
