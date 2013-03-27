package com.mattdavben.emeraldsisters.map;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Viewport;
import com.mattdavben.emeraldsisters.entity.Player;
import com.mattdavben.emeraldsisters.entity.WorldEntity;
import com.mattdavben.emeraldsisters.entity.collision.QuadTree;
import com.mattdavben.emeraldsisters.player.PlayerMoveEvent;

public class Environment implements MapTransitionListener {

	private TiledMap map;
	private int width, height;
	private final static int TILE_WIDTH = 32;
	private ArrayList<Shape> collisionObjects;
	private ArrayList<WorldEntity> worldEntities;
	private QuadTree quadtree;

	public Environment(String nameOfLevel) throws SlickException {
		EventNexus.register(this);

		this.map = new TiledMap("res/" + nameOfLevel + ".tmx");
		this.width = map.getWidth();
		this.height = map.getHeight();

		collisionObjects = Lists.newArrayList();
		worldEntities = Lists.newArrayList();
		quadtree = new QuadTree(0, new Rectangle(0, 0, map.getWidth() * TILE_WIDTH, map.getHeight() * TILE_WIDTH));

		buildCollisionMap();
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
		quadtree.clear();
		for (Shape shape : collisionObjects)
			quadtree.insert(shape);

		ArrayList<Shape> collidableShapesNearPlayer = Lists.newArrayList();

		player.blockedLeft = false;
		player.blockedRight = false;
		player.blockedUp = false;
		player.blockedDown = false;

		float speedInTilesPerSecond = 3.0f;
		float pixelsPerTile = 32.0f;
		float distance = speedInTilesPerSecond * pixelsPerTile * delta / 1000f;
		
		if (player.getCollisionShape().intersects(new Rectangle((int) 21.5 * 32, (int) 25.5 * 32, 16, 16))) {
			EventNexus.post(new MapTransitionEvent("throneRoom"));
		}

		Shape collisionShapeUp = player.getCollisionShapeCopy();
		collisionShapeUp.setY(collisionShapeUp.getY() - distance);

		Shape collisionShapeDown = player.getCollisionShapeCopy();
		collisionShapeDown.setY(collisionShapeDown.getY() + distance);

		Shape collisionShapeLeft = player.getCollisionShapeCopy();
		collisionShapeLeft.setX(collisionShapeLeft.getX() - distance);

		Shape collisionShapeRight = player.getCollisionShapeCopy();
		collisionShapeRight.setX(collisionShapeRight.getX() + distance);

		quadtree.retrieve(collidableShapesNearPlayer, player.getCollisionShape());

		for (int k = 0; k < collidableShapesNearPlayer.size(); k++) {
			if (!collidableShapesNearPlayer.get(k).equals(player.getCollisionShape())) {
				if (collisionShapeUp.intersects(collidableShapesNearPlayer.get(k))) {
					player.blockedUp = true;
				}
				if (collisionShapeDown.intersects(collidableShapesNearPlayer.get(k))) {
					player.blockedDown = true;
				}
				if (collisionShapeLeft.intersects(collidableShapesNearPlayer.get(k))) {
					player.blockedLeft = true;
				}
				if (collisionShapeRight.intersects(collidableShapesNearPlayer.get(k))) {
					player.blockedRight = true;
				}
			}
		}
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

		collisionObjects.clear();
		buildCollisionMap();
	}

	public Vector2f getPlayerStartingPosition() {
		String startingXTileString = map.getMapProperty("startingXTile", "0");
		float startingXPosition = Float.parseFloat(startingXTileString) * TILE_WIDTH;
		String startingYTileString = map.getMapProperty("startingYTile", "0");
		float startingYPosition = Float.parseFloat(startingYTileString) * TILE_WIDTH;
		return new Vector2f(startingXPosition, startingYPosition - (Player.SPRITE_HEIGHT / 2));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void buildCollisionMap() {
		for (int layer = 1; layer < map.getLayerCount(); layer++) {
			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					int tileID = map.getTileId(x, y, layer);
					String blocked = map.getTileProperty(tileID, "blocked", "false");
					if (blocked.equals("true")) {
						WorldEntity entity = new WorldEntity().withCollisionShape(x * TILE_WIDTH + 3, y * TILE_WIDTH
								+ 3, TILE_WIDTH - 6, TILE_WIDTH - 6);
						worldEntities.add(entity);
						collisionObjects.add(entity.getCollisionShapeCopy());
					}
				}
			}
		}
	}
}