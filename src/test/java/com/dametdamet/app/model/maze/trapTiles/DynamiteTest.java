package com.dametdamet.app.model.maze.trapTiles;


import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.maze.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/** Ces tests sont valables si la range de la case Dynamite est de 1 */
public class DynamiteTest {

    private PacmanGame game;
    private Maze maze;
    private Hero hero;
    private Position dynamitePosition;

    @BeforeEach
    public void setUp(){
        game = new PacmanGame("", new String[]{"maze_dynamite.txt"});
        maze = game.getMaze();
        hero = (Hero) game.getHero();
        dynamitePosition = new Position(3, 1);
    }

    public void setMazeOutOfRange(){
        game = new PacmanGame("", new String[]{"maze_dynamiteOutRange.txt"});
        maze = game.getMaze();
        hero = (Hero) game.getHero();
        // La position de la dynamite est identique
    }

    public void triggerTrap(){
        maze.whatIsIn(dynamitePosition).applyEffect(game,hero);
    }

    @Test
    public void rightHero(){
        /* Au début, le héros a son max de pv */
        int maxHpHero = hero.getMaxHp();
        Assertions.assertEquals(maxHpHero, game.getHero().getHP());

        /* Le héros va aller sur la case dynamite */
        triggerTrap();

        /* Le héros a pris un point de dégâts */
        Assertions.assertEquals(maxHpHero-1,game.getHero().getHP());
    }

    @Test
    public void rightMonsters(){

        /* Au début, le jeu contient 7 monstres vivants */
        int compteur = 0;

        Iterator<Entity> iterator = game.getMonstersIterator();
        while(iterator.hasNext()){
            Entity entity = iterator.next();
            compteur++;
            Assertions.assertTrue(entity.getHP()>0);
        }

        Assertions.assertEquals(7, compteur);

        triggerTrap();

        iterator = game.getMonstersIterator();
        /* Tous les monstres sont censés ne plus avoir de pv */
        while(iterator.hasNext()){
            Assertions.assertEquals(iterator.next().getHP(), 0);
        }
    }

    @Test
    public void rightScore(){
        int NB_MONSTERS_RANGE = 7;
        triggerTrap();

        Assertions.assertEquals(NB_MONSTERS_RANGE*100, game.getScore());
    }

    @Test
    public void rightOutOfRangeHero(){
        setMazeOutOfRange();
        int maxHpHero = hero.getMaxHp();

        triggerTrap();

        /* Comme le héros est hors range, il ne devrait pas y avoir de soucis */
        Assertions.assertEquals(maxHpHero, hero.getHP());
    }

    @Test
    public void rightOutOfRangeMonster(){
        setMazeOutOfRange();
        triggerTrap();

        Iterator<Entity> iterator = game.getMonstersIterator();
        while(iterator.hasNext()){
            Entity monster = iterator.next();
            Assertions.assertEquals(monster.getMaxHp(), monster.getHP());
        }
    }

    /**
     * Il ne faut pas que le piège s'active plus d'une fois
     */
    @Test
    public void errorHero(){
        triggerTrap();
        triggerTrap();
        triggerTrap();
        Assertions.assertEquals(hero.getMaxHp()-1, hero.getHP());
    }

    /**
     * Idem que au-dessus
     */
    @Test
    public void errorMonsters(){
        triggerTrap();
        triggerTrap();
        triggerTrap();

        Iterator<Entity> iterator = game.getMonstersIterator();
        while(iterator.hasNext()){
            Entity monster = iterator.next();
            Assertions.assertEquals(monster.getMaxHp()-1, monster.getHP());
        }
    }

}
