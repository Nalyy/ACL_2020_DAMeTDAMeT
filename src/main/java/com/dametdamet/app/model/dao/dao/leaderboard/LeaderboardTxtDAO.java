package com.dametdamet.app.model.dao.dao.leaderboard;

import com.dametdamet.app.model.leaderboard.Leaderboard;
import com.dametdamet.app.model.maze.Maze;

import java.io.*;

public enum LeaderboardTxtDAO implements AbstractFileLeaderboardDAO {

    INSTANCE;

    @Override
    public Leaderboard load(String nomFichier) {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        Leaderboard lb = new Leaderboard();
        try {
            fileIn = new FileInputStream(nomFichier);
            in = new ObjectInputStream(fileIn);
            lb = (Leaderboard) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        } finally { // Code appelé à la fin (même en cas d'exception)
            if(fileIn != null){
                try {
                    fileIn.close(); // On ferme la lecture du fichier
                } catch (IOException ignored) {
                }
            }
            if (in != null) {
                try {
                    in.close(); // On ferme le filtre de la lecture du fichier
                } catch (IOException ignored) {
                }
            }
        }
        return lb;
    }

    @Override
    public void save(Leaderboard leaderboard, String nomFichier) {
        try (FileOutputStream fileOut = new FileOutputStream(nomFichier); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(leaderboard);
        } catch (IOException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }
    }
}
