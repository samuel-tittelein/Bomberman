package fr.univartois.butinfo.r304.bomberman.model.map.walls.state;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class IntactWallState extends AbstractWallState {
    public IntactWallState() {
        super("intact_wall");
    }

    public IWallState next() {
        return new DegradedWallState();
    }
}
