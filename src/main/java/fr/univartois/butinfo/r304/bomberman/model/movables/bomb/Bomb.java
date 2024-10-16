package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;
import java.util.logging.Logger;

import static java.lang.System.currentTimeMillis;

public class Bomb extends AbstractMovable {

    public static final long BOMB_LIFESPAN = 4500; // Durée de vie de 4.5 secondes
    //public static final long COOLDOWN_TIME = 4500;
    //private static long lastDropTime = 0; // Temps de la dernière bombe déposée// Cooldown de 5 secondes
    public static final SpriteStore spriteStore = new SpriteStore();
    private final int explosionSize;
    private long timeWhenDropped;
    Logger logger = Logger.getLogger("tkt.log");


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
        super(game, xPosition, yPosition, explosionSprite);
        this.explosionSize = explosionSize;
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
        // Vérifie si le temps écoulé depuis le dépôt de la bombe dépasse BOMB_LIFESPAN
        long elapsedTime = currentTimeMillis() - timeWhenDropped;
        if (elapsedTime > BOMB_LIFESPAN) {
            logger.info("ms , elapsedTime : " + elapsedTime + "ms");
            explode(); // Déclenche l'explosion si le temps est écoulé
        }
        return true; // La bombe ne se déplace pas, donc toujours vrai
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
        logger.info("bombe explosé à : " + currentTimeMillis() );
        game.addMovable(new Explosion(game, getX(), getY()));
        logger.info("Bombe explose au coord : "+ getX() + ", " + getY());
        for (int direction = 0; direction < 4; direction++) {
            spreadExplosion(direction, getX(), getY(), 0, getHeight());
        }
        game.removeMovable(this);
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
    public void spreadExplosion(int direction, int x, int y, int iteration, int cellHeight) {
        int limitMaxX = game.getWidth() - getWidth();
        int limitMaxY = game.getHeight() - getHeight();

        iteration++;
        if (iteration > explosionSize) {
            // On arrête la propagation de l'explosion si la taille maximale est atteinte.
            return;
        }

        // Mise à jour des coordonnées selon la direction
        switch (direction) {
            case 0: // droite
                x += cellHeight;
                break;
            case 1: // gauche
                x -= cellHeight;
                break;
            case 2: // bas
                y += cellHeight;
                break;
            case 3: // haut
                y -= cellHeight;
                break;
            default:
              break;
        }

        // Vérifie si on dépasse les limites de la carte.
        if ((x < 0) || (x > limitMaxX) || (y < 0) || (y > limitMaxY)) {
            return;  // On a atteint la limite de la carte.
        }

        // Ajout de l'explosion dans la cellule.
        game.addMovable(new Explosion(game, x, y));

        // Vérification de la cellule actuelle pour voir s'il y a un mur.
        Cell currentCell = game.getCellAt(x, y);
        if (currentCell.getWall() != null) {
            // Si un mur est présent, le remplacer par une cellule vide (herbe, par exemple).
            currentCell.replaceBy(new Cell(spriteStore.getSprite("lawn")));
            return;  // On arrête la propagation de l'explosion car un mur a été détruit.
        }

        // Si aucun mur n'est rencontré, continue à propager l'explosion dans la même direction.
        spreadExplosion(direction, x, y, iteration, cellHeight);
    }


    /**
     * Méthode appelée lorsque la bombe touche un ennemi.
     * Dans cette implémentation, ne fait rien.
     */
    @Override
    public void hitEnemy() {

    }


    public void drop(Cell cell) {


        long currentTime = currentTimeMillis();
        //if (currentTime - lastDropTime >= COOLDOWN_TIME) {
        timeWhenDropped = currentTime;
        logger.info("bombe larguée à : " + timeWhenDropped );

        int x = cell.getColumn() * cell.getWidth();
        int y = cell.getRow() * cell.getHeight();

        setX(x);
        setY(y);

        game.addMovable(this);
            //lastDropTime = currentTime;
        //}
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Bomb bomb = (Bomb) object;
        return timeWhenDropped == bomb.timeWhenDropped && explosionSize == bomb.explosionSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeWhenDropped, explosionSize);
    }
}
