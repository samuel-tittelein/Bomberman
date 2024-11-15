/**
 * Ce logiciel est distribué à des fins éducatives.
 * <p>
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 * <p>
 * (c) 2022-2024 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.r304.bomberman.model;

import fr.univartois.butinfo.r304.bomberman.model.movables.EnemyHPDecorator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.univartois.butinfo.r304.bomberman.model.map.*;
import fr.univartois.butinfo.r304.bomberman.model.movables.Enemy;
import fr.univartois.butinfo.r304.bomberman.model.movables.Player;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.Bomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.IBomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs.HorizontalBomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs.LargeBomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.bomb.special_bombs.VerticalBomb;
import fr.univartois.butinfo.r304.bomberman.model.movables.strategy.ChaseMovementStrategy;
import fr.univartois.butinfo.r304.bomberman.model.movables.strategy.RandomMovementStrategy;
import fr.univartois.butinfo.r304.bomberman.view.ISpriteStore;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import javafx.animation.AnimationTimer;

/**
 * La classe {@link BombermanGame} gère une partie du jeu Bomberman.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class BombermanGame {



    // Constantes pour la position initiale du joueur. (à modifier potentiellement plus tard)
    public static final double PLAYER_INITIAL_X = 100.0;  // Position initiale en X
    public static final double PLAYER_INITIAL_Y = 100.0;  // Position initiale en Y

    private IMapGenerator mapGenerator; // Générateur de carte choisi pour cette partie

    /**
     * Définit le générateur de carte à utiliser pour cette partie.
     *
     * @param mapGenerator Le générateur de carte.
     */
    public void setMapGenerator(IMapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }

    private final List<IMapGenerator> generators = Arrays.asList(
            new DefaultMapGenerator(),
            new MapCroixGenerator(),
            new RandomMapGenerator(),
            new SpiralMapGenerator(),
            new MazeMapGenerator(),
            new IslandMapGenerator()
    );

    /**
     * Le génarateur de nombres aléatoires utilisé dans le jeu.
     */
    public static final Random RANDOM = new Random();

    /**
     * La vitesse de déplacement du joueur (en pixels/s).
     */
    public static final int DEFAULT_SPEED = 75;

    /**
     * Le nombre de bombes initialement disponibles pour le joueur.
     */
    public static final int DEFAULT_BOMBS = 500;

    /**
     * La largeur de la carte du jeu (en pixels).
     */
    private final int width;

    /**
     * La hauteur de la carte du jeu (en pixels).
     */
    private final int height;

    /**
     * L'instance de {@link ISpriteStore} permettant de créer les {@link Sprite} du jeu.
     */
    private final ISpriteStore spriteStore;

    /**
     * La carte du jeu.
     */
    private GameMap gameMap;

    /**
     * Le personnage du joueur.
     */
    private Player player;

    /**
     * Le nombre d'ennemis initialement dans le jeu.
     */
    private final int nbEnemies;

    /**
     * Le nombre d'ennemis restant dans le jeu.
     */
    private int remainingEnemies;

    /**
     * La liste des objets pouvant se déplacer dans le jeu.
     */
    private final List<IMovable> movableObjects = new CopyOnWriteArrayList<>();

    /**
     * L'animation du jeu, qui s'assure que les différents objets se déplacent.
     */
    private final AnimationTimer animation = new BombermanAnimation(movableObjects);

    /**
     * Le contrôleur du jeu.
     */
    private IBombermanController controller;

    /**
     * Crée une nouvelle instance de BombermanGame.
     *
     * @param gameWidth La largeur de la carte du jeu.
     * @param gameHeight La hauteur de la carte du jeu.
     * @param spriteStore L'instance de {@link ISpriteStore} permettant de créer les
     *        {@link Sprite} du jeu.
     * @param nbEnemies Le nombre d'ennemis dans le jeu.
     */
    public BombermanGame(int gameWidth, int gameHeight, ISpriteStore spriteStore, int nbEnemies) {
        this.width = gameWidth;
        this.height = gameHeight;
        this.spriteStore = spriteStore;
        this.nbEnemies = nbEnemies;
    }

    /**
     * Modifie le contrôleur avec lequel interagir pour mettre à jour l'affichage.
     *
     * @param controller Le contrôleur avec lequel interagir.
     */
    public void setController(IBombermanController controller) {
        this.controller = controller;
    }

    /**
     * Donne l'instance de {@link ISpriteStore} permettant de créer les {@link Sprite} du jeu..
     *
     * @return L'instance de {@link ISpriteStore} permettant de créer les {@link Sprite} du jeu..
     */
    public ISpriteStore getSpriteStore() {
        return spriteStore;
    }

    /**
     * Donne la largeur de la carte du jeu (en pixels).
     *
     * @return La largeur de la carte du jeu.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Donne la hauteur de la carte du jeu (en pixels).
     *
     * @return La hauteur de la carte du jeu.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Prépare une partie de Bomberman avant qu'elle ne démarre.
     */
    public void prepare() {
        gameMap = createMap();

        if (mapGenerator != null) {
            mapGenerator.fillMap(gameMap); // Utilise le générateur de carte sélectionné
        } else {
            new DefaultMapGenerator().fillMap(gameMap); // Générateur par défaut si aucun n’est défini
        }

        controller.prepare(gameMap);
    }

    /**
     * Crée la carte du jeu, en respectant les dimensions de la fenêtre.
     *
     * @return La carte du jeu ayant été créée.
     */
    private GameMap createMap() {
        int cellSize = spriteStore.getSpriteSize();
        int mapWidthInCells = width / cellSize;
        int mapHeightInCells = height / cellSize;

        return new GameMap(mapHeightInCells, mapWidthInCells);
    }

    /**
     * Démarre la partie de Bomberman.
     */
    public void start() {
        // Sélection d'une carte aléatoirement
        this.mapGenerator = generators.get(RANDOM.nextInt(generators.size()));

        createMovables();
        initStatistics();
        animation.start();
    }

    /**
     * Crée les différents objets présents au début de la partie et pouvant se déplacer.
     */
    private void createMovables() {
        // On commence par enlever tous les éléments mobiles encore présents.
        clearAllMovables();

        // Création et placement du joueur sur la carte.
        Sprite playerSprite = spriteStore.getSprite("guy"); // Sprite normal
        Sprite invulnerableSprite = spriteStore.getSprite("guy_invulnerable"); // Sprite pour l'invulnérabilité

        // Initialisation du joueur avec le sprite normal et invulnérable
        player = new Player(this, PLAYER_INITIAL_X, PLAYER_INITIAL_Y, playerSprite, invulnerableSprite);

        movableObjects.add(player);
        spawnMovable(player);

        // Ajout des bombes initiales pour le joueur.
        for (int i = 0; i < DEFAULT_BOMBS; i++) {
            // ajoute une bombe horizontale
            player.addBomb(new HorizontalBomb(new Bomb(this, player.getXPosition(), player.getYPosition())));
            // ajoute une bombe verticale
            player.addBomb(new VerticalBomb(new Bomb(this, player.getXPosition(), player.getYPosition())));
            // ajoute une bombe normale avec pour taille par défaut 3
            Bomb bomb = new Bomb(this, player.getXPosition(), player.getYPosition(), spriteStore.getSprite("bomb"), 3); // Taille de l'explosion fixée à 3
            player.addBomb(bomb);
            // ajoute une grosse bombe (taille par défaut 8)
            player.addBomb(new LargeBomb(new Bomb(this, player.getXPosition(), player.getYPosition(), 3)));
        }

        // Création des ennemis avec des stratégies de déplacement
        for (int i = 0; i < nbEnemies; i++) {
            Enemy enemy = new Enemy(this, 0, 0, spriteStore.getSprite("goblin"));

            if (i % 2 == 0) {
                // Ennemi avec mouvement aléatoire
                enemy.setMovementStrategy(new RandomMovementStrategy(5, this)); // Ajuste le 5 selon le pourcentage souhaité de changement de direction
            } else {
                // Ennemi agressif
                enemy.setMovementStrategy(new ChaseMovementStrategy(player, this));
            }

            // Décorateur pour ajouter des points de vie à l'ennemi
            EnemyHPDecorator decoratedEnemy = new EnemyHPDecorator(3, this, enemy);
            movableObjects.add(decoratedEnemy);
            spawnMovable(decoratedEnemy);
        }
    }

    /**
     * Initialie les statistiques de cette partie.
     */
    private void initStatistics() {
        // Lier les propriétés du joueur avec celles du contrôleur.
        controller.bindLife(player.getLivesProperty());
        controller.bindScore(player.getScoreProperty());
        controller.bindBombs(player.getBombsProperty());

        // Mettre à jour le nombre d'ennemis restants.
        remainingEnemies = nbEnemies;
    }


    /**
     * Fait apparaître un objet pouvant se déplacer sur la carte du jeu.
     *
     * @param movable L'objet à faire apparaître.
     */
    private void spawnMovable(IMovable movable) {
        List<Cell> spawnableCells = gameMap.getEmptyCells();
        if (!spawnableCells.isEmpty()) {
            Cell cell = spawnableCells.get(RANDOM.nextInt(spawnableCells.size()));
            movable.setX(cell.getColumn() * spriteStore.getSpriteSize());
            movable.setY(cell.getRow() * spriteStore.getSpriteSize());
            addMovable(movable);
        }
    }

    /**
     * Déplace le personnage du joueur vers le haut.
     */
    public void moveUp() {
        stopMoving();
        player.setVerticalSpeed(-DEFAULT_SPEED);
    }

    /**
     * Déplace le personnage du joueur vers la droite.
     */
    public void moveRight() {
        stopMoving();
        player.setHorizontalSpeed(DEFAULT_SPEED);
    }

    /**
     * Déplace le personnage du joueur vers le bas.
     */
    public void moveDown() {
        stopMoving();
        player.setVerticalSpeed(DEFAULT_SPEED);
    }

    /**
     * Déplace le personnage du joueur vers la gauche.
     */
    public void moveLeft() {
        stopMoving();
        player.setHorizontalSpeed(-DEFAULT_SPEED);
    }

    /**
     * Arrête le déplacement du joueur.
     */
    public void stopMoving() {
        player.setVerticalSpeed(0);
        player.setHorizontalSpeed(0);
    }

    /**
     * Dépose une bombe sur la tuile où se trouve le joueur, et programme l'explosion de
     * cette bombe.
     */
    public void dropBomb() {
        if (!player.getBombs().isEmpty()) {
            dropBomb(player.getBombs().removeFirst());
        }
    }

    /**
     * Dépose une bombe sur la tuile où se trouve le joueur, et programme l'explosion de
     * cette bombe.
     *
     * @param bomb La bombe à déposer.
     */
    public void dropBomb(IBomb bomb) {
        bomb.drop(getCellOf(player));
    }


    /**
     * Récupére la cellule correspondant à la position d'un objet mobile.
     * Il s'agit de la cellule sur laquelle l'objet en question occupe le plus de place.
     *
     * @param movable L'objet mobile dont la cellule doit être récupérée.
     *
     * @return La cellule occupée par l'objet mobile.
     */
    private Cell getCellOf(IMovable movable) {
        // On commence par récupérer la position du centre de l'objet.
        int midX = movable.getX() + (movable.getWidth() / 2);
        int midY = movable.getY() + (movable.getHeight() / 2);
        return getCellAt(midX, midY);
    }

    /**
     * Donne la cellule à la position donnée sur la carte.
     *
     * @param x La position en x de la cellule.
     * @param y La position en y de la cellule.
     *
     * @return La cellule à la position donnée.
     */
    public Cell getCellAt(int x, int y) {
        // On traduit cette position en position dans la carte.
        int row = y / spriteStore.getSpriteSize();
        int column = x / spriteStore.getSpriteSize();

        // On récupère enfin la cellule à cette position dans la carte.
        return gameMap.getAt(row, column);
    }

    /**
     * Ajoute un objet pouvant se déplacer dans le jeu.
     *
     * @param object L'objet à ajouter.
     */
    public void addMovable(IMovable object) {
        movableObjects.add(object);
        controller.addMovable(object);
    }

    /**
     * Supprime un objet pouvant se déplacer dans le jeu.
     *
     * @param object L'objet à supprimer.
     */
    public void removeMovable(IMovable object) {
        movableObjects.remove(object);
        object.consume();
    }

    /**
     * Supprime tous les objets pouvant se déplacer dans le jeu.
     */
    private void clearAllMovables() {
        for (IMovable movable : movableObjects) {
            movable.consume();
        }
        movableObjects.clear();
    }

    /**
     * Met à jour le score du joueur lorsqu'un ennemi est tué.
     * Si c'était le dernier, le joueur gagne la partie.
     *
     * @param enemy L'ennemi qui a été tué.
     *
     */
    public void enemyIsDead(IMovable enemy) {
        // Mettre à jour le score du joueur (exemple : +100 points par ennemi).
        player.addScore(100);

        // Décrémenter le nombre d'ennemis restants.
        remainingEnemies--;

        // Retirer l'ennemi du jeu.
        removeMovable(enemy);

        // Vérifier si tous les ennemis ont été tués.
        if (remainingEnemies == 0) {
            // Tous les ennemis ont été tués : la partie est terminée.
            gameOver("YOU WIN!");
        }
    }


    /**
     * Termine la partie lorsque le joueur est tué.
     */
    public void playerIsDead() {
        gameOver("YOU HAVE BEEN KILLED!");
    }

    /**
     * Termine la partie en cours.
     *
     * @param message Le message indiquant le résultat de la partie.
     */
    private void gameOver(String message) {
        animation.stop();
        controller.gameOver(message);
    }

}
