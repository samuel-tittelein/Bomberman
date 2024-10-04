package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

import java.util.Objects;

public class Explosion extends AbstractMovable {

    public static final double EXPLOSION_DURATION = 1000; // 1 seconde
    private long explosionBegin;

    public Explosion(BombermanGame game, double xPosition,
            double yPosition, Sprite sprite){

        super(game, xPosition, yPosition, sprite);
        this.explosionBegin = System.currentTimeMillis();
    }

    @Override
    public boolean move(long delta) {
        if(System.currentTimeMillis() - explosionBegin > EXPLOSION_DURATION ) {
            this.consume();
        }
        return true;
    }

    @Override
    public void collidedWith(IMovable other) {
        //TODO
    }

    @Override
    public void explode() {
        //TODO
    }

    @Override
    public void hitEnemy() {
        //TODO
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Explosion explosion = (Explosion) o;
        return explosionBegin == explosion.explosionBegin;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), explosionBegin);
    }

}
