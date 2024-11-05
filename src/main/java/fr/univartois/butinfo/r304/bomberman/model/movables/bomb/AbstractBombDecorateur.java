package fr.univartois.butinfo.r304.bomberman.model.movables.bomb;

import fr.univartois.butinfo.r304.bomberman.model.BombermanGame;
import fr.univartois.butinfo.r304.bomberman.model.map.Cell;
import fr.univartois.butinfo.r304.bomberman.model.map.walls.Wall;
import fr.univartois.butinfo.r304.bomberman.view.Sprite;
import fr.univartois.butinfo.r304.bomberman.view.SpriteStore;

public abstract class AbstractBombDecorateur implements IBomb {
    private IBomb bomb;
    private int height;
    private int width;
    private BombermanGame game;

    private static SpriteStore spriteStore = new SpriteStore();
    public AbstractBombDecorateur(BombermanGame game, double xPosition, double yPosition, Sprite explosionSprite, int explosionSize) {
        this.bomb = new Bomb(game, xPosition, yPosition, explosionSprite, explosionSize);
        this.height = bomb.getHeight();
        this.width = bomb.getWidth();
        this.game  = bomb.getGame();
    }

    public void drop(Cell cell) {
        bomb.drop(cell);
    }
    public void spreadExplosion(int direction, int x, int y, int iteration, int cellHeight) {

        int limitMaxX = game.getWidth() - bomb.getWidth();
        int limitMaxY = game.getHeight() - bomb.getHeight();

        iteration++;
        if (iteration > bomb.getExplosionSize()) {
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
            currentCell.getWall().nextState(); // Le mur passe à l'état suivant.
            Wall replace = currentCell.getWall(); // Le mur qui a été détruit.
            if (replace.getState() == null) { // Le mur est un mur vide. TODO : trouver une autre implémentation
                currentCell.replaceBy(new Cell(spriteStore.getSprite("lawn")));
            } else {
                currentCell.replaceBy(new Cell(replace));
            }
            //currentCell.replaceBy(new Cell(spriteStore.getSprite("lawn")));
            return;  // On arrête la propagation de l'explosion car un mur a été détruit.
        }

        // Si aucun mur n'est rencontré, continue à propager l'explosion dans la même direction.
        spreadExplosion(direction, x, y, iteration, cellHeight);
    }

    @Override
    public BombermanGame getGame() {

        return game;
    }

    @Override
    public int getWidth() {

        return width;
    }

    @Override
    public int getHeight() {

        return height;
    }
}
