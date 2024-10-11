package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

import java.util.Objects;

public class Explosion extends AbstractMovable {

    public static final double EXPLOSION_DURATION = 500; // 1/2 seconde
    private final long explosionBegin;
    public static SpriteStore spriteStore = new SpriteStore();

    /**
     * Crée une nouvelle instance de l'objet Explosion.
     *
     * @param game Le jeu Bomberman auquel cette explosion est liée.
     * @param xPosition La position en X où l'explosion se déclenche.
     * @param yPosition La position en Y où l'explosion se déclenche.
     * @param sprite Le sprite représentant visuellement l'explosion.
     */
    public Explosion(BombermanGame game, double xPosition,
                     double yPosition, Sprite sprite){

        super(game, xPosition, yPosition, sprite);
        this.explosionBegin = System.currentTimeMillis();
    }

    /**
     * Crée une nouvelle instance de l'objet Explosion.
     *
     * @param game Le jeu Bomberman auquel cette explosion est liée.
     * @param xPosition La position en X où l'explosion se déclenche.
     * @param yPosition La position en Y où l'explosion se déclenche.
     * <p>
     * L'attribut sprite est par défaut initialisé par {@link SpriteStore}
     * avec pour sprite le sprite "explosion".
     * </p>
     */
    public Explosion(BombermanGame game, double xPosition,
                     double yPosition){

        super(game, xPosition, yPosition, spriteStore.getSprite("explosion"));
        this.explosionBegin = System.currentTimeMillis();
    }

    /**
     * Gère le déplacement de l'explosion. Si la durée de l'explosion dépasse
     * EXPLOSION_DURATION, elle est consommée (retirée du jeu).
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour.
     * @return Toujours true car l'explosion ne change pas de position mais peut être consommée.
     */
    @Override
    public boolean move(long delta) {
        if(System.currentTimeMillis() - explosionBegin > EXPLOSION_DURATION ) {
            this.consume();
        }
        return true;
    }

    /**
     * Gère la collision de l'explosion avec un autre objet mobile.
     * Si une collision est détectée, l'autre objet subit une explosion.
     *
     * @param other L'objet mobile avec lequel cette explosion entre en collision.
     */
    @Override
    public void collidedWith(IMovable other) {
        other.explode();
    }


    /**
     * Méthode appelée lorsque cette explosion est elle-même censée exploser.
     * Ne fait rien dans ce cas, car l'explosion a déjà lieu.
     */
    @Override
    public void explode() {
    }

    /**
     * Méthode appelée lorsque cette explosion touche un ennemi.
     * Dans cette implémentation, ne fait rien. C'est fait de la classe {@link Ennemi}
     */
    @Override
    public void hitEnemy() {
    }

    /**
     * Vérifie si cet objet Explosion est égal à un autre objet.
     *
     * @param o L'objet à comparer avec cette explosion.
     * @return true si l'objet est égal, false sinon.
     */
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

    /**
     * Calcule le code de hachage pour cet objet Explosion.
     *
     * @return Le code de hachage basé sur explosionBegin et le hash du parent.
     */
    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), explosionBegin);
    }

}
