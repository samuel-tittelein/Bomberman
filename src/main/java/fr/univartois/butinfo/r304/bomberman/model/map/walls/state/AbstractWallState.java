package fr.univartois.butinfo.r304.bomberman.model.map.walls.state;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

abstract class AbstractWallState implements IWallState {
    public static SpriteStore sp = new SpriteStore();
    protected Sprite sprite;

    public AbstractWallState(String identifier) {
        sprite = sp.getSprite(identifier);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
