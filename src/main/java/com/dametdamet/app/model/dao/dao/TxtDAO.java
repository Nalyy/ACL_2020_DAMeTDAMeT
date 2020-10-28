package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public enum TxtDAO implements AbstractFileDAO {

    INSTANCE;

    @Override
    public Case[][] load(String nomFichier) {
        RandomAccessFile fr = null;
        int c;
        Case[][] laby = new Case[0][0];
        try {
            fr = new RandomAccessFile(getClass().getResource("/" + nomFichier).getFile(), "r");
            int width = 0;
            int maxWidth = 0;
            int height = 1; // Pour la première ligne qui n'est pas comptabilisée

            while((c = fr.read()) != -1){ // Premier check des tailles du fichier
                if(c == 13 || c == '\n'){ // 13 car caractère inconnu à la fin de ligne
                    if(c == 13){ // Si on tombe sur le caractère inconnu on skip le '\n'
                        fr.skipBytes(1);
                    }
                    height++;
                    if(maxWidth < width){ // On garde la longueur de la plus grande ligne
                        maxWidth = width;
                    }
                    width = 0;
                }else{
                    width++;
                }
            }
            fr.seek(0); // On retourne au début du fichier

            // On commence la construction du labyrinthe
            laby = new Case[maxWidth][height];

            int x = 0;
            int y = 0;
            while((c = fr.read()) != -1){
                if(c == 13 || c == '\n'){
                    if(c == 13){
                        fr.skipBytes(1);
                    }
                    if(x < maxWidth){
                        for(; x < maxWidth ; x++){
                            laby[x][y] = new Case(TypeCase.EMPTY);
                        }
                    }
                    y++;
                    x = 0;
                } else {
                    laby[x][y] = new Case(TypeCase.getValueOf((char) c));
                    x++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return laby;
    }
}
