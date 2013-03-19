package com.mattdavben.emeraldsisters.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.entity.collision.QuadTree;

public class World {

	private TiledMap map;
	private ArrayList<Shape> collisionObjects;
	private ArrayList<WorldEntity> worldEntities;
	private QuadTree quadtree;
	private Player player;
	private Viewport viewport;
	private final static int TILE_WIDTH = 32;

	public World(Input input) throws SlickException {
		map = new TiledMap("res/testLevel.tmx");
		collisionObjects = Lists.newArrayList();
		worldEntities = Lists.newArrayList();
		quadtree = new QuadTree(0, new Rectangle(0, 0, map.getWidth() * TILE_WIDTH, map.getHeight() * TILE_WIDTH));
		player = new Player(input);
		viewport = new Viewport(map.getWidth() * 32, map.getHeight() * 32);

		for (int layer = 1; layer < map.getLayerCount() - 1; layer++) {
			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					int tileID = map.getTileId(x, y, layer);
					String blocked = map.getTileProperty(tileID, "blocked", "false");
					if (blocked.equals("true")) {
						WorldEntity entity = new WorldEntity().withCollisionShape(x * TILE_WIDTH, y * TILE_WIDTH, 32,
								32);
						worldEntities.add(entity);
						collisionObjects.add(entity.getCollisionShapeCopy());
					}
				}
			}
		}
		collisionObjects.add(player.getCollisionShape());
	}

	public void render(GameContainer gc, Graphics gr) {
		for (int i = 0; i < map.getLayerCount() - 2; i++)
			map.render((int) -viewport.position.x, (int) -viewport.position.y);
		player.render(gc, viewport, gr);
		map.render((int) -viewport.position.x, (int) -viewport.position.y, map.getLayerCount() - 1);
		// player.renderCollisionBox(viewport, gr);
		// for (WorldEntity entity : worldEntities)
		// entity.renderCollisionBox(viewport, gr);
		//
		// for (Shape shape : nearbyObjectsToDraw) {
		// Polygon polygon = (Polygon) shape;
		// polygon = polygon.copy();
		// polygon.setX(polygon.getX() - viewport.position.x);
		// polygon.setY(polygon.getY() - viewport.position.y);
		// gr.draw(polygon);
		// }
	}

	public void update(GameContainer gc, int delta) throws SlickException {
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

		player.update(gc, delta);
		viewport.update(player);
	}
}
