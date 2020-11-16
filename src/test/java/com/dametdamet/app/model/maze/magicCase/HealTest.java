package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HealTest {

    private PacmanGame game;
    private Heal heal;

    private void loadGame() {
        String[] mazes = new String[1];
        mazes[0] = "maze_magicCase.txt";
        this.game = new PacmanGame("helpFilePacman.txt", mazes);
        this.heal = (Heal) game.getMaze().whatIsIn(new Position(4, 1));
    }

    /**
     * La case n'est pas pressée après l'initialisation.
     */
    @Test
    public void testInitNotPressed() {
        loadGame();
        assertFalse(heal.isPressed());
    }

    /**
     * La case est pressée après activation.
     */
    @Test
    public void testPressedAfterActivation() {
        loadGame();
        heal.applyEffect(game, game.getHero());
        assertTrue(heal.isPressed());
    }

    /**
     * Le héro gagne un point de vie s'il n'a pas toute sa vie.
     */
    @Test
    public void testHeal() {
        loadGame();

        Hero hero = (Hero) game.getHero();
        hero.loseHP(1);
        int prevHP = hero.getHP();
        heal.applyEffect(game, hero);

        assertEquals(prevHP + 1, hero.getHP());
    }

    /**
     * Le héro ne gagne pas de point de vie s'il a tous ses points de vie.
     */
    @Test
    public void testFullHeal() {
        loadGame();

        Hero hero = (Hero) game.getHero();
        int prevHP = hero.getHP();
        heal.applyEffect(game, hero);

        assertEquals(prevHP, hero.getHP());
    }

    /**
     * La case ne s'active pas si elle a été pressée.
     */
    @Test
    public void testDeadIfPressed() {
        loadGame();
        Hero hero = (Hero) game.getHero();
        hero.loseHP(2);
        heal.applyEffect(game, game.getHero());

        int prevHP = hero.getHP();
        heal.applyEffect(game, game.getHero());

        assertEquals(prevHP, hero.getHP());
    }

    /**
     * La case renvoie une exception si le jeu PacmanGame est null.
     */
    @Test
    public void testNullGame() {
        Hero hero = new Hero(new Position(1, 1), 3);
        assertThrows(NullPointerException.class, () -> heal.applyEffect(null, hero));
    }

    /**
     * La classe renvoie une exception si le héro est null.
     */
    @Test
    public void testNullHero() {
        loadGame();
        assertThrows(NullPointerException.class, () -> heal.applyEffect(game, null));
    }
}
