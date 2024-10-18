package fr.univartois.butinfo.r304.bomberman.model.map.walls.State;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.IWallState;

public class BrokenWallState extends AbstractWallState {
    public BrokenWallState() {
        super("broken_wall");
    }

    public IWallState next() {
        //TODO remplacer le mur par du gazon
        System.out.println("OK broken");
        //currentCell.replaceBy(new Cell(spriteStore.getSprite("lawn")));
        return this;
    }
}
