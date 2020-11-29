package com.dametdamet.app.model.graphic;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.dametdamet.app.engine.GamePainter;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.graphic.factory.ImageFactory;
import com.dametdamet.app.model.maze.Maze;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	private PacmanGame pacmanGame;
	/**
	 * la taille des cases
	 */
	protected final int WIDTH;
	protected final int HEIGHT;
	protected final int HEIGHT_HUD;

	/**
	 * appelle constructeur parent
	 * 
	 * @param game le jeu a afficher
	 * @param width longueur de la fenêtre du jeu
	 * @param height hauteur de la fenêtre du jeu
	 */
	public PacmanPainter(PacmanGame game,int width, int height) {
		this.pacmanGame = game;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.HEIGHT_HUD = (int)( 0.1 * height);
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		if(pacmanGame.isOnGoing()) {
			drawMaze(im);
			drawMonsters(im);
			drawHero(im);
			drawEffects(im);
			drawHUD(im);
		} else {
			drawScreenGameState(im);
		}

	}

	/**
	 * dessine les écrans de pause/victoire/défaite
	 * @param im image sur laquelle on dessine les écrans
	 */
	private void drawScreenGameState(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setFont(new Font("Serial",Font.PLAIN,HEIGHT_HUD));

		int heightText = (getHeight() / 2) + (crayon.getFontMetrics().getAscent() / 2);
		int heightTextScore = heightText + crayon.getFontMetrics().getAscent();

		if (pacmanGame.isFinished()) {
			crayon.setColor(Color.BLACK);
			crayon.fillRect(0, 0, getWidth(), getHeight());
			crayon.setColor(Color.WHITE);
			crayon.drawString("Retry ?", 0, heightText);
			crayon.drawString("Score:"+String.valueOf(pacmanGame.getScore()),0,heightTextScore);
		} else if (pacmanGame.isPaused()) {
			crayon.setColor(Color.BLACK);
			crayon.fillRect(0, 0, getWidth(), getHeight());
			crayon.setColor(Color.WHITE);
			crayon.drawString("Pause", 0, heightText);
			crayon.drawString("Score:"+String.valueOf(pacmanGame.getScore()),0,heightTextScore);

		} else if (pacmanGame.isWon()) {
			crayon.setColor(Color.BLACK);
			crayon.fillRect(0, 0, getWidth(), getHeight());
			crayon.setColor(Color.WHITE);
			crayon.drawString("You won !", 0, heightText);
			crayon.drawString("Score:"+String.valueOf(pacmanGame.getScore()),0,heightTextScore);

		}
	}

	/**
	 * dessine les monstres sur l'image en paramètre
	 * @param im image sur laquelle on dessine les monstres
	 */
	private void drawMonsters(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		for (Entity monster: pacmanGame) {
			Position position = monster.getPosition();

			//on récupère l'image du monstre
			BufferedImage imageMonster = ImageFactory.getInstance().getEntityImage(monster);

			//on dessine l'image du monstre
			crayon.drawImage(imageMonster,position.getX()*getRatioWidth(),position.getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth(),getRatioHeight(),null);

		}
	}

	/**
	 * dessine le labyrinthe sur l'image en paramètre
	 * @param im image sur laquelle on dessine le labyrinthe
	 */
	private void drawMaze(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Maze maze = pacmanGame.getMaze();
		Position position = new Position(0,0);
		for(int i = 0; i < maze.getWidth(); i++){
			for(int j = 0; j < maze.getHeight();j++){
				position.setX(i);
				position.setY(j);

				//on récupère l'image de la case
				BufferedImage imageCase = ImageFactory.getInstance().getCaseImage(maze.whatIsIn(position));

				//on dessine l'image de la case
				crayon.drawImage(imageCase,position.getX()*getRatioWidth(),position.getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth(),getRatioHeight(),null);
			}
		}
	}

	/**
	 * dessine le héros sur l'image en paramètre
	 * @param im image sur laquelle on dessine le héros
	 */
	private void drawHero(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Hero hero = (Hero)pacmanGame.getHero();

		if((hero.getInvicibiltyTimer().getTime()/300)% 2 == 0){//condition pour le clignotement de l'image du héros
			Position position = hero.getPosition();

			//on récupère l'image du héros
			BufferedImage imageHero = ImageFactory.getInstance().getEntityImage(hero);


			//on dessine l'image du héros

			crayon.drawImage(imageHero,position.getX()*getRatioWidth(),position.getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth(),getRatioHeight(),null);
		}
	}

	/**
	 * dessine le hud sur l'image en paramètre
	 * @param im image sur laquelle on dessine le hud
	 */
	private void drawHUD(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		//coloriage de la bande du HUD en noir
		crayon.setColor(Color.black);
		crayon.fillRect(0,0,getWidth(),HEIGHT_HUD);


		//affichage timer
		double tailleTimer = getWidth()*0.1;
		if(tailleTimer > HEIGHT_HUD * 0.9) tailleTimer = HEIGHT_HUD * 0.9;

		crayon.setColor(Color.white);
		crayon.setFont(new Font("Serial",Font.PLAIN,(int)tailleTimer));
		crayon.drawString(String.valueOf(pacmanGame.getGameTimer()),0,crayon.getFontMetrics().getAscent());

		//affichage point de vie du héros
		BufferedImage heart_full = ImageFactory.getInstance().getHudImage("heart_full");
		BufferedImage heart_empty = ImageFactory.getInstance().getHudImage("heart_empty");

		Hero hero = (Hero) pacmanGame.getHero();

		//les coeurs occupent 40% de la longueur maximum
		double tailleCoeur = (getWidth()*0.4)/hero.getMaxHp();
		//le ratio de taille des coeurs ne doit pas dépasser la hauteur du HUD (sinon il va dépasser sur le jeu)
		if(tailleCoeur > HEIGHT_HUD*0.9) tailleCoeur = HEIGHT_HUD*0.9;

		//on positionne les coeurs au milieu du HUD
		int posXDepart = (int)((getWidth()/2) - ((hero.getMaxHp()/2.0)*1.1)*tailleCoeur);
		for(int i = 0; i < hero.getMaxHp(); i++){
			crayon.drawImage(hero.getHP() > i ? heart_full : heart_empty,(int)(posXDepart + i*1.1*tailleCoeur),0,(int)(tailleCoeur),(int)(tailleCoeur),null);
		}


		//affichage score
		double tailleScore = getWidth()*0.1;
		if(tailleScore > HEIGHT_HUD * 0.9) tailleScore = HEIGHT_HUD * 0.9;
		crayon.setColor(Color.white);
		crayon.setFont(new Font("Serial",Font.PLAIN,(int)tailleScore));

		crayon.drawString(String.valueOf(pacmanGame.getScore()),getWidth() - crayon.getFontMetrics().stringWidth(String.valueOf(pacmanGame.getScore())) ,crayon.getFontMetrics().getAscent());



	}

	private void drawEffects(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		for(GraphicalEffect effect:pacmanGame.getGraphicalEffects()) {
			//on récupère l'image de l'effet
			BufferedImage imageHero = ImageFactory.getInstance().getEffectImage(effect);

			//on dessine l'image de l'effet

			crayon.drawImage(imageHero,effect.getPosition().getX()*getRatioWidth(),effect.getPosition().getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth() * effect.getWidth(),getRatioHeight() * effect.getHeight(),null);
		}
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT + HEIGHT_HUD;
	}

	private int getRatioWidth(){
		return (WIDTH/pacmanGame.getMaze().getWidth());
	}

	private int getRatioHeight(){
		return (HEIGHT/pacmanGame.getMaze().getHeight());
	}

}
