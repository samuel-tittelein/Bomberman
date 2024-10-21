package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class DegradedWallState extends AbstractWallState {
    public DegradedWallState() {
        super("degraded_wall");
    }

    public IWallState next() {
        return new BrokenWallState();
    }
}
