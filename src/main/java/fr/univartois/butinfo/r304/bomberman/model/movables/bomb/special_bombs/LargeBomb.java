package fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;


public class LargeBomb extends AbstractBombDecorator {

    public LargeBomb(Bomb bomb) {
        super(bomb);
        this.decorate();
    }

    private void decorate() {
        setBombSprite(spritestore.getSprite("large-bomb"));
        setExplosionSize(8);
    }


}
