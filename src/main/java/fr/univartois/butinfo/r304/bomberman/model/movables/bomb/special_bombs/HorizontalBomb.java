package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Explosion;

import static fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb.BOMB_LIFESPAN;
import static java.lang.System.currentTimeMillis;

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
    public boolean move(long timeDelta) {
        long elapsedTime = currentTimeMillis() - bomb.getTimeWhenDropped();
        if (elapsedTime > BOMB_LIFESPAN) {
            this.explode(); // Déclenche l'explosion si le temps est écoulé
        }
        return true;
    }

    @Override
    public void explode() {
        BombermanGame game = bomb.getGame();

        //game.addMovable(new Explosion(game, getX(), getY()));

        bomb.spreadExplosion(0, getX(), getY(), 0, getHeight());
        bomb.spreadExplosion(2, getX(), getY(), 0, getHeight());
        System.out.println("ok");

        game.removeMovable(this);
    }

}
