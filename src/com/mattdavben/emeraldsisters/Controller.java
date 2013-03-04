package com.mattdavben.emeraldsisters;

import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.collect.Maps;

public class Controller {

	private Player player;
	private Viewport viewport;
	private TiledMap map;
	private Map<Coordinate, Boolean> blocked;

	public Controller() throws SlickException {
		map = new TiledMap("res/testLevel.tmx");
		blocked = Maps.newHashMap();
		player = new Player();
		viewport = new Viewport();

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if (!value.equals("false")) blocked.put(new Coordinate(x, y), new Boolean(true));
				else blocked.put(new Coordinate(x, y), new Boolean(false));
			}
		}
	}

	public void renderComponents(Graphics g) throws SlickException {
		float viewportX = viewport.getX();
		float viewportY = viewport.getY();
		float playerX = player.getPlayerX();
		float playerY = player.getPlayerY();
		map.render((int) -viewportX, (int) -viewportY);
		player.draw(playerX - viewportX, playerY - viewportY);
		g.drawString((int) ((player.getPlayerX() + 16) / 32) + "|" + (int) ((player.getPlayerY() + 24) / 32), 20, 50);
	}

	public void update(int delta, Input input) throws SlickException {
		player.update(delta, input);
		viewport.update(player.getPlayerX(), player.getPlayerY());
	}
}
