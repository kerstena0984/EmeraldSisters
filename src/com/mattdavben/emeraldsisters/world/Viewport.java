package com.mattdavben.emeraldsisters.world;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.eventbus.Subscribe;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Main;
import com.mattdavben.emeraldsisters.character.Player;
import com.mattdavben.emeraldsisters.map.Environment;
import com.mattdavben.emeraldsisters.map.MapTransitionEvent;
import com.mattdavben.emeraldsisters.map.MapTransitionListener;

public class Viewport implements MapTransitionListener {

	public Vector2f position;
	private int mapWidth, mapHeight;

	public Viewport(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		position = new Vector2f(0, 0);

		EventNexus.register(this);
	}

	public void setWidthAndHeight(int mapWidth, int mapHeight) {
		System.out.println(mapWidth + " " + mapHeight);
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public void update(Player player) {
		position.x = player.getPosition().x + 16 - (Main.GAME_SCREEN_WIDTH / 2.0f);
		position.y = player.getPosition().y + 24 - (Main.GAME_SCREEN_HEIGHT / 2.0f);

		if (position.x <= 0.0f) position.x = 0.0f;
		if (position.y <= 0.0f) position.y = 0.0f;
		if (mapWidth >= Main.GAME_SCREEN_WIDTH) {
			if (position.x >= (mapWidth - Main.GAME_SCREEN_WIDTH)) position.x = (mapWidth - Main.GAME_SCREEN_WIDTH);
		} else {
			position.x = -((Main.GAME_SCREEN_WIDTH - mapWidth) / 2);
		}
		if (mapHeight >= Main.GAME_SCREEN_HEIGHT) {
			if (mapHeight >= Main.GAME_SCREEN_HEIGHT && position.y >= (mapHeight - Main.GAME_SCREEN_HEIGHT)) position.y = (mapHeight - Main.GAME_SCREEN_HEIGHT);
		}else {
			position.y = -((Main.GAME_SCREEN_HEIGHT - mapHeight) / 2);
		}
	}

	@Subscribe
	public void listen(MapTransitionEvent event) {
		try {
			TiledMap map = new TiledMap("res/" + event.getNewMap() + ".tmx");
			setWidthAndHeight(map.getWidth() * Environment.TILE_WIDTH, map.getHeight() * Environment.TILE_WIDTH);
		} catch (SlickException e) {
			System.out.println("Viewport could not reset width/height because TiledMap does not exist!");
			e.printStackTrace();
		}

	}

}
