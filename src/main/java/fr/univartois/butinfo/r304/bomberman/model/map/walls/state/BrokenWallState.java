package fr.univartois.butinfo.r304.bomberman.model.map.walls.state;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class BrokenWallState extends AbstractWallState {
    public BrokenWallState() {
        super("broken_wall");
    }

    public IWallState next() {
        return null;
    }
}
