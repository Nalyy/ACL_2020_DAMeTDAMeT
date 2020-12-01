package com.dametdamet.app.model;

import com.dametdamet.app.model.leaderboard.Leaderboard;
import com.dametdamet.app.model.leaderboard.Score;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {

    private Leaderboard board;

    /**
     * On ajoute un score au palmarès.
     */
    @Test
    public void testAddScore() {
        board = new Leaderboard(1);
        Score score = new Score("", 0);
        Iterator<Score> iterator = board.iterator();
        assertFalse(iterator.hasNext());
        board.add(score);
        assertTrue(iterator.hasNext());
    }

    /**
     * On ajoute pas un score supérieur au plus petit score d'un palmarès plein.
     */
    @Test
    public void testAddBetterScore() {
        board = new Leaderboard(1);
        Score score = new Score("", 0);
        board.add(score);
        Score betterScore = new Score("", 1);
        board.add(betterScore);
        Iterator<Score> iterator = board.iterator();
        Score savedScore = iterator.next();
        assertEquals(betterScore.getValue(), savedScore.getValue());
    }

    /**
     * On n'ajoute pas un score inférieur au plus petit score d'un palmarès plein.
     */
    @Test
    public void testScoreTooSmall() {
        board = new Leaderboard(1);
        Score score = new Score("", 1);
        board.add(score);
        Score smallScore = new Score("", 0);
        board.add(smallScore);
        Iterator<Score> iterator = board.iterator();
        Score savedScore = iterator.next();
        assertEquals(score.getValue(), savedScore.getValue());
    }

    /**
     * On n'ajoute pas de score si la taille du palmarès est nulle.
     */
    @Test
    public void testSizeZero() {
        board = new Leaderboard(0);
        Score score = new Score("", 0);
        Iterator<Score> iterator = board.iterator();
        assertFalse(iterator.hasNext());
        board.add(score);
        assertFalse(iterator.hasNext());
    }

    /**
     * On n'ajoute pas de score si le palmarès est plein.
     */
    @Test
    public void testBoardFull() {
        board = new Leaderboard(1);
        Score score1 = new Score("", 0);
        board.add(score1);
        Score score2 = new Score("", 0);
        board.add(score2);
        Iterator<Score> iterator = board.iterator();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

}
