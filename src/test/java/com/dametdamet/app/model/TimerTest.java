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

    @Test
    void isFinishedSansTop(){
        assert(timer.isFinished());

        timer.pause();

        timer.continueTimer();

        assert (timer.isFinished());
    }

    @Test
    void pauseAndContinueTimer() throws InterruptedException {

        //cas où on fait une pause qu'on continue après
        timer.top(100);

        timer.pause();

        Thread.sleep(100);

        assert(!timer.isFinished());

        timer.continueTimer();

        assert(!timer.isFinished());

        Thread.sleep(100);

        assert(timer.isFinished());



        //cas où on relance un nouveau top après avoir fait une pause
        timer.top(100);

        timer.pause();

        timer.top(50);

        Thread.sleep(50);

        assert (timer.isFinished());
    }


}