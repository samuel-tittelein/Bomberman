package fr.univartois.butinfo.r304.bomberman.model.map;
//36 - 24
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import static fr.univartois.butinfo.r304.bomberman.model.map.DefaultMapGenerator.DEFAULT_WALL_STATE;
public class  SecretMapGenerator extends MapGeneratorDecorator {

    @Override
    public void fillMap(GameMap map) {
        super.fillMap(map);

        for (int row = 2; row < rows-2; row+=5) {
            for (int col = 3; col < cols-2 ; col += 17) {


                for (int i = 0; i + row < rows - 2 && i <= 2; i++) {
                    for (int j = 0; j + col < cols - 2 && j <= 12; j += 2) {
                        Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                        map.setAt(i + row, j+col, wall);
                    }
                }
                int i = 3;
                for (int j = 0; j + col < cols - 2 && j <= 12; j ++) {
                    if (!(j == 3 || j == 4 || j == 6 || j == 8 || j == 9)) {
                        System.out.println(j +" "+(i + row)+","+(j+col));
                        Cell wall = new Cell(new Wall(DEFAULT_WALL_STATE));
                        map.setAt(i + row, j + col, wall);
                    }
                }
            }
        }
    }
}

