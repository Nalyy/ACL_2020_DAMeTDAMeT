package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.model.maze.Maze;

public interface MoveStrategy {
    void setMaze(Maze maze);

    Command getNextCommand(Monster monster);

}
