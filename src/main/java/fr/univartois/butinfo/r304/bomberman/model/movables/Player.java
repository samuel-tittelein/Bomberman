package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.IBomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.state.PlayerState;
import fr.univartois.butinfo.r304.bomberman.model.movables.state.VulnerableState;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La classe {@link Player} représente le personnage joueur dans le jeu.
 * Elle gère les points de vie, le score, les bombes et les états d'invulnérabilité du joueur.
 */
public class Player extends AbstractMovable {

    private final ObservableList<IBomb> bombs = FXCollections.observableArrayList();
    private final IntegerProperty score;
    private final IntegerProperty lives;
    private PlayerState state; // État actuel du joueur

    private final Sprite originalAppearance;
    private final Sprite invulnerableAppearance;

    /**
     * Crée une nouvelle instance de {@link Player}.
     *
     * @param game               Le jeu auquel appartient le joueur.
     * @param xPosition          La position initiale en x.
     * @param yPosition          La position initiale en y.
     * @param sprite             Apparence normale du joueur.
     * @param invulnerableSprite Apparence du joueur en état d'invulnérabilité.
     */
    public Player(BombermanGame game, double xPosition, double yPosition, Sprite sprite, Sprite invulnerableSprite) {
        super(game, xPosition, yPosition, sprite);
        this.state = new VulnerableState(); // État initial : vulnérable
        this.originalAppearance = sprite;
        this.invulnerableAppearance = invulnerableSprite;
        this.score = new SimpleIntegerProperty(0);
        this.lives = new SimpleIntegerProperty(3);
    }

    public IntegerProperty getLivesProperty() {
        return lives;
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public void addScore(int points) {
        this.score.set(this.score.get() + points);
    }

    public IntegerBinding getBombsProperty() {
        return Bindings.size(this.bombs);
    }

    public void addBomb(IBomb bomb) {
        bombs.add(bomb);
    }

    public ObservableList<IBomb> getBombs() {
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

    public int getLives() {
        return lives.get();
    }

    /**
     * Réduit les points de vie du joueur.
     * Si les points de vie tombent à zéro ou en dessous, la partie se termine.
     *
     * @param points Le nombre de points de vie à retirer.
     */
    public void decreaseLives(int points) {
        this.lives.set(this.lives.get() - points);
        if (lives.get() <= 0) {
            game.playerIsDead();
        }
    }

    @Override
    public void explode() {
        takeDamage();
    }

    @Override
    public void hitEnemy() {
        takeDamage();
    }

    @Override
    public void collidedWith(IMovable other) {
    }

    /**
     * Modifie l'état actuel du joueur.
     *
     * @param newState Le nouvel état du joueur.
     */
    public void setState(PlayerState newState) {
        this.state = newState;
        updateAppearance(); // Met à jour immédiatement l'apparence pour refléter le nouvel état
    }

    /**
     * Applique des dommages au joueur en fonction de son état actuel.
     */
    public void takeDamage() {
        state.takeDamage(this);
    }

    /**
     * Met à jour l'apparence du joueur en fonction de son état actuel.
     */
    public void updateAppearance() {
        state.updateAppearance(this);
    }

    /**
     * Obtient l'apparence normale du joueur.
     *
     * @return Le sprite représentant l'apparence normale.
     */
    public Sprite getOriginalAppearance() {
        return originalAppearance;
    }

    /**
     * Obtient l'apparence invulnérable du joueur.
     *
     * @return Le sprite représentant l'apparence invulnérable.
     */
    public Sprite getInvulnerableAppearance() {
        return invulnerableAppearance;
    }
}
