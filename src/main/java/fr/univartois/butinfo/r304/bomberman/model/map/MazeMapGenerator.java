package fr.univartois.butinfo.r304.bomberman.model.map;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.state.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class MazeMapGenerator implements IMapGenerator {

    private final SpriteStore spriteStore = new SpriteStore();

    @Override
    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Bordures incassables
                } else if ((i % 4 == 0 || j % 4 == 0) && Math.random() < 0.6) {
                    map.setAt(i, j, new Cell(new Wall(State.INDESTRUCTIBLE))); // Labyrinthe avec murs incassables
                } else if (Math.random() < 0.2) {
                    map.setAt(i, j, new Cell(new Wall(State.INTACT))); // Murs destructibles aléatoires
                } else {
                    map.setAt(i, j, new Cell(spriteStore.getSprite("lawn"))); // Pelouse
                }
            }
        }
    }
}