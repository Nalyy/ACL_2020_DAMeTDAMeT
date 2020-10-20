package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

public interface MoveStrategy {
    void setMaze(Maze maze);

    Command getNextCommand(Monster monster);

}
