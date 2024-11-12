# *Bomberman* en JavaFX

## Description

Ce projet fournit une implantation de base du jeu *Bomberman* en *JavaFX*.
Pour pouvoir développer votre propre implantation de ce projet, vous devez
en créer une **divergence** en cliquant sur le bouton `Fork` en haut à droite
de cette page.

Lorsque ce sera fait, vous pourrez inviter les membres de votre groupe en tant
que *Developer* pour vous permettre de travailler ensemble sur ce projet.

## Membres du Projet

- [Romain Wallon](https://gitlab.univ-artois.fr/romain_wallon) : *Creator*
- [Rayane Rousseau](https://gitlab.univ-artois.fr/rayane_rousseau) : *Developer / Chef de projet*
- [Samuel Tittelein](https://gitlab.univ-artois.fr/samuel_tittelein) : *Developer*
- [Etienne Focquet](https://gitlab.univ-artois.fr/etienne_focquet) : *Developer*
- [Clément Goustiaux](https://gitlab.univ-artois.fr/clement_goustiaux) : *Developer*

## Consignes

Vous pouvez retrouver ci-dessous les liens vers les sujets de TP vous guidant
dans le développement de votre projet.

- [Lancement du projet](https://gitlab.univ-artois.fr/enseignements-rwa/modules/but-2/r3-04/tp/-/tree/main/TP03)

```plantuml
  @startuml
  package grA1.iutlens.lens_judge {

  package compilator {
  class CompilatorC {
  - static final String SOURCE_EXTENSION
  - static final String BINARY_EXTENSION
  - static CompilatorC instance
  + static getInstance()
  + boolean isCompatible(String filename)
  + String compiledFilename(String filename)
  + String getCommand(String filename)
  }

        class CompilatorCpp {
            - static final String[] SOURCE_EXTENSIONS
            - static final String BINARY_EXTENSION
            - static CompilatorCpp instance
            + static getInstance()
            + boolean isCompatible(String filename)
            + String compiledFilename(String filename)
            + String getCommand(String filename)
        }

        class CompilatorJava {
            - static final String SOURCE_EXTENSION
            - static final String BINARY_EXTENSION
            - static CompilatorJava instance
            + static getInstance()
            + boolean isCompatible(String filename)
            + String compiledFilename(String filename)
            + String getCommand(String filename)
        }

        class CompilatorPython {
            - static final String SOURCE_EXTENSION
            - static CompilatorPython instance
            + static getInstance()
            + boolean isCompatible(String filename)
            + String compiledFilename(String filename)
            + String getCommand(String filename)
        }

        abstract class CompilatorStrategy {
            + static String getFileExtension(String fileName)
            + static String replaceLast(String string, String toReplace, String replacement)
            + boolean isCompatible(String filename)
            + String compiledFilename(String filename)
            + String getCommand(String filename)
            + int compile(String filename) throws IOException, InterruptedException
        }

        CompilatorC --|> CompilatorStrategy
        CompilatorCpp --|> CompilatorStrategy
        CompilatorJava --|> CompilatorStrategy
        CompilatorPython --|> CompilatorStrategy
  }

  package verifier {
  interface VerifierStrategy {
  + boolean compareOutputs(List<String> programOutput, List<String> expectedOutput)
  }

        class CaseToleranceVerifierDecorator {
            - VerifierStrategy verifierStrategy
            + CaseToleranceVerifierDecorator(VerifierStrategy verifierStrategy)
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class ErrorHandlingVerifierStrategy {
            - VerifierStrategy verifierStrategy
            + ErrorHandlingVerifierStrategy(VerifierStrategy verifierStrategy)
            + compareOutputsWithErrors(List<String> standardOutput, List<String> expectedOutput, int exitCode): boolean
        }

        class ExternProcessVerifier {
            - String externalVerifierPath
            + ExternProcessVerifier(String externalVerifierPath)
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class ListSolutionVerifier {
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class OrderToleranceVerifier {
            - static OrderToleranceVerifier instance
            + static getInstance(): OrderToleranceVerifier
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class PrecisionToleranceVerifier {
            - static PrecisionToleranceVerifier instance
            - static final double DEFAULT_TOLERANCE
            - double tolerance
            + static getInstance(): PrecisionToleranceVerifier
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class StrictVerifier {
            - static StrictVerifier instance
            + static getInstance(): StrictVerifier
            + compareOutputs(List<String> programOutput, List<String> expectedOutput): boolean
        }

        class Verifier {
            - VerifierStrategy verifierStrategy
            + Verifier(VerifierStrategy verifierStrategy)
            + compareOutputs(List<String> standardOutput, List<String> expectedOutput, int exitCode): boolean
            + VerifierStrategy getVerifierStrategy(): VerifierStrategy
        }

        CaseToleranceVerifierDecorator --|> VerifierStrategy
        ErrorHandlingVerifierStrategy --> VerifierStrategy
        ExternProcessVerifier --|> VerifierStrategy
        ListSolutionVerifier --|> VerifierStrategy
        OrderToleranceVerifier --|> VerifierStrategy
        PrecisionToleranceVerifier --|> VerifierStrategy
        StrictVerifier --|> VerifierStrategy
        Verifier --> VerifierStrategy
  }

  package execution {
  interface IExecutionStrategy {
  + createProcess(String binaryPath): ProcessBuilder
  }

        class CExecution {
            + createProcess(String binaryPath): ProcessBuilder
        }

        class JavaExecution {
            + createProcess(String className): ProcessBuilder
        }

        class PythonExecution {
            + createProcess(String binaryPath): ProcessBuilder
        }

        class Execution {
            - IExecutionStrategy strategy
            + Execution(IExecutionStrategy strategy)
            + ProcessBuilder processPrepared(String filePath)
            + void setStrategy(IExecutionStrategy strategy)
        }

        class Runner {
            - String sourceFile
            - File inputFile
            - File expectedOutputFile
            - Verifier verifier
            - ProcessController processController
            - final long timeLimit
            + run(): void
            - boolean compileSourceFile(): boolean
            - void executeProgram(): void
            - boolean verifyOutput(): boolean
        }

        class RunnerBuilder {
            + setSourceFile(String sourceFile): RunnerBuilder
            + setInputFile(File inputFile): RunnerBuilder
            + setExpectedOutputFile(File expectedOutputFile): RunnerBuilder
            + setVerifier(Verifier verifier): RunnerBuilder
            + setProcessController(ProcessController processController): RunnerBuilder
            + build(): Runner
        }

        CExecution --|> IExecutionStrategy
        JavaExecution --|> IExecutionStrategy
        PythonExecution --|> IExecutionStrategy
        Execution --> IExecutionStrategy
        Runner --> Verifier
        Runner --> ProcessController
        RunnerBuilder --> Runner
  }

  package process {
  class ProcessAdapter {
  - ProcessBuilder processBuilder
  - Process process
  - StringBuilder standardOutput
  - StringBuilder errorOutput
  + ProcessAdapter(String sourceFile, File inputFile)
  + startProcess(String command): void
  + getStandardOutput(): String
  + getErrorOutput(): String
  + isRunning(): boolean
  + stopProcess(): void
  }

        interface ProcessController {
            + startProcess(String command): void
            + getStandardOutput(): String
            + getErrorOutput(): String
            + isRunning(): boolean
            + stopProcess(): void
        }

        class TimedProcessDecorator {
            - ProcessController processController
            - long timeLimit
            - boolean timedOut
            + TimedProcessDecorator(ProcessController processController, long timeLimit)
            + startProcess(String command): void
            + getStandardOutput(): String
            + getErrorOutput(): String
            + isRunning(): boolean
            + stopProcess(): void
            + hasTimedOut(): boolean
        }

        ProcessAdapter --|> ProcessController
        TimedProcessDecorator --|> ProcessController
  }

  package problem {
  class BuilderProblem {
  - List<TestCase> testCases
  - long timeout
  - long memoryLimit
  - VerifierStrategy verifier
  + addTestCase(TestCase testCase): BuilderProblem
  + setTimeout(long timeout): BuilderProblem
  + setMemoryLimit(long memoryLimit): BuilderProblem
  + setVerifier(VerifierStrategy verifier): BuilderProblem
  + List<TestCase> getTestCases(): List<TestCase>
  + long getTimeout(): long
  + long getMemoryLimit(): long
  + VerifierStrategy getVerifier(): VerifierStrategy
  + Problem build(): Problem
  }

        class Problem implements Iterable<TestCase> {
            - List<TestCase> testCases
            - long timeout
            - long memoryLimit
            - VerifierStrategy verifier
            + Problem(BuilderProblem builder)
            + VerifierStrategy getVerificator(): VerifierStrategy
            + void setVerificator(VerifierStrategy verificator)
            + long getMemoryLimit(): long
            + long getTimeout(): long
            + Iterable<TestCase> getTestCases(): Iterable<TestCase>
            + Iterator<TestCase> iterator()
            + void forEach(Consumer<? super TestCase> action)
            + Spliterator<TestCase> spliterator()
        }

        class TestCase {
            - String input
            - String expectedOutput
            + TestCase(String input, String expectedOutput)
            + String getInput(): String
            + String getExpectedOutput(): String
        }

        BuilderProblem --> Problem
        Problem --> VerifierStrategy
        Problem --> TestCase
  }

  package main {
  class Main {
  + main(String[] args): void
  }
  }

  Runner --> Main
  }
  @enduml
## Diagramme de classes

```plantuml
hide empty members

class Bomberman {
    - {static} GAME_WIDTH: int
    - {static} GAME_HEIGHT: int
    - {static} NB_ENEMIES: int

    + start(stage: Stage): void
    + {static} main(args: String[]): void
}
Bomberman --> BombermanController : << charge >>
Bomberman --> BombermanGame : << crée >>

class BombermanGame {
    + {static} RANDOM: Random
    + {static} DEFAULT_SPEED: int
    + {static} DEFAULT_BOMBS: int
    - width: int
    - height: int
    - spriteStore: ISpriteStore
    - gameMap: GameMap
    - player: IMovable
    - nbEnemies: int
    - remainingEnemies: int
    - movableObjects: List<IMovable>
    - animation: BombermanAnimation
    - controller: IBombermanController

    + BombermanGame(gameWidth: int, gameHeight: int, spriteStore: ISpriteStore, nbEnemies: int)
    + setController(controller: IBombermanController): void
    + getWidth(): int
    + getHeight(): int
    + prepare(): void
    - createMap(): GameMap
    + start(): void
    - createMovables(): void
    - initStatistics(): void
    - spawnMovable(movable: IMovable): void
    + moveUp(): void
    + moveRight(): void
    + moveDown(): void
    + moveLeft(): void
    + stopMoving(): void
    + dropBomb(): void
    + dropBomb(bomb: IMovable): void
    - getCellOf(movable: IMovable): Cell
    + getCellAt(x: int, y: int): Cell
    + addMovable(object: IMovable): void
    + removeMovable(object: IMovable): void
    - clearAllMovables(): void
    + enemyIsDead(enemy: IMovable): void
    + playerIsDead(): void
    - gameOver(message: String): void
}
BombermanGame o-- "1" ISpriteStore
BombermanGame *-- "1" GameMap
BombermanGame *-- "*" IMovable
BombermanGame *-- "1" BombermanAnimation
BombermanGame o-- "1" IBombermanController

class BombermanAnimation {
    - movableObjects: List<IMovable>
    - previousTimestamp: long

    + BombermanAnimation(movableObjects: List<IMovable>)
    + start(): void
    + handle(now: long): void
    - moveObjects(delta: long): void
    - checkCollisions(): void
}
BombermanAnimation o-- "*" IMovable

interface IBombermanController{
    + {abstract} setGame(game: BombermanGame): void
    + {abstract} prepare(map: GameMap): void
    + {abstract} bindScore(scoreProperty: IntegerExpression): void
    + {abstract} bindBombs(bombsProperty: IntegerExpression): void
    + {abstract} bindLife(lifeProperty: IntegerExpression): void
    + {abstract} addMovable(movable: IMovable): void
    + {abstract} gameOver(endMessage: String): void
    + {abstract} reset(): void
}

class BombermanController implements IBombermanController {
    - game: BombermanGame
    - stage: Stage
    - backgroundPane: GridPane
    - movingPane: Pane
    - score: Label
    - bombs: Label
    - life: Label
    - message: Label
    - started: boolean

    + setStage(stage: Stage): void
    + setGame(game: BombermanGame): void
    + prepare(map: GameMap): void
    - createBackground(map: GameMap): void
    - addKeyListeners(): void
    + bindScore(scoreProperty: IntegerExpression): void
    + bindBombs(bombsProperty: IntegerExpression): void
    + bindLife(lifeProperty: IntegerExpression): void
    + addMovable(movable: IMovable): void
    + gameOver(endMessage: String): void
    + reset(): void
}
BombermanController o-- "1" BombermanGame

interface IMovable {
    + {abstract} getWidth(): int
    + {abstract} getHeight(): int
    + {abstract} setX(xPosition: int): void
    + {abstract} getX(): int
    + {abstract} getXProperty(): DoubleProperty
    + {abstract} setY(yPosition: int): void
    + {abstract} getY(): int
    + {abstract} getYProperty(): DoubleProperty
    + {abstract} consume(): void
    + {abstract} isConsumed(): boolean
    + {abstract} isConsumedProperty(): BooleanProperty
    + {abstract} setHorizontalSpeed(speed: double): void
    + {abstract} getHorizontalSpeed(): double
    + {abstract} setVerticalSpeed(speed: double): void
    + {abstract} getVerticalSpeed(): double
    + {abstract} setSprite(sprite: Sprite): void
    + {abstract} getSprite(): Sprite
    + {abstract} getSpriteProperty(): ObjectProperty<Sprite>
    + {abstract} move(timeDelta: long): boolean
    + {abstract} isCollidingWith(other: IMovable): boolean
    + {abstract} collidedWith(other: IMovable): void
    + {abstract} explode(): void
    + {abstract} hitEnemy(): void
    + {abstract} self(): IMovable
}

abstract class AbstractMovable implements IMovable {
    - {static} MARGIN: int
    # game: BombermanGame
    # xPosition: DoubleProperty
    # yPosition: DoubleProperty
    # consumed: BooleanProperty
    # horizontalSpeed: double
    # verticalSpeed: double
    # sprite: ObjectProperty<Sprite>

    # AbstractMovable(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite)
    + getWidth(): int
    + getHeight(): int
    + setX(xPosition: int): void
    + getX(): int
    + getXProperty(): DoubleProperty
    + setY(yPosition: int): void
    + getY(): int
    + getYProperty(): DoubleProperty
    + consume(): void
    + isConsumed(): boolean
    + isConsumedProperty(): BooleanProperty
    + setHorizontalSpeed(speed: double): void
    + getHorizontalSpeed(): double
    + setVerticalSpeed(speed: double): void
    + getVerticalSpeed(): double
    + setSprite(sprite: Sprite): void
    + getSprite(): Sprite
    + getSpriteProperty(): ObjectProperty<Sprite>
    + move(timeDelta: long): boolean
    - isOnWall(x: int, y: int): boolean
    + isCollidingWith(other: IMovable): boolean
    + collidedWith(other: IMovable): void
    + explode(): void
    + hitEnemy(): void
    + self(): IMovable
    + hashCode(): int
    + equals(obj: Object): boolean
}
AbstractMovable *-- "1" BombermanGame
AbstractMovable o-- "1" Sprite

class Player extends AbstractMovable {
    - score: IntegerProperty
    - lives: IntegerProperty

    + Player(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite)
    + scoreProperty(): IntegerProperty
    + getLivesProperty(): IntegerProperty
    + getScoreProperty(): IntegerProperty
    + getBombsProperty(): IntegerProperty
    + getScore(): int
    + increaseScore(points: int): void
    + livesProperty(): IntegerProperty
    + getLives(): int
    + decreaseLives(points: int): void
    + collidedWith(other: IMovable): void
    + explode(): void
    + hitEnemy(): void
    + move(delta: long): boolean
}

class Enemy extends AbstractMovable {
    - {static} RANDOM: Random

    + Enemy(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite)
    + collidedWith(other: IMovable): void
    + explode(): void
    + hitEnemy(): void
    + move(delta: long): boolean
    - changeDirectionRandomly(): void
    - getRandomSpeed(): double
}

class GameMap {
    - height: int
    - width: int
    - cells: Cell[][]

    + GameMap(height: int, width: int)
    - init(): void
    + getHeight(): int
    + getWidth(): int
    + isOnMap(row: int, column: int): boolean
    + getAt(row: int, column: int): Cell
    + setAt(row: int, column: int, cell: Cell): void
    + getEmptyCells(): List<Cell>
}
GameMap *-- "*" Cell

class GameMapGenerator {
    - ss: SpriteStore
    - wall: Cell
    - lawn: Cell

    + fillMap(map: GameMap): GameMap
}
GameMapGenerator *-- "1" SpriteStore
GameMapGenerator *-- "1" Cell
GameMapGenerator *-- "1" GameMap

class Cell {
    - row: int
    - column: int
    - spriteProperty: ObjectProperty<Sprite>
    - wallProperty: ObjectProperty<Wall>

    + Cell(row: int, column: int)
    + Cell(sprite: Sprite)
    # Cell(wall: Wall)
    + getRow(): int
    + getColumn(): int
    + getWidth(): int
    + getHeight(): int
    + isEmpty(): boolean
    + getSprite(): Sprite
    + getSpriteProperty(): ObjectProperty<Sprite>
    + getWall(): Wall
    + getWallProperty(): ObjectProperty<Wall>
    + replaceBy(cell: Cell): void
}
Cell o-- "1" Sprite
Cell *-- "0..1" Wall

class Wall {
    - sprite: Sprite

    + Wall(sprite: Sprite)
    + getSprite(): Sprite
}

interface ISpriteStore {
    + {abstract} getSprite(identifier: String): Sprite
    + getSpriteSize(): int
}
ISpriteStore --> Sprite : << crée >>

class SpriteStore implements ISpriteStore {
    - spriteCache: Map<String, Sprite>
    + getSprite(identifier: String): Sprite
    - loadImage(name: String): Image
}

class Sprite {
    - image: Image

    + Sprite(image: Image)
    + getWidth(): int
    + getHeight(): int
    + getImage(): Image
    + draw(graphics: GraphicsContext, x: int, y: int): void
}

class Explosion {
    + {static} EXPLOSION_DURATION: long
    - explosionBegin: long
    + {static} spriteStore: SpriteStore

    + Explosion(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite)
    + Explosion(game: BombermanGame, xPosition: double, yPosition: double)
    + move(delta: long): void
    + collidedWith(other: IMovable): void
    + explode(): void
    + hitEnemy(): void
    + equals(other: Object): boolean
    + hashCode(): int
}

Explosion --|> AbstractMovable
Explosion --> SpriteStore : << utilise >>

class Bomb {
    + {static} BOMB_LIFESPAN: long
    + {static} spriteStore: SpriteStore
    - explosionSprite: Sprite
    - timeWhenDroped: long
    - xDropPosition: int
    - yDropPosition: int
    - explosionSize: int

    + Bomb(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite, explosionSprite: Sprite)
    + Bomb(game: BombermanGame, xPosition: double, yPosition: double, sprite: Sprite)
    + Bomb(game: BombermanGame, xPosition: double, yPosition: double)
    + move(delta: long): void
    + collidedWith(other: IMovable): void
    + explode(): void
    + spreadExplosion(): void
    + hitEnemy(): void
    + drop(xDropPosition: int, yDropPosition: int): void
    + equals(other: Object): boolean
    + hashCode(): int
}
Bomb --|> AbstractMovable
Bomb --> SpriteStore : << utilise >>
Bomb --> Explosion : << utilise >>
```

## Tâches réalisées

### TP n°3

| Fonctionnalité                                                | Terminée ? | Auteur(s)                         |
|---------------------------------------------------------------|------------|-----------------------------------|
| Représentation des ennemis                                    | Oui        | Clément Goustiaux                 |
| Intégration des ennemis dans la partie                        | Oui        | Clément Goustiaux                 |
| Représentation du joueur                                      | Oui        | Rousseau Rayane                   |
| Intégration du joueur dans la partie                          | Oui        | Rousseau Rayane                   |
| Représentation des bombes et explosion                        | Oui        | Samuel TITTELEIN                  |
| Intégration des bombes dans la partie                         | Oui        | Etienne Focquet, Samuel Tittelein |
| Création de la carte du jeu                                   | Oui        | Etienne Focquet                   |
| Fixation des bugs                                             | Oui        | (tous le groupe)                  |
| Mise à jour du README.md                                      | Oui        | Samuel TITTELEIN, Rousseau Rayane |

## Tâches réalisées

### TP n°4

| Fonctionnalité                                                                          | Terminée ? | Auteur(s)                                          |
|-----------------------------------------------------------------------------------------|------------|----------------------------------------------------|
| Variation des déplacements des ennemis / Patron de conception *Stratégie*               | Oui        | Rousseau Rayane                                    |
| Des ennemis plus résistants / Patron de conception *Décorateur*                         | Non        | Clément Goustiaux                                  |
| Rendre le joueur invulnérable après avoir subi un dommage / Patron de conception *Etat* | Oui        | Rousseau Rayane                                    |
| Différentes cartes pour le jeu / Patron de conception *Stratégie* ou *Proxy*            | Oui        | Rousseau Rayane, Etienne Focquet, Samuel Tittelein |
| Murs plus résistants / Patron de conception *Etat*                                      | Oui        | Samuel Tittelein                                   |
| Différents types de bombes / Patron de conception *Décorateur*                          | Oui        | Samuel Tittelein                                   |
| Mise à jour du README.md                                                                | Oui        | Rousseau Rayane                                    |