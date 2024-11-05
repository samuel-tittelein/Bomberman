package fr.univartois.butinfo.r304.bomberman.model.map;


import fr.univartois.butinfo.r304.bomberman.model.map.walls.State.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class GameMapGenerator {

    private static final State DEFAULT_WALL_STATE = State.INTACT;
    SpriteStore ss = new SpriteStore();

    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) { // Bordures
                    Cell indestructibleWall = new Cell(new Wall(State.INDESTRUCTIBLE));
                    map.setAt(i, j, indestructibleWall);
                } else if (Math.random() < 0.05) { // Mur aléatoire avec probabilité de 20%
                    Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                    map.setAt(i, j, wall);
                } else {
                    Cell lawn = new Cell(ss.getSprite("lawn"));
                    map.setAt(i, j, lawn);
                }
            }
        }
    }
}
