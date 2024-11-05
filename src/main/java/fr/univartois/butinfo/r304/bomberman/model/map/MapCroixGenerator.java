package fr.univartois.butinfo.r304.bomberman.model.map;
//36 - 24
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import static fr.univartois.butinfo.r304.bomberman.model.map.DefaultMapGenerator.DEFAULT_WALL_STATE;
public class MapCroixGenerator extends MapGeneratorDecorator {

    @Override
    public void fillMap(GameMap map) {
        super.fillMap(map);
        for (int i = 5; i < rows-5; i++) {
            for (int j = 17; j < rows - 5; j++) {
                Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                map.setAt(i, j, wall);
            }
        }

        for (int j = 5; j < cols-5; j++) {
            for (int i = 11; i < rows - 11; i++) {
                Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                map.setAt(i, j, wall);
            }
        }
    }
}

