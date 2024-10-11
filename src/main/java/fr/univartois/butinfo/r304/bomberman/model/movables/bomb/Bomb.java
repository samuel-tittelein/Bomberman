package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

import java.util.Objects;

public class Bomb extends AbstractMovable {

    public static final long BOMB_LIFESPAN = 2000; // 2 secondes
    public static SpriteStore spriteStore = new SpriteStore();
    private final Sprite explosionSprite;
    private long timeWhenDroped;
    private int xDropPosition, yDropPosition;
    private final int explosionSize;

    /**
     * Crée une nouvelle bombe avec un sprite spécifique et une taille d'explosion définie.
     *
     * @param game Le jeu Bomberman auquel cette bombe est liée.
     * @param xPosition La position en X où la bombe est placée.
     * @param yPosition La position en Y où la bombe est placée.
     * @param sprite Le sprite représentant visuellement la bombe.
     * @param explosionSprite Le sprite de l'explosion qui sera affiché après l'explosion.
     * @param explosionSize La taille de l'explosion (en nombre de cases).
     */
    public Bomb(BombermanGame game, double xPosition, double yPosition, Sprite sprite, Sprite explosionSprite, int explosionSize) {
        super(game, xPosition, yPosition, sprite);
        this.explosionSprite  = explosionSprite;
        this.explosionSize = explosionSize;
    }

    /**
     * Crée une nouvelle bombe avec un sprite spécifique et une taille d'explosion définie.
     *
     * @param game Le jeu Bomberman auquel cette bombe est liée.
     * @param xPosition La position en X où la bombe est placée.
     * @param yPosition La position en Y où la bombe est placée.
     * @param explosionSprite Le sprite de l'explosion qui sera affiché après l'explosion.
     * @param explosionSize La taille de l'explosion (en nombre de cases).
     * <p>
     * L'attribut sprite est par défaut initialisé par {@link SpriteStore}
     * avec pour sprite le sprite "bomb".
     * </p>
     */
    public Bomb(BombermanGame game, double xPosition, double yPosition, Sprite explosionSprite, int explosionSize) {
        this(game, xPosition, yPosition, explosionSprite,spriteStore.getSprite("bomb"), explosionSize);
    }

    /**
     * Crée une nouvelle bombe avec un sprite spécifique et une taille d'explosion définie.
     *
     * @param game Le jeu Bomberman auquel cette bombe est liée.
     * @param xPosition La position en X où la bombe est placée.
     * @param yPosition La position en Y où la bombe est placée.
     * @param explosionSize La taille de l'explosion (en nombre de cases).
     * <p>
     * Les attributs sprite et explosionSprite sont par défaut initialisé par {@link SpriteStore}
     * avec pour sprite les sprites "bomb" et "explosion" respectivement.
     * </p>
     */

    public Bomb(BombermanGame game, double xPosition, double yPosition, int explosionSize) {
        this(game, xPosition, yPosition, spriteStore.getSprite("bomb"), spriteStore.getSprite("explosion"), explosionSize);
    }

    @Override
    public void interactWithPlayer(Player player) {
        player.decreaseLives(1);
    }

    /**
     * Gère le déplacement de la bombe. Si le temps écoulé depuis le dépôt de la bombe dépasse
     * BOMB_LIFESPAN, la bombe explose.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour.
     * @return Toujours true, car la bombe ne bouge pas mais peut exploser.
     */
    @Override
    public boolean move(long delta) {
        if (System.currentTimeMillis() - timeWhenDroped > BOMB_LIFESPAN) {
            explode();
        }
        return true;
    }


    /**
     * Gère la collision de la bombe avec un autre objet mobile.
     * Dans cette implémentation, ne fait rien.
     *
     * @param other L'objet mobile avec lequel la bombe entre en collision.
     */
    @Override
    public void collidedWith(IMovable other) {

    }

    /**
     * Déclenche l'explosion de la bombe, en créant une explosion à sa position et
     * en répandant l'explosion dans plusieurs directions selon sa taille.
     */
    @Override
    public void explode() {

        game.addMovable(new Explosion(game, xDropPosition, yDropPosition, explosionSprite));

        for (int direction = 0; direction < 4; direction++) {
            spreadExplosion(direction, xDropPosition, yDropPosition, 0);
        }
        consume();
    }

    /**
     * Répand l'explosion dans une direction donnée jusqu'à atteindre la taille définie
     * de l'explosion ou un obstacle (comme un mur).
     *
     * @param direction La direction dans laquelle l'explosion se propage (0: droite, 1: gauche, 2: bas, 3: haut).
     * @param x La position X actuelle de l'explosion.
     * @param y La position Y actuelle de l'explosion.
     * @param iteration Le nombre d'itérations d'explosion (taille de l'explosion).
     * <p>
     * J'ai décidé de faire une implémentation récursive ! Cette méthode facilitera
     * l'implémentation des different types d'explosion.
     * </p>
     */
    public void spreadExplosion(int direction, int x, int y, int iteration) {
        int limitMaxX = game.getWidth() - getWidth();
        int limitMaxY = game.getHeight() - getHeight();

        iteration++;
        if (iteration > explosionSize) {
            // c'est la seule manière que j'aie trouvée pour arrêter de répendre l'explosion
            return;
        }

        // On met a jour la position en fonction de la direction
        switch (direction) {
            case 0: // droite
                x += 1;
                break;
            case 1: // gauche
                x -= 1;
                break;
            case 2: // bas
                y += 1;
                break;
            case 3: // haut
                y -= 1;
        }

        if ((x < 0) || (x > limitMaxX) || (y < 0) || (y > limitMaxY)) {
            // On a atteint la limite sur l'axe x ou y.
            return;
        }

        // On peut répendre l'explosion.
        game.addMovable(new Explosion(game, x, y, explosionSprite));

        // On vérifie s'il y a un obstacle.
        if (game.getCellAt(x, y).getWall() != null) {
            // L'objet a atteint un mur.
            Cell cell = new Cell(spriteStore.getSprite("lawn"));
            game.getCellAt(x, y).replaceBy(cell);
            return ; // On arrête de répendre l'explosion.
        }

        // On répand l'explosion dans la même direction si on a pas rencontré un mur.
        spreadExplosion(direction, x, y, iteration);
    }


    /**
     * Méthode appelée lorsque la bombe touche un ennemi.
     * Dans cette implémentation, ne fait rien.
     */
    @Override
    public void hitEnemy() {

    }

    /**
     * Dépose la bombe à la position spécifiée (xDropPosition, yDropPosition)
     * et enregistre le temps exact de dépôt.
     *
     * @param xDropPosition La position X où la bombe est déposée.
     * @param yDropPosition La position Y où la bombe est déposée.
     */
    public void drop(int xDropPosition, int yDropPosition) {
        timeWhenDroped = System.currentTimeMillis();
        this.xDropPosition = xDropPosition;
        this.yDropPosition = yDropPosition;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Bomb bomb = (Bomb) object;
        return timeWhenDroped == bomb.timeWhenDroped && xDropPosition == bomb.xDropPosition && yDropPosition == bomb.yDropPosition && explosionSize == bomb.explosionSize && Objects.equals(explosionSprite, bomb.explosionSprite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), explosionSprite, timeWhenDroped, xDropPosition, yDropPosition, explosionSize);
    }
}
