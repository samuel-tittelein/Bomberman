package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Explosion;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player extends AbstractMovable {

    // Déclaration de la liste observable de bombes
    private final ObservableList<Bomb> bombs = FXCollections.observableArrayList();

    // Propriétés du joueur
    private IntegerProperty score;
    private IntegerProperty lives;

    /**
     * Crée une nouvelle instance de Player.
     */
    public Player(BombermanGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
        this.score = new SimpleIntegerProperty(0); // Initialisation du score à 0
        this.lives = new SimpleIntegerProperty(3); // Exemple d'initialisation des vies
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

    // Méthode pour ajouter des points au score
    public void addScore(int points) {
        this.score.set(this.score.get() + points);
    }

    /**
     * Renvoie la propriété observable des bombes disponibles du joueur.
     *
     * @return IntegerProperty des bombes du joueur.
     */
    public IntegerProperty getBombsProperty() {
        return new SimpleIntegerProperty(bombs.size());
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public ObservableList<Bomb> getBombs() {
        return bombs;
    }


    public double getXPosition() {
        return getX();
    }

    public double getYPosition() {
        return getY();
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
    public void interactWithPlayer(Player player) {

    }

    @Override
    public boolean isEnemy() {
        return super.isEnemy();
    }

    @Override
    public boolean move(long delta) {
        // Implémentation spécifique pour le déplacement du joueur, peut-être plus directe que celle de l'ennemi
        return super.move(delta);
    }

    @Override
    public void collidedWith(IMovable other) {
        // Appelle la méthode d'interaction pour l'autre objet
        other.interactWithPlayer(this);
    }

}
