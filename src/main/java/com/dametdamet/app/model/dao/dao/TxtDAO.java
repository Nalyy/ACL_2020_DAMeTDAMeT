package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.graphic.factory.ImageFactory;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.Teleportation;
import com.dametdamet.app.model.maze.TypeCase;
import com.dametdamet.app.model.maze.magicCase.Heal;
import com.dametdamet.app.model.maze.magicCase.SpawnerChest;
import com.dametdamet.app.model.maze.magicCase.Time;
import com.dametdamet.app.model.maze.trapCases.Damage;
import com.dametdamet.app.model.maze.trapCases.SpawnerMonster;

import java.io.*;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Random;


public enum TxtDAO implements AbstractFileDAO {

    INSTANCE;

    private static final char EMPTY = '0';
    private static final char WALL = '1';
    private static final char STAIRS = '2';

    private static final char POS_JOUEUR = 'X';
    private static final char POS_MONSTRE = 'Y';

    private static final char HEAL = 'H';
    private static final char TIME = 'T';
    private static final char SPAWNER_CHEST = 'C';
    private static final char POS_CHEST = 'B';

    private static final char DAMAGE = 'D';

    private static final char SPAWNER_MONSTERS = 'S';
    private static final char POS_MONSTERS = 'M';

    private static final char TELEPORTATION = 'P';

    @Override
    public Maze load(String nomFichier) {
        BufferedReader fr = null;
        Maze laby = new Maze();
        try {
            //URL x = getClass().getResource("/" + nomFichier);
            InputStream x = getClass().getResourceAsStream("/" + nomFichier);
            if(x != null)
                fr = new BufferedReader(new InputStreamReader(x)); // Permet de lire le fichier
            else
                throw new IOException("Impossible de lire le fichier, le fichier n'existe pas. " + "\"/" + nomFichier + "\"");

            // Premier check des tailles du fichier
            int[] widthHeight = trouverTaillesTabCases(fr);

            fr.close(); // On ferme le
            x = getClass().getResourceAsStream("/" + nomFichier); // On retourne au début du fichier
            fr = new BufferedReader(new InputStreamReader(x)); // Permet de lire le fichier

            laby = createTabCases(fr, widthHeight[0], widthHeight[1]);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        } finally { // Code appelé à la fin (même en cas d'exception)
            if (fr != null) {
                try {
                    fr.close(); // On ferme la lecture du fichier
                } catch (IOException e) {
                    System.out.println("Erreur lecture fichier : " + e.getMessage());
                }
            }
        }
        return laby;
    }

    /**
     * Donne les tailles du tableau (indice 0 pour width et 1 pour height)
     * @param fr le lecteur de fichier
     * @return le tableau qui contient la taille en largeur et hauteur
     * @throws IOException exception du lecteur
     */
    private int[] trouverTaillesTabCases(Reader fr) throws IOException {
        int width = 0;
        int maxWidth = 0;
        int height = 1; // Pour la première ligne qui n'est pas comptabilisée
        int c;

        while((c = fr.read()) != -1){ // On récupère le caractère suivant dans le fichier
            if(c == 13 || c == '\n'){ // 13 car caractère inconnu à la fin de ligne
                if(c == 13){ // Si on tombe sur le caractère inconnu on skip le '\n' (car '\n' est juste après)
                    fr.skip(1);
                }
                height++;
                if(maxWidth < width){ // On garde la longueur de la plus grande ligne
                    maxWidth = width;
                }
                width = 0; // On reset la ligne
            }else{
                width++;
            }
        }
        return new int[]{maxWidth, height};
    }

    /**
     * Lis le fichier pour créer le tableau de Cases (avec les cases correspondantes)
     * @param fr le lecteur du fichier
     * @param width la taille du tableau en largeur
     * @param height la taille du tableau en hauteur
     * @return la tableau de cases du fichier
     * @throws IOException exception du lecteur
     */
    private Maze createTabCases(Reader fr, int width, int height) throws IOException {
        // On commence la construction du labyrinthe
        Random randomGenerator = new Random();
        Case[][] laby = new Case[width][height];
        ArrayList<Position> casesTP = new ArrayList<>();
        int c;

        Maze maze = new Maze();

        int x = 0;
        int y = 0;
        Position positionJoueur = new Position(0, 0);
        while((c = fr.read()) != -1){ // On va construire le labyrinthe (tableau à deux dim)
            if(c == 13 || c == '\n'){ // idem, caractère 13 avant le saut à la ligne
                if(c == 13){ // On skip le saut à la ligne
                    fr.skip(1);
                }
                if(x < width){ // Si on est pas au bout de la ligne on remplis de cases vides
                    for(; x < width ; x++){
                        laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                    }
                }
                y++;
                x = 0;
            } else {
                switch (c) {
                    case POS_JOUEUR:
                        positionJoueur = new Position(x, y);
                        laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        break;
                    case POS_MONSTRE:
                        maze.addInitialMonsterPosition(new Position(x, y));
                        laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        break;
                    case EMPTY:
                        laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        break;
                    case WALL:
                        laby[x][y] = new Case(TypeCase.WALL,randomGenerator.nextInt(ImageFactory.NB_WALL_IMG));
                        break;
                    case STAIRS:
                        laby[x][y] = new Case(TypeCase.STAIRS);
                        break;
                    case HEAL:
                        laby[x][y] = new Heal();
                        break;
                    case TIME:
                        laby[x][y] = new Time();
                        break;
                    case SPAWNER_CHEST:
                        laby[x][y] = new SpawnerChest();
                        break;
                    case POS_CHEST:
                        laby[x][y] = new Case(TypeCase.EMPTY, randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        maze.addNewPositionChest(new Position(x, y));
                        break;
                    case DAMAGE:
                        laby[x][y] = new Damage(TypeCase.DAMAGE);
                        break;
                    case SPAWNER_MONSTERS:
                        laby[x][y] = new SpawnerMonster(TypeCase.SPAWNER_MONSTERS);
                        break;
                    case POS_MONSTERS:
                        laby[x][y] = new Case(TypeCase.EMPTY,  randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        maze.addNewPositionMonster(new Position(x, y));
                        break;
                    case TELEPORTATION:
                        if (casesTP.size() < 2) {
                            casesTP.add(new Position(x, y));
                        } else {
                            laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
                        }
                }
                x++;
            }
        }

        if (casesTP.size() == 1) {
            Position p = casesTP.get(0);
            laby[p.getX()][p.getY()] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
        } else {
            for (Position p : casesTP) {
                laby[p.getX()][p.getY()] = new Teleportation();
                maze.addTeleportation(p);
            }
        }

        while(y < height && laby[0][y] == null){ // Gestion du bug quand une ligne n'est pas initialisée
            x = 0;
            for(; x < width ; x++){
                laby[x][y] = new Case(TypeCase.EMPTY,randomGenerator.nextInt(ImageFactory.NB_EMPTY_IMG));
            }
            y++;
        }

        maze.setMaze(laby);
        maze.setInitialPositionPlayer(positionJoueur);

        return maze;
    }
}
