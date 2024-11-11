package fr.univartois.butinfo.r304.bomberman.model.map;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.state.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class SpiralMapGenerator implements IMapGenerator {

    private final SpriteStore spriteStore = new SpriteStore();

    @Override
    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();
        int x = 1, y = 1;
        int dx = 1, dy = 0;

        for (int i = 0; i < rows * cols / 2; i++) {
            if (x > 0 && x < rows - 1 && y > 0 && y < cols - 1) {
                if (Math.random() < 0.1) {
                    map.setAt(x, y, new Cell(new Wall(State.INTACT))); // Murs destructibles éparpillés
                } else {
                    map.setAt(x, y, new Cell(spriteStore.getSprite("lawn"))); // Pelouse
                }
            }

            x += dx;
            y += dy;

            // Change de direction pour former une spirale
            if ((dx == 1 && x >= rows - 2) || (dx == -1 && x <= 1) ||
                    (dy == 1 && y >= cols - 2) || (dy == -1 && y <= 1) ||
                    (map.getAt(x + dx, y + dy) != null && !(x == 1 && y == 1))) {
                int temp = dx;
                dx = -dy;
                dy = temp;
            }
        }
    }
}