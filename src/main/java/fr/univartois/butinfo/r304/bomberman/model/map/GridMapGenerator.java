package fr.univartois.butinfo.r304.bomberman.model.map;
//36 - 24
import fr.univartois.butinfo.r304.bomberman.model.map.walls.State.State;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import static fr.univartois.butinfo.r304.bomberman.model.map.DefaultMapGenerator.DEFAULT_WALL_STATE;
public class  GridMapGenerator extends MapGeneratorDecorator {

    @Override
    public void fillMap(GameMap map) {
        super.fillMap(map);
        for (int i = 2; i < rows-2; i+=2) {
            for (int j = 2; j < cols -2; j+=2) {
                if(j % 3==2 ){
                    Cell indestructibleWall = new Cell(new Wall(State.INDESTRUCTIBLE));
                    map.setAt(i, j, indestructibleWall);
                } else  {
                    Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                    map.setAt(i, j, wall);
                }
            }
        }
    }
}

