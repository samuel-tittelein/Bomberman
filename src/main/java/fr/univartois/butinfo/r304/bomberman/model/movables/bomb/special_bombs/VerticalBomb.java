package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Explosion;

import static fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb.BOMB_LIFESPAN;
import static java.lang.System.currentTimeMillis;

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
    public boolean move(long timeDelta) {
        long elapsedTime = currentTimeMillis() - getTimeWhenDropped();
        if (elapsedTime > BOMB_LIFESPAN) {
            this.explode(); // Déclenche l'explosion si le temps est écoulé
        }
        return true;
    }

    @Override
    public void explode() {
        BombermanGame game = bomb.getGame();
        game.addMovable(new Explosion(game, getX(), getY()));
        bomb.spreadExplosion(2, getX(), getY(), 0, getHeight());
        bomb.spreadExplosion(3, getX(), getY(), 0, getHeight());
        game.removeMovable(this);
    }

}
