package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.geom.Vector2f;

import com.mattdavben.emeraldsisters.Main;

public class Viewport {

	public Vector2f position;
	private int mapWidth, mapHeight;

	public Viewport(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		position = new Vector2f(0, 0);
	}
	
	public void update(Player player) {
		position.x = player.currentPosition.x + 16 - (Main.GAME_SCREEN_WIDTH / 2.0f);
		position.y = player.currentPosition.y + 24 - (Main.GAME_SCREEN_HEIGHT / 2.0f);

		if (position.x <= 0.0f) position.x = 0.0f;
		if (position.y <= 0.0f) position.y = 0.0f;
		if (position.x >= (mapWidth - Main.GAME_SCREEN_WIDTH)) position.x = (mapWidth - Main.GAME_SCREEN_WIDTH);
		if (position.y >= (mapHeight - Main.GAME_SCREEN_HEIGHT)) position.y = (mapHeight - Main.GAME_SCREEN_HEIGHT);
	}

}
