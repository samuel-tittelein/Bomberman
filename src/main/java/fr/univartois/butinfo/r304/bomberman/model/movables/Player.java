package fr.univartois.butinfo.r304.bomberman.model.movables;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.IMovable;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player extends AbstractMovable {

    private final ObservableList<Bomb> bombs = FXCollections.observableArrayList();
    private IntegerProperty score;
    private IntegerProperty lives;

    private long lastHitTime = 0; // Timestamp pour le cooldown de collision
    private static final int COOLDOWN_TIME = 3000; // 3 secondes de cooldown (sinon il perd trop de pdv trop rapidement)

    public Player(BombermanGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
        this.score = new SimpleIntegerProperty(0);
        this.lives = new SimpleIntegerProperty(3);
    }

    public IntegerProperty scoreProperty() {
        return score;
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

    public IntegerProperty livesProperty() {
        return lives;
    }

    public int getLives() {
        return lives.get();
    }

    public void decreaseLives(int points) {
        this.lives.set(this.lives.get() - points);
        if (lives.get() <= 0) {
            game.playerIsDead();
        }
    }

    @Override
    public void explode() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHitTime >= COOLDOWN_TIME) {
            decreaseLives(1);
            lastHitTime = currentTime; // Mise à jour du dernier temps de collision
        }
    }

    @Override
    public void hitEnemy() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHitTime >= COOLDOWN_TIME) {
            // TODO à sortir de player
            decreaseLives(1);
            lastHitTime = currentTime; // Mise à jour du dernier temps de collision
        }
    }

    @Override
    public boolean isEnemy() {
        return super.isEnemy();
    }

    @Override
    public boolean move(long delta) {
        return super.move(delta);
    }

    @Override
    public void collidedWith(IMovable other) {

    }
}
