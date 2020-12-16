package com.dametdamet.app.model.graphic.factory;

import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.EntityType;
import com.dametdamet.app.model.graphic.DeathEffect;
import com.dametdamet.app.model.graphic.ExplosionEffect;
import com.dametdamet.app.model.graphic.GraphicalEffect;
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
    public final static int NB_EXPLOSION_IMG = ExplosionEffect.NUM_SPRITE_MAX;
    public final static int NB_DEATH_IMG = DeathEffect.NUM_SPRITE_MAX;
    private final static String PATH = "/images";
    private final static String CASE_PATH = PATH + "/case";
    private final static String ENTITY_PATH = PATH + "/entity";
    private final static String HUD_PATH = PATH + "/hud";
    private final static String EFFECT_PATH = PATH + "/effect";

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
    private final BufferedImage[] explosion;
    private final BufferedImage[] death;
    private final BufferedImage[] arrow;
    private final BufferedImage stairs;
    private final BufferedImage teleportation;
    private final BufferedImage special_dynamite_pressed;
    private final BufferedImage special_heal_pressed;
    private final BufferedImage special_time_pressed;
    private final BufferedImage special_damage_pressed;
    private final BufferedImage special_spawner_monster_pressed;
    private final BufferedImage special_spawner_treasure_pressed;
    private final BufferedImage special_bouncingShot_pressed;
    private final BufferedImage special_quickShot_pressed;
    private final BufferedImage special_multiShoot_pressed;
    private final BufferedImage special_piercingShot_pressed;

    private final BufferedImage special_gameplay_changes;



    private final BufferedImage entityNotFound;

    private final BufferedImage[] hero;
    private final BufferedImage[] monster;
    private  final BufferedImage[] ghost;

    private final BufferedImage heart_full;
    private final BufferedImage heart_empty;

    private ImageFactory() throws IOException {
        ///initialisation des tableaux
        empty = new BufferedImage[NB_EMPTY_IMG];
        treasure = new BufferedImage[NB_TREASURE_IMG];
        wall = new BufferedImage[NB_WALL_IMG];
        special = new BufferedImage[NB_SPECIAL_IMG];
        hero = new BufferedImage[4];
        monster = new BufferedImage[4];
        ghost = new BufferedImage[4];
        explosion = new BufferedImage[ExplosionEffect.NUM_SPRITE_MAX];
        death = new BufferedImage[DeathEffect.NUM_SPRITE_MAX];
        arrow = new BufferedImage[4];

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

        //image DYNAMITE
        special_dynamite_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_dynamite_pressed.png"));

        //image HEAL
        special_heal_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_heal_pressed.png"));

        //image TIME
        special_time_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_time_pressed.png"));

        //image damage
        special_damage_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_damage_pressed.png"));

        //image spanwer monster
        special_spawner_monster_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_spawner_monster_pressed.png"));

        //image spawner treasure
        special_spawner_treasure_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_spawner_treasure_pressed.png"));

        // image TELEPORTATION
        teleportation = ImageIO.read(getClass().getResource(CASE_PATH+"/caseTP.png"));

        // images des changements de tir
        special_gameplay_changes = ImageIO.read(getClass().getResource(CASE_PATH+"/caseShot.png"));

        special_bouncingShot_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_bouncingShot_pressed.png"));
        special_quickShot_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_quickShot_pressed.png"));
        special_piercingShot_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_piercingShot_pressed.png"));
        special_multiShoot_pressed = ImageIO.read(getClass().getResource(CASE_PATH+"/special_multiShoot_pressed.png"));

        //images WALL
        for(int i = 1;i < NB_WALL_IMG + 1;i++){
            wall[i - 1] = ImageIO.read(getClass().getResourceAsStream((CASE_PATH+"/wall" + (i<10 ? "0":"") + i + ".png")));
        }
        //image de la case notFound
        caseNotFound = ImageIO.read(getClass().getResourceAsStream(CASE_PATH+"/case_not_found.png"));
        ///                 ///


        ///RÉCUPÉRATION DES IMAGES DES ENTITÉS///
        //image HERO
        for(int i = 0;i < 4;i++){
            hero[i] = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/hero_sprite0"+i+".png"));
        }

        //image MONSTER
        for(int i = 0;i < 4;i++){
            monster[i] = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/monster_sprite0"+i+".png"));
        }

        //image GHOST
        for(int i = 0;i < 4;i++){
            ghost[i] = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/ghost_sprite0"+i+".png"));
        }

        //image ARROW
        for(int i = 0;i < 4;i++){
            arrow[i] = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/arrow_0"+i+".png"));
        }
        //image entity notFound
        entityNotFound = ImageIO.read(getClass().getResourceAsStream(ENTITY_PATH+"/entity_not_found.png"));
        ///                ///


        ///RÉCUPÉRATION DES IMAGES DE L'INTERFACE///
        //image coeur rempli
        heart_full = ImageIO.read(getClass().getResourceAsStream(HUD_PATH+"/heart_full.png"));
        //image coeur vide
        heart_empty = ImageIO.read(getClass().getResourceAsStream(HUD_PATH+"/heart_empty.png"));
        ///                ///

        ///RÉCUPÉRATION DES IMAGES DES EFFETS///
        for(int i = 0;i < NB_EXPLOSION_IMG;i++){
            explosion[i] = ImageIO.read(getClass().getResourceAsStream((EFFECT_PATH+"/explosion" + (i<10 ? "0":"") + i + ".png")));
        }
        for(int i = 0;i < NB_DEATH_IMG;i++){
            death[i] = ImageIO.read(getClass().getResourceAsStream((EFFECT_PATH+"/death" + (i<10 ? "0":"") + i + ".png")));
        }
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
            case TELEPORTATION:
                return teleportation;
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
            case DAMAGE:
                if(ca.getNumSprite() > 0)
                    return special_damage_pressed;
            case DYNAMITE:
                if(ca.getNumSprite() > 0)
                    return special_dynamite_pressed;
            case TIME:
                if(ca.getNumSprite() > 0)
                    return special_time_pressed;
            case HEAL:
                if(ca.getNumSprite() > 0)
                    return special_heal_pressed;
            case SPAWNER_CHEST:
                if(ca.getNumSprite() > 0)
                    return special_spawner_treasure_pressed;
            case SPAWNER_MONSTERS:
                if(ca.getNumSprite() > 0)
                    return special_spawner_monster_pressed;
                if(ca.getNumSprite() == 0)
                    return special[0];

            case BOUNCINGSHOT_ENABLER:
                if(ca.getNumSprite() > 0)
                    return special_bouncingShot_pressed;
            case MULTISHOOT_ENABLER:
                if(ca.getNumSprite() > 0)
                    return special_multiShoot_pressed;
            case PIERCINGSHOT_ENABLER:
                if(ca.getNumSprite() > 0)
                    return special_piercingShot_pressed;
            case QUICKSHOT_ENABLER:
                if(ca.getNumSprite() > 0)
                    return special_quickShot_pressed;
                if(ca.getNumSprite() == 0)
                    return special_gameplay_changes;

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
        EntityType type = entity.getType();
        int numSprite ;
        switch (entity.getDirection()){

            case UP:
                numSprite = 2;
                break;
            case LEFT:
                numSprite = 1;
                break;
            case RIGHT:
                numSprite = 3;
                break;
            default:
            case DOWN:
                numSprite = 0;
                break;
        }

        switch (type){
            case HERO:
                return hero[numSprite];
            case RUNNER:
            case MONSTER:
                return monster[numSprite];
            case GHOST:
                return ghost[numSprite];
            case PROJECTILE:
                return arrow[numSprite];
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

    public BufferedImage getEffectImage(GraphicalEffect effect){
        switch (effect.getType()){
            case EXPLOSION:
                return explosion[effect.getNumSprite()];
            case DEATH:
                return death[effect.getNumSprite()];
            default:
                return caseNotFound;
        }
    }
}
