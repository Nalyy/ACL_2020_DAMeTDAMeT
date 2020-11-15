package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests effectués :
 * Right, Boundary, Inverse, Error
 */
class TpTilesTest {
    private Maze maze;
    AbstractDAOFactory ab = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT);
    private PacmanGame game;

    private void loadGame(String sourceFile){
        String[] files = new String[1];
        files[0] = sourceFile;
        game = new PacmanGame("", files);
        maze = game.getMaze();
    }

    /**
     * Cette fonction considère que la case à la position donnée est une case de la classe
     * Teleportation.
     *
     * @return vrai si l'entity se trouve à la destination de la case TP
     */
    private boolean hasTeleported(Position position, Entity entity){
        Teleportation tpTile = (Teleportation) maze.whatIsIn(position);
        tpTile.applyEffect(game, entity);

        return entity.getPosition().equals(tpTile.getDestination());
    }

    /**
     * @return vrai si l'entity se trouve à la même position qu'avant.
     */
    private boolean hasNotTeleported(Position falseTpPosition, Entity entity){

        Position positionFalseTP = new Position(falseTpPosition);

        Position pastPosition = new Position(entity.getPosition());

        maze.whatIsIn(positionFalseTP).applyEffect(game, entity);

        return pastPosition.equals(entity.getPosition());
    }

    private Position rightLabyCorrectlyRead(){
        loadGame("tp_maze/tp_right.txt");

        Position positionFirstTp = new Position(5, 2);
        Assertions.assertEquals(maze.whatIsIn(positionFirstTp).getType(), TypeCase.TELEPORTATION);

        Position positionSecondTp = new Position(2, 5);
        Assertions.assertEquals(maze.whatIsIn(positionSecondTp).getType(), TypeCase.TELEPORTATION);

        Teleportation tpTile = (Teleportation) maze.whatIsIn(positionFirstTp);
        Assertions.assertEquals(tpTile.getDestination(), positionSecondTp);

        return positionFirstTp;
    }

    /**
     * Le héros se téléporte à l'endroit voulu.
     */
    @Test
    public void tpRightHero(){
        Position positionFirstTp = rightLabyCorrectlyRead();

        boolean hasTeleported = hasTeleported(positionFirstTp, game.getHero());
        Assertions.assertTrue(hasTeleported);
    }

    /**
     * Le monstre se téléporte à l'endroit voulu.
     */
    @Test
    public void tpRightMonster(){
        Monster monster = new Monster(maze.getInitialPositionPlayer(), RandomMove.INSTANCE);

        Position positionFirstTp = rightLabyCorrectlyRead();

        boolean hasTeleported = hasTeleported(positionFirstTp, monster);
        Assertions.assertTrue(hasTeleported);
    }


    /**
     * Une seule case de TP est crée et le héros va dessus.
     * Rien ne devrait se passer.
     */
    @Test
    public void tpBoundary1TileHero(){
        loadGame("tp_maze/tp_1tile.txt");

        Position positionFalseTP = new Position(2, 5);

        boolean hasNotTeleported = hasNotTeleported(positionFalseTP, game.getHero());

        Assertions.assertTrue(hasNotTeleported);
    }

    /**
     * Une seule case de TP est crée et le monstre va dessus.
     * Rien ne devrait se passer.
     */
    @Test
    public void tpBoundary1TileMonster(){
        loadGame("tp_maze/tp_1tile.txt");

        Position positionFalseTP = new Position(2, 5);
        Monster monster = new Monster(maze.getInitialPositionPlayer(), RandomMove.INSTANCE);

        boolean hasNotTeleported = hasNotTeleported(positionFalseTP, monster);

        Assertions.assertTrue(hasNotTeleported);
    }

    /**
     * Trois cases de TP sont crées et le héros va sur la 3e.
     * Rien ne devrait  se passer.
     *
     * Par contre, la TP entre la deuxième et la première devrait fonctionner.
     */
    @Test
    public void tpBoundary3TilesHero(){
        loadGame("tp_maze/tp_3tiles.txt");

        // la 3e case de TP est la x = 2; y = 5
        Position positionFalseTP = new Position(2, 5);

        Position positionTrueTP = new Position(5, 2);

        Assertions.assertTrue(hasNotTeleported(positionFalseTP, game.getHero()));
        Assertions.assertTrue(hasTeleported(positionTrueTP, game.getHero()));
    }

    /**
     * Trois cases de TP sont crées et le monstre va sur la 3e.
     * Rien ne devrait  se passer.
     *
     * Par contre, la TP entre la deuxième et la première devrait fonctionner.
     */
    @Test
    public void tpBoundary3TilesMonster(){
        loadGame("tp_maze/tp_3tiles.txt");

        Monster monster = new Monster(maze.getInitialPositionPlayer(), RandomMove.INSTANCE);

        // la 3e case de TP est la x = 2; y = 5
        Position positionFalseTP = new Position(2, 5);

        Position positionTrueTP = new Position(5, 2);

        Assertions.assertTrue(hasNotTeleported(positionFalseTP, monster));
        Assertions.assertTrue(hasTeleported(positionTrueTP, monster));
    }

    /**
     * Le héros peut prendre le TP dans les deux sens.
     */
    @Test
    public void tpInverseHero(){
        rightLabyCorrectlyRead();

        Position positionFirstTp = new Position(5, 2);
        Position positionSecondTp = new Position(2, 5);

        // Le héros va de la 1ere tp à la 2e tp
        Assertions.assertTrue(hasTeleported(positionFirstTp, game.getHero()));
        Assertions.assertEquals(game.getHero().getPosition(), positionSecondTp);

        // Le héros va de la 2e tp à la 1ere tp
        Assertions.assertTrue(hasTeleported(positionSecondTp, game.getHero()));
        Assertions.assertEquals(game.getHero().getPosition(), positionFirstTp);
    }

    /**
     * Le monstre peut prendre le TP dans les deux sens.
     */
    @Test
    public void tpInverseMonster(){
        rightLabyCorrectlyRead();
        Monster monster = new Monster(maze.getInitialPositionPlayer(), RandomMove.INSTANCE);

        Position positionFirstTp = new Position(5, 2);
        Position positionSecondTp = new Position(2, 5);

        // Le héros va de la 1ere tp à la 2e tp
        Assertions.assertTrue(hasTeleported(positionFirstTp, monster));
        Assertions.assertEquals(monster.getPosition(), positionSecondTp);

        // Le héros va de la 2e tp à la 1ere tp
        Assertions.assertTrue(hasTeleported(positionSecondTp, monster));
        Assertions.assertEquals(monster.getPosition(), positionFirstTp);
    }

}
