package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.IBomb;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La classe {@link Player} représente le personnage joueur dans le jeu.
 * Elle gère les points de vie, le score, les bombes et l'état temporaire d'invulnérabilité du joueur.
 */
public class Player extends AbstractMovable {

    private final ObservableList<IBomb> bombs = FXCollections.observableArrayList();
    private final IntegerProperty score;
    private final IntegerProperty lives;

    private long lastHitTime = 0; // Timestamp pour le dernier coup pris
    private static final int COOLDOWN_TIME = 3000; // Durée d'invulnérabilité en ms

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
     * Applique des dommages au joueur en fonction de son invulnérabilité temporaire.
     */
    public void takeDamage() {
        long currentTime = System.currentTimeMillis();

        // Si le cooldown est écoulé, le joueur prend des dégâts
        if (currentTime - lastHitTime >= COOLDOWN_TIME) {
            decreaseLives(1);
            lastHitTime = currentTime; // Redémarre le cooldown
            setSprite(invulnerableAppearance); // Change d'apparence

            // Remet l'apparence originale après le cooldown
            new Thread(() -> {
                try {
                    Thread.sleep(COOLDOWN_TIME);
                    setSprite(originalAppearance);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}