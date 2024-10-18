package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class IndestructibleWallState implements IWallState {
    public IWallState nextState() {
        return this;
    }
}
