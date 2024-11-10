package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;

/**
 * This class decorate Bomb
 * it implement the large bomb (it explosion size is 2 times the explosion size of a normal bomb)
 * when it's dropped, it displays large-bomb.png
 */
public class LargeBomb extends AbstractBombDecorator {

    public LargeBomb(Bomb bomb) {
        super(bomb);
        this.decorate();
    }

    private void decorate() {
        setBombSprite(spritestore.getSprite("large-bomb"));
        setExplosionSize(2*getExplosionSize());
    }
}
