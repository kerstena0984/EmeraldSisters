package com.mattdavben.emeraldsisters.entity.collision;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.character.Player;
import com.mattdavben.emeraldsisters.map.Environment;
import com.mattdavben.emeraldsisters.map.MapTransitionEvent;
import com.mattdavben.emeraldsisters.tile.SolidTile;

public class CollisionMap {

	private ArrayList<Shape> environmentCollisionObjects;
	private QuadTree quadtree;
	private TiledMap map;
	private ArrayList<Shape> collisionObjects;

	public CollisionMap(TiledMap map) {
		this.map = map;

		collisionObjects = Lists.newArrayList();

		quadtree = new QuadTree(0, new Rectangle(0, 0, map.getWidth() * Environment.TILE_WIDTH, map.getHeight()
				* Environment.TILE_WIDTH));

		buildCollisionMap();
	}

	public void addEnvironmentShape(Shape shape) {
		environmentCollisionObjects.add(shape);
	}

	public void update(Player player, int delta) {
		quadtree.clear();
		for (Shape shape : collisionObjects)
			quadtree.insert(shape);

		ArrayList<Shape> collidableShapesNearPlayer = Lists.newArrayList();

		player.setToNotBlocked();

		float distance = Player.WALKING_SPEED * Environment.TILE_WIDTH * delta / 1000f;

		Rectangle completelyArbitraryRectangle = new Rectangle((int) 21.5 * 32, (int) 25.5 * 32, 16, 16);

		if (player.getCollisionShape().intersects(completelyArbitraryRectangle)) {
			EventNexus.post(new MapTransitionEvent("throneRoom"));
		}

		Rectangle tinyRoomRectangle = new Rectangle((int) 11.5 * 32, (int) 18.5 * 32, 16, 16);

		if (player.getCollisionShape().intersects(tinyRoomRectangle)) {
			EventNexus.post(new MapTransitionEvent("tinyRoom"));
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

	private void buildCollisionMap() {
		collisionObjects.clear();

		for (int layer = 0; layer < map.getLayerCount(); layer++) {
			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					int tileID = map.getTileId(x, y, layer);
					String blocked = map.getTileProperty(tileID, "blocked", "false");
					if (blocked.equals("true")) {
						String startingXString = map.getTileProperty(tileID, "startingXOffset", "0");
						String startingYString = map.getTileProperty(tileID, "startingYOffset", "0");
						String collisionWidth = map.getTileProperty(tileID, "collisionWidth", "32");
						String collisionHeight = map.getTileProperty(tileID, "collisionHeight", "32");
						int startX = Integer.parseInt(startingXString);
						int startY = Integer.parseInt(startingYString);
						int width = Integer.parseInt(collisionWidth);
						int height = Integer.parseInt(collisionHeight);
						Rectangle collision = buildCollisionBox(tileID, x - startX, y - startY, width, height);
						SolidTile tile = new SolidTile(new Vector2f(x - startX, y - startY), collision);
						collisionObjects.add(tile.getCollisionBox());
					}
				}
			}
		}
	}

	private Rectangle buildCollisionBox(int tileID, int x, int y, int width, int height) {
		int tileWidth = Environment.TILE_WIDTH;
		return new Rectangle(x * tileWidth, y * tileWidth, width, height);
	}
}
