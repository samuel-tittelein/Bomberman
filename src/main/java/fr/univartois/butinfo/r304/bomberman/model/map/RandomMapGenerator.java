package fr.univartois.butinfo.r304.bomberman.model.map;

import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;


import static fr.univartois.butinfo.r304.bomberman.model.map.DefaultMapGenerator.DEFAULT_WALL_STATE;

public class RandomMapGenerator extends MapGeneratorDecorator {

    SpriteStore ss = new SpriteStore();
    IMapGenerator generator = new DefaultMapGenerator();

    @Override
    public void fillMap(GameMap map) {

        generator.fillMap(map);

        for (int i = 1; i < rows-1; i++) {
            for (int j = 1; j < cols-1; j++) {
                if (Math.random() < 0.2) { // Mur aléatoire avec probabilité de 20%
                    Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                    map.setAt(i, j, wall);
                }
            }
        }
    }
}
