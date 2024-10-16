package fr.univartois.butinfo.r304.bomberman.model.map;


import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class GameMapGenerator {

    SpriteStore ss = new SpriteStore();
    Cell wall = new Cell(new Wall(ss.getSprite("wall")));
    Cell lawn = new Cell(ss.getSprite("lawn"));

    public GameMap fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) { // Bordures
                    map.setAt(i, j, wall);
                } else if (Math.random() < 0.2) { // Mur aléatoire avec probabilité de 20%
                    map.setAt(i, j, wall);
                } else {
                    map.setAt(i, j, lawn);
                }
            }
        }
        return map;
    }
}
