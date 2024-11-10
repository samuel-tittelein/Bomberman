package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Explosion;

/**
 * This class decorate Bomb
 * it implement the horizontal bomb that explode all the row if there are no wall.
 */
public class HorizontalBomb extends AbstractBombDecorator {

    public HorizontalBomb(Bomb bomb) {
        super(bomb);
        this.decorate();
    }

    private void decorate() {
        setBombSprite(spritestore.getSprite("row-bomb"));
        setExplosionSize(100);
    }


    @Override
    public void explode() {
        BombermanGame game = bomb.getGame();
        game.addMovable(new Explosion(game, getX(), getY()));
        //spread the explosion to the right
        bomb.spreadExplosion(0, getX(), getY(), 0, getHeight());
        //spread the explosion to the left
        bomb.spreadExplosion(1, getX(), getY(), 0, getHeight());
        game.removeMovable(this);
    }

}
