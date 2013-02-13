package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {

	private Image level;
	private float shiftX;
	private float shiftY;
	private Player player;

	public PlayState(int state) {
	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		level = new Image("res/sample.png");
		level.setFilter(Image.FILTER_NEAREST);
		player = new Player();

		shiftX = player.getXPosition();
		shiftY = player.getYPosition();
	}

	public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(3.0f, 3.0f);
		float renderX = player.getXPosition();
		float renderY = player.getYPosition();
		
		System.out.println(renderX + " " + renderY);
		level.draw(renderX, renderY);
		player.draw(shiftX, shiftY);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		if (shiftX <= 0) shiftX = 0;
		if (shiftY <= 0) shiftY = 0;

		Input input = container.getInput();

		player.update(delta, input);
	}
}
