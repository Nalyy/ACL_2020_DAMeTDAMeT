package com.dametdamet.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    Timer timer;
    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    @Test
    void isFinished() throws InterruptedException {
        timer.top(100);

        assert(!timer.isFinished());

        Thread.sleep(100);

        assert (timer.isFinished());
    }
}