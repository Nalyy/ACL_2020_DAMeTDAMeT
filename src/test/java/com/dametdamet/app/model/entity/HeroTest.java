package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private Hero hero;

    @BeforeEach
    void setUp() {
        hero = new Hero(new Position(0, 0));
    }

    /*
     * Test du gain
     */

    @Test
    void rightGainHPPlus1() {
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }

    @Test
    void right2GainHPMinusPlus1() {
        hero.loseHP(1);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps + 1, hero.getHP());
    }
    @Test
    void right3GainHPMinusMinusPlus1() {
        hero.loseHP(1);
        hero.loseHP(1);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps+1, hero.getHP());
    }
    @Test
    void right4GainHPMinus2Plus1() {
        hero.loseHP(2);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps+1, hero.getHP());
    }

    @Test
    void boundary1GainHPMAXINT() {
        int hps = hero.getHP();
        hero.gainHP(Integer.MAX_VALUE);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }

    @Test
    void boundary1GainHPMinus5() {
        int hps = hero.getHP();
        hero.gainHP(-1);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }

    @Test
    void boundary1GainHPMININT() {
        int hps = hero.getHP();
        hero.gainHP(Integer.MIN_VALUE);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }


    /*
     * Test du lose
     */


    @Test
    void rightLoseHP1() {
        int hps = hero.getHP();
        hero.loseHP(1);
        assertEquals(hps-1, hero.getHP());
    }

    @Test
    void rightLoseHP2() {
        int hps = hero.getHP();
        hero.loseHP(2);
        assertEquals(hps-2, hero.getHP());
    }

    @Test
    void right2LoseHPMinusPlus1() {
        hero.loseHP(1);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps + 1, hero.getHP());
    }
    @Test
    void right3LoseHPMinusMinusPlus1() {
        hero.loseHP(1);
        hero.loseHP(1);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps+1, hero.getHP());
    }
    @Test
    void right4LoseHPMinus2Plus1() {
        hero.loseHP(2);
        int hps = hero.getHP();
        hero.gainHP(1);
        assertEquals(hps+1, hero.getHP());
    }

    @Test
    void boundary1LoseHPMAXINT() {
        hero.loseHP(Integer.MAX_VALUE);
        assertEquals(0, hero.getHP());
    }

    @Test
    void boundary1LoseHPMinus5() {
        int hps = hero.getHP();
        hero.loseHP(-1);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }

    @Test
    void boundary1LoseHPMININT() {
        int hps = hero.getHP();
        hero.gainHP(Integer.MIN_VALUE);
        assertEquals(hps, hero.getHP());
        assertEquals(hps, hero.getMaxHp());
    }
}