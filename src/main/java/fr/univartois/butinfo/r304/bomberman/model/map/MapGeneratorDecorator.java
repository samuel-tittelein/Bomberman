package fr.univartois.butinfo.r304.bomberman.model.map;

public abstract class MapGeneratorDecorator implements IMapGenerator{
    int rows;
    int cols;
    @Override
    public void fillMap(GameMap map) {
        int rows = map.getHeight();
        int cols = map.getWidth();

    }
}
