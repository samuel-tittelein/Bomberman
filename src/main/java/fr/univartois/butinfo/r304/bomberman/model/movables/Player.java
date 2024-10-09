package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player extends AbstractMovable {

    // Propriétés du joueur
    private IntegerProperty score;
    private IntegerProperty lives;

    /**
     * Crée une nouvelle instance de Player.
     *
     * @param game      Le jeu dans lequel le joueur évolue.
     * @param xPosition La position en x initiale du joueur.
     * @param yPosition La position en y initiale du joueur.
     * @param sprite    L'instance de {@link Sprite} représentant le joueur.
     */
    public Player(BombermanGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
        this.score = new SimpleIntegerProperty(0);  // Score initialisé à 0
        this.lives = new SimpleIntegerProperty(3);  // Points de vie initialisés à 3
    }

    // Accesseurs pour le score
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Renvoie la propriété observable des vies du joueur.
     *
     * @return IntegerProperty des vies du joueur.
     */
    public IntegerProperty getLivesProperty() {
        return lives;
    }

    /**
     * Renvoie la propriété observable du score du joueur.
     *
     * @return IntegerProperty du score du joueur.
     */
    public IntegerProperty getScoreProperty() {
        return score;
    }

    /**
     * Renvoie la propriété observable des bombes disponibles du joueur.
     *
     * @return IntegerProperty des bombes du joueur.
     */
    public IntegerProperty getBombsProperty() {
        return bombs;
    }


    public int getScore() {
        return score.get();
    }

    public void increaseScore(int points) {
        this.score.set(this.score.get() + points);
    }

    // Accesseurs pour les points de vie
    public IntegerProperty livesProperty() {
        return lives;
    }

    public int getLives() {
        return lives.get();
    }

    public void decreaseLives(int points) {
        this.lives.set(this.lives.get() - points);
    }

    // Gestion des collisions avec d'autres objets
    @Override
    public void collidedWith(IMovable other) {
        // On appelle une méthode dédiée à l'objet autre
        other.interactWithPlayer(this);
    }



    @Override
    public void explode() {
        // Si le joueur explose (par exemple à cause d'une bombe), il perd un point de vie
        decreaseLives(1);
        if (lives.get() <= 0) {
            game.removeMovable(this);  // Si les points de vie atteignent 0, le joueur est retiré du jeu
        }
    }

    @Override
    public void hitEnemy() {
        // Gestion si le joueur touche un ennemi
        decreaseLives(1);
    }

    @Override
    public boolean move(long delta) {
        // Implémentation spécifique pour le déplacement du joueur, peut-être plus directe que celle de l'ennemi
        return super.move(delta);
    }
}
