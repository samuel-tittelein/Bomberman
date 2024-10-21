package fr.univartois.butinfo.r304.bomberman.model.map.walls;

import fr.univartois.butinfo.r304.bomberman.view.Sprite;

public interface IWallState {

    Sprite getSprite();
    IWallState next();
}
