package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public class Bomb extends AbstractMovable {

    public static final long BOMB_LIFESPAN = 2000; // 2 secondes
    public static SpriteStore spriteStore = new SpriteStore();
    private final Sprite explosionSprite;
    private long timeWhenDroped;
    private int xDropPosition, yDropPosition;
    private final int explosionSize;


    public Bomb(BombermanGame game, double xPosition, double yPosition, Sprite sprite, Sprite explosionSprite, int explosionSize) {
        super(game, xPosition, yPosition, sprite);
        this.explosionSprite  = explosionSprite;
        this.explosionSize = explosionSize;
    }

    public Bomb(BombermanGame game, double xPosition, double yPosition, Sprite explosionSprite, int explosionSize) {
        super(game, xPosition, yPosition, spriteStore.getSprite("bomb"));
        this.explosionSprite  = explosionSprite;
        this.explosionSize = explosionSize;
    }

    public Bomb(BombermanGame game, double xPosition, double yPosition, int explosionSize) {
        super(game, xPosition, yPosition, spriteStore.getSprite("bomb"));
        this.explosionSprite  = spriteStore.getSprite("explosion");
        this.explosionSize = explosionSize;
    }

    @Override
    public boolean move(long delta) {
        if (System.currentTimeMillis() - timeWhenDroped > BOMB_LIFESPAN) {
            explode();
        }
        return true;
    }

    @Override
    public void collidedWith(IMovable other) {

    }

    @Override
    public void explode() {

        game.addMovable(new Explosion(game, xDropPosition, yDropPosition, explosionSprite));

        for (int direction = 0; direction < 4; direction++) {
            spreadExplosion(direction, xDropPosition, yDropPosition, 0);
        }
        consume();
    }

    public void spreadExplosion(int direction, int x, int y, int iteration) {
        int limitMaxX = game.getWidth() - getWidth();
        int limitMaxY = game.getHeight() - getHeight();
        if ((x < 0) || (x > limitMaxX) || (y < 0) || (y > limitMaxY)) {
            // L'objet a atteint la limite sur l'axe x ou y.
            return;
        }

        iteration++;
        if (iteration > explosionSize) {
            // c'est la seule manière que j'aie trouvée pour arrêter de répendre l'explosion
            return;
        }

        switch (direction) {
            case 0:
                x += 1;
                break;
            case 1:
                x -= 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                y -= 1;
        }
        game.addMovable(new Explosion(game, x, y, explosionSprite));
        if (game.getCellAt(x, y).getWall() != null) {
            // L'objet a atteint un mur.
            Cell cell = new Cell(spriteStore.getSprite("pelouse"));
            game.getCellAt(x, y).replaceBy(cell);
            return ;
        }
        spreadExplosion(direction, x, y, iteration);
    }

    @Override
    public void hitEnemy() {

    }

    public void drop(int xDropPosition, int yDropPosition) {
        timeWhenDroped = System.currentTimeMillis();
        this.xDropPosition = xDropPosition;
        this.yDropPosition = yDropPosition;
    }
}
