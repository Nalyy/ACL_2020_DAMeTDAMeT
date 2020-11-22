package com.dametdamet.app.model.leaderboard;

import java.io.Serializable;

public class Score implements Serializable {

    private final String date;
    private final int value;

    public Score(String date, int value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return date + " : " + value;
    }

    public int getValue() {
        return value;
    }
}
