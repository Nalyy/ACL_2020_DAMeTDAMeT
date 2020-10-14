package com.dametdamet.app.start;

import com.dametdamet.app.model.PacmanPainter;
import com.dametdamet.app.engine.GameEngineGraphical;
import com.dametdamet.app.model.PacmanController;
import com.dametdamet.app.model.PacmanGame;

/**
 * lancement du moteur avec le jeu
 */
public class App {

	public static void main(String[] args) throws InterruptedException {

		// creation du jeu particulier et de son afficheur
		PacmanGame game = new PacmanGame("helpFilePacman.txt");
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}
