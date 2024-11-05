package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;

public class BigBomb extends AbstractBombDecorateur {

    public BigBomb(BombermanGame game, double xPosition, double yPosition,
            Sprite explosionSprite, int explosionSize) {

        super(game, xPosition, yPosition, explosionSprite, explosionSize);
    }

    @Override
    public int getExplosionSize() {

        return 0;
    }

    @Override
    public Sprite getBombSprite() {

        return null;
    }

}
