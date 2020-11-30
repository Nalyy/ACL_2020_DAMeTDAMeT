package com.dametdamet.app.model.dao.dao.leaderboard;

import com.dametdamet.app.model.leaderboard.Leaderboard;

public interface AbstractFileLeaderboardDAO {
    Leaderboard load(String nomFichier);
    void save(Leaderboard toSave, String nomFichier);
}
