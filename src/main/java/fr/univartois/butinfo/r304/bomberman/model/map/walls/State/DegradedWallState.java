package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class DegradedWallState extends AbstractWallState {
    public DegradedWallState() {
        super("wall_degraded");
    }

    public IWallState nextState() {
        return new BrokenWallState();
    }
}
