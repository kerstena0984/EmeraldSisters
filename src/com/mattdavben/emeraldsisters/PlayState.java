package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class PlayState extends BasicGameState {

	private TiledMap map;
	private Player player;

	public PlayState(int state) {
	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/testLevel.tmx");
		player = new Player();
	}

	public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		float viewportX = player.getViewportX();
		float viewportY = player.getViewportY();
		float playerX = player.getPlayerX();
		float playerY = player.getPlayerY();
		map.render((int) -viewportX, (int) -viewportY);
		player.draw(playerX - viewportX, playerY - viewportY);
		g.drawString((int) ((player.getPlayerX() + 0) / 32) + "|" + (int) ((player.getPlayerY() + 44) / 32), 20, 50);

	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		player.update(delta, input);
	}
}
