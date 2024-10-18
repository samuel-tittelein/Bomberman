package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class BrokenWallState extends AbstractWallState {
    public BrokenWallState() {
        super("broken_wall");
    }

    public IWallState nextState() {
        return null;
    }
}
