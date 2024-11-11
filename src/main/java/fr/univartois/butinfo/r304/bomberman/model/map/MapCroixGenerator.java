package fr.univartois.butinfo.r304.bomberman.model.map;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.state.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class MapCroixGenerator implements IMapGenerator {

    private final SpriteStore spriteStore = new SpriteStore();

    @Override
    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Bordures incassables
                } else if (i % 2 == 0 && j % 2 == 0) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Murs incassables toutes les deux cases
                } else if (i == j || i + j == cols - 1) {
                    map.setAt(i, j, new Cell(new Wall(State.INTACT))); // Motif en croix avec murs destructibles
                } else {
                    map.setAt(i, j, new Cell(spriteStore.getSprite("lawn"))); // Pelouse
                }
            }
        }
    }
}
