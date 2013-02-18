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
	private Player player;

	public PlayState(int state) {
	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		level = new Image("res/sample.png");
		level.setFilter(Image.FILTER_NEAREST);
		player = new Player(level.getWidth(), level.getHeight());
	}

	public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		float viewportX = player.getViewportX();
		float viewportY = player.getViewportY();
		float playerX = player.getPlayerX();
		float playerY = player.getPlayerY();
		level.draw(-viewportX, -viewportY, level.getWidth() * 2, level.getHeight() * 2);
		player.draw(playerX - viewportX, playerY - viewportY);
		g.drawString((int) ((player.getPlayerX() + 30) / 32) + "|" + (int) ((player.getPlayerY() + 44) / 32), 20, 50);

	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();

		player.update(delta, input);
	}
}
