package fr.univartois.butinfo.r304.bomberman.model.map;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.state.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class IslandMapGenerator implements IMapGenerator {

    private final SpriteStore spriteStore = new SpriteStore();

    @Override
    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Bordures incassables
                } else if ((i % 6 == 0 && j % 6 == 0) || (i % 6 == 2 && j % 6 == 2)) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Petites "îles" de murs indestructibles
                } else if (Math.random() < 0.3) {
                    map.setAt(i, j, new Cell(new Wall(State.INTACT))); // Murs destructibles formant des ponts
                } else {
                    map.setAt(i, j, new Cell(spriteStore.getSprite("lawn"))); // Pelouse
                }
            }
        }
    }
}