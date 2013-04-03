package com.mattdavben.emeraldsisters.map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.entity.Player;
import com.mattdavben.emeraldsisters.entity.collision.CollisionMap;
import com.mattdavben.emeraldsisters.player.PlayerMoveEvent;
import com.mattdavben.emeraldsisters.world.Viewport;

public class Environment implements MapTransitionListener {

	private TiledMap map;
	private int width, height;
	public final static int TILE_WIDTH = 32;
	private CollisionMap collisionMap;

	public Environment(String nameOfLevel) throws SlickException {
		EventNexus.register(this);

		this.map = new TiledMap("res/" + nameOfLevel + ".tmx");
		this.width = map.getWidth();
		this.height = map.getHeight();

		collisionMap = new CollisionMap(map);
	}

	public void renderBottomLayers(Viewport viewport) {
		for (int i = 0; i < map.getLayerCount() - 1; i++) {
			map.render((int) -viewport.position.x, (int) -viewport.position.y, i);
		}
	}

	public void renderTopLayers(Viewport viewport) {
		map.render((int) -viewport.position.x, (int) -viewport.position.y, map.getLayerCount() - 1);
	}

	public void update(Player player, int delta) {
		collisionMap.update(player, delta);
	}

	@Override
	public void listen(MapTransitionEvent event) {
		transitionToNewMap(event.getNewMap());
		EventNexus.post(new PlayerMoveEvent(getPlayerStartingPosition()));
	}

	public void transitionToNewMap(String newMap) {
		try {
			this.map = new TiledMap("res/" + newMap + ".tmx");
		} catch (SlickException e) {
			e.printStackTrace();
			System.out.println("Could not transition to " + newMap);
		}

		collisionMap = new CollisionMap(map);
	}

	public Vector2f getPlayerStartingPosition() {
		int verticalSpriteOffset = 24;
		String startingXTileString = map.getMapProperty("startingXTile", "0");
		float startingXPosition = Float.parseFloat(startingXTileString) * TILE_WIDTH;
		String startingYTileString = map.getMapProperty("startingYTile", "0");
		float startingYPosition = Float.parseFloat(startingYTileString) * TILE_WIDTH;
		return new Vector2f(startingXPosition, startingYPosition - (verticalSpriteOffset));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}