package com.dametdamet.app.model.graphic.factory;

import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.TypeEntity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Maxime Choné
 *
 * classe qui permet d'obtenir une couleur pour afficher une case ou une entité
 */
public class ImageFactory {

    public final static int NB_EMPTY_IMG = 14;
    public final static int NB_TREASURE_IMG = 2;
    public final static int NB_SPECIAL_IMG = 2;
    public final static int NB_WALL_IMG = 5;
    private final static String PATH = "/images";
    private final static String CASE_PATH = PATH + "/case";
    private final static String ENTITY_PATH = PATH + "/entity";
    private final static String HUD_PATH = PATH + "/hud";


    private static ImageFactory instance;

    static {
        try {
            instance = new ImageFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final BufferedImage caseNotFound;
    private final BufferedImage[] empty;
    private final BufferedImage[] treasure;
    private final BufferedImage[] wall;
    private final BufferedImage[] special;
    private final BufferedImage stairs;
    private final BufferedImage teleportation;

    private final BufferedImage entityNotFound;
    private final BufferedImage hero;
    private final BufferedImage monster;

    private final BufferedImage heart_full;
    private final BufferedImage heart_empty;

    private ImageFactory() throws IOException {
        ///initialisation des tableaux
        empty = new BufferedImage[NB_EMPTY_IMG];
        treasure = new BufferedImage[NB_TREASURE_IMG];
        wall = new BufferedImage[NB_WALL_IMG];
        special = new BufferedImage[NB_SPECIAL_IMG];

        /// RÉCUPÉRATION DES IMAGES DES CASES ///
        //images EMPTY
        for(int i = 1;i < NB_EMPTY_IMG + 1;i++){
            empty[i - 1] = ImageIO.read(getClass().getResourceAsStream((CASE_PATH+"/empty" + (i<10 ? "0":"") + i + ".png")));
        }
        //images TREASURE
        for(int i = 1;i < NB_TREASURE_IMG + 1;i++){
            treasure[i - 1] = ImageIO.read(getClass().getResourceAsStream((CASE_PATH+"/treasure" + (i<10 ? "0":"") + i + ".png")));
        }

        // image STAIRS
        stairs = ImageIO.read(getClass().getResource(CASE_PATH+"/stairs.png"));

        // image SPECIAL
        for(int i = 1 ; i < NB_SPECIAL_IMG  + 1; i++){
            special[i - 1] = ImageIO.read(getClass().getResourceAsStream((CASE_PATH+"/special" + (i<10 ? "0":"") + i + ".png")));
        }

        // image TELEPORTATION
        teleportation = ImageIO.read(getClass().getResource(CASE_PATH+"/caseTP.png"));

        //images WALL
        for(int i = 1;i < NB_WALL_IMG + 1;i++){
            wall[i - 1] = ImageIO.read(getClass().getResourceAsStream((CASE_PATH+"/wall" + (i<10 ? "0":"") + i + ".png")));
        }
        //image de la case notFound
        caseNotFound = ImageIO.read(getClass().getResourceAsStream(CASE_PATH+"/case_not_found.png"));
        ///                 ///


        ///RÉCUPÉRATION DES IMAGES DES ENTITÉS///
        //image HERO
        hero = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/hero01.png"));

        //image MONSTER
        monster = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/monster01.png"));

        //image entity notFound
        entityNotFound = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/entity_not_found.png"));
        ///                ///


        ///RÉCUPÉRATION DES IMAGES DE L'INTERFACE///
        //image coeur rempli
        heart_full = ImageIO.read(getClass().getResourceAsStream(HUD_PATH+"/heart_full.png"));
        //image coeur vide
        heart_empty = ImageIO.read(getClass().getResourceAsStream(HUD_PATH+"/heart_empty.png"));
        ///                ///

    }

    public static ImageFactory getInstance() {
        return instance;
    }

    /**
     *
     * @param ca case correspondant à l'image qu'on veut
     * @return l'image correspondant à la case en paramètre
     */
    public BufferedImage getCaseImage(Tile ca){
        TileType type = ca.getType();
        switch (type){
            case EMPTY:
                if(!(ca.getNumSprite() >= empty.length || ca.getNumSprite() < 0) )
                return empty[ca.getNumSprite()];
                break;
            case STAIRS:
                return stairs;
            case BONUS:
                if(!(ca.getNumSprite() >= treasure.length || ca.getNumSprite() < 0))
                    return treasure[ca.getNumSprite()];
                break;
            case WALL:
                if(!(ca.getNumSprite() >= wall.length || ca.getNumSprite() < 0))
                    return wall[ca.getNumSprite()];
                break;
            case TELEPORTATION:
                return teleportation;
            case SPAWNER_CHEST:
            case SPAWNER_MONSTERS:
            case DAMAGE:
            case HEAL:
            case TIME:
                if(!(ca.getNumSprite() >= special.length || ca.getNumSprite() < 0))
                    return special[ca.getNumSprite()];
                break;
            default:
                break;
        }
        return caseNotFound;
    }

    /**
     *
     * @param entity entité correspondante à l'image qu'on veut
     * @return l'image correspondante à l'entité en paramètre
     */
    public BufferedImage getEntityImage(Entity entity){
        TypeEntity type = entity.getType();
        switch (type){
            case HERO:
                return hero;
            case MONSTER:
                return monster;
            default:
                return entityNotFound;
        }
    }

    /**
     *
     * @param hudPartName partie du HUD correspondante à l'image qu'on veut ("heart_full","heart_empty")
     * @return l'image correspondante à la partie du HUD en paramètre
     */
    public BufferedImage getHudImage(String hudPartName){
        switch (hudPartName){
            case "heart_full":
                return heart_full;
            case "heart_empty":
                return heart_empty;
            default:
                return caseNotFound;
        }
    }
}
