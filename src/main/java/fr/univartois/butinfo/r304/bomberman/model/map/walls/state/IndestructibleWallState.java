package fr.univartois.butinfo.r304.bomberman.model.map.walls.state;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class IndestructibleWallState extends AbstractWallState {
    public IndestructibleWallState() {
        super("wall");
    }

    public IWallState next() {
        return this;
    }
}
