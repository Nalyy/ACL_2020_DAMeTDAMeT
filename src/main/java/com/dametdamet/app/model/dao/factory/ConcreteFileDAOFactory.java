package com.dametdamet.app.model.dao.factory;

import com.dametdamet.app.model.dao.dao.leaderboard.AbstractFileLeaderboardDAO;
import com.dametdamet.app.model.dao.dao.leaderboard.LeaderboardTxtDAO;
import com.dametdamet.app.model.dao.dao.maze.AbstractMazeDAO;
import com.dametdamet.app.model.dao.dao.maze.TxtDAO;

public class ConcreteFileDAOFactory extends AbstractDAOFactory{

    @Override
    public AbstractMazeDAO getMazeDAO() {
        return TxtDAO.INSTANCE;
    }

    @Override
    public AbstractFileLeaderboardDAO getLeaderboardDAO() {
        return LeaderboardTxtDAO.INSTANCE;
    }
}
