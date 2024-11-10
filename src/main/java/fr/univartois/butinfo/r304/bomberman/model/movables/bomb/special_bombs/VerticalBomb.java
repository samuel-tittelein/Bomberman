package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Explosion;

/**
 * This class decorate Bomb
 * it implement the vertical bomb that explode all the column if there are no wall.
 */
public class VerticalBomb extends AbstractBombDecorator {

    public VerticalBomb(Bomb bomb) {
        super(bomb);
        this.decorate();
    }

    private void decorate() {
        setBombSprite(spritestore.getSprite("column-bomb"));
        setExplosionSize(100);
    }

    @Override
    public void explode() {
        BombermanGame game = bomb.getGame();
        game.addMovable(new Explosion(game, getX(), getY()));
        //spread explosion to the up direction
        bomb.spreadExplosion(2, getX(), getY(), 0, getHeight());
        //spread explosion to the down direction
        bomb.spreadExplosion(3, getX(), getY(), 0, getHeight());
        game.removeMovable(this);
    }
}
