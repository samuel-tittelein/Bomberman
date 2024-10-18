package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class IntactWallState extends AbstractWallState {
    public IntactWallState() {
        super("wall_intact");
    }

    public IWallState nextState() {
        return new DegradedWallState();
    }
}
