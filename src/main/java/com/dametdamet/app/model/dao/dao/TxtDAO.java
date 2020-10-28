package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

import java.io.IOException;
import java.io.RandomAccessFile;

public enum TxtDAO implements AbstractFileDAO {

    INSTANCE;

    @Override
    public Case[][] load(String nomFichier) {
        RandomAccessFile fr = null;
        Case[][] laby = new Case[0][0];
        try {
            fr = new RandomAccessFile(getClass().getResource("/" + nomFichier).getFile(), "r"); // Permet de lire le fichier

            // Premier check des tailles du fichier
            int[] widthHeight = trouverTaillesTabCases(fr);

            fr.seek(0); // On retourne au début du fichier

            laby = createTabCases(fr, widthHeight[0], widthHeight[1]);

        } catch (IOException e) {
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
    private int[] trouverTaillesTabCases(RandomAccessFile fr) throws IOException {
        int width = 0;
        int maxWidth = 0;
        int height = 1; // Pour la première ligne qui n'est pas comptabilisée
        int c;

        while((c = fr.read()) != -1){ // On récupère le caractère suivant dans le fichier
            if(c == 13 || c == '\n'){ // 13 car caractère inconnu à la fin de ligne
                if(c == 13){ // Si on tombe sur le caractère inconnu on skip le '\n' (car '\n' est juste après)
                    fr.skipBytes(1);
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
        return new int[]{width, height};
    }

    /**
     * Lis le fichier pour créer le tableau de Cases (avec les cases correspondantes)
     * @param fr le lecteur du fichier
     * @param width la taille du tableau en largeur
     * @param height la taille du tableau en hauteur
     * @return la tableau de cases du fichier
     * @throws IOException exception du lecteur
     */
    private Case[][] createTabCases(RandomAccessFile fr, int width, int height) throws IOException {
        // On commence la construction du labyrinthe
        Case[][] laby = new Case[width][height];
        int c;

        int x = 0;
        int y = 0;
        while((c = fr.read()) != -1){ // On va construire le labyrinthe (tableau à deux dim)
            if(c == 13 || c == '\n'){ // idem, caractère 13 avant le saut à la ligne
                if(c == 13){ // On skip le saut à la ligne
                    fr.skipBytes(1);
                }
                if(x < width){ // Si on est pas au bout de la ligne on remplis de cases vides
                    for(; x < width ; x++){
                        laby[x][y] = new Case(TypeCase.EMPTY);
                    }
                }
                y++;
                x = 0;
            } else {
                laby[x][y] = new Case(TypeCase.getValueOf((char) c)); // On demande à quoi correspond le caractère à TypeCase
                x++;
            }
        }
        return laby;
    }
}
