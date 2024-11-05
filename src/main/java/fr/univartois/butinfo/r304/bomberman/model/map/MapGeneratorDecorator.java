package fr.univartois.butinfo.r304.bomberman.model.map;

public abstract class MapGeneratorDecorator implements IMapGenerator{
    int rows;
    int cols;
    IMapGenerator generator = new DefaultMapGenerator();

    @Override
    public void fillMap(GameMap map) {
         rows = map.getHeight();
         cols = map.getWidth();
        generator.fillMap(map);
    }
}
