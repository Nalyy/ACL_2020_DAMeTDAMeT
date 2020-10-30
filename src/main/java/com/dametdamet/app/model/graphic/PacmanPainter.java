package com.dametdamet.app.model.graphic;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.dametdamet.app.engine.GamePainter;
import com.dametdamet.app.model.Entity;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.graphic.factory.ColorFactory;
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
		this.HEIGHT_HUD = getRatioHeight() * 2;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		if (pacmanGame.isFinished()){
			crayon.setColor(Color.BLACK);
			crayon.fillRect(0, 0, getWidth(), getHeight());
			crayon.setColor(Color.WHITE);
			crayon.drawString("Retry ?", 0, getHeight()/2);
		}else if (pacmanGame.isPaused()){
			crayon.setColor(Color.BLACK);
			crayon.fillRect(0, 0, getWidth(), getHeight());
			crayon.setColor(Color.WHITE);
			crayon.drawString("Pause", 0, getHeight()/2);
		}else {
			drawMaze(im);
			drawHero(im);
			drawMonsters(im);
			drawHUD(im);
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
			crayon.setColor(ColorFactory.INSTANCE.getEntityColor(monster.getType()));
			crayon.fillOval(position.getX()*getRatioWidth(), position.getY()*getRatioHeight() + HEIGHT_HUD, getRatioWidth(), getRatioHeight());
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
				crayon.setColor(ColorFactory.INSTANCE.getCaseColor(maze.whatIsIn(position).getType()));
				crayon.fillRect(position.getX()*getRatioWidth(),position.getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth(),getRatioHeight());
			}
		}
	}

	/**
	 * dessine le héros sur l'image en paramètre
	 * @param im image sur laquelle on dessine le héros
	 */
	private void drawHero(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Entity hero = pacmanGame.getHero();

		Position position = hero.getPosition();
		crayon.setColor(ColorFactory.INSTANCE.getEntityColor((hero.getType())));
		crayon.fillOval(position.getX()*getRatioWidth(),position.getY()*getRatioHeight() + HEIGHT_HUD,getRatioWidth(),getRatioHeight());
	}

	/**
	 * dessine le hud sur l'image en paramètre
	 * @param im image sur laquelle on dessine le hud
	 */
	private void drawHUD(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.black);
		crayon.setFont(new Font("Serial",Font.PLAIN,HEIGHT_HUD));
		crayon.drawString(String.valueOf(pacmanGame.getGameTimer().getTime() / 1000),0,crayon.getFontMetrics().getAscent());
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
