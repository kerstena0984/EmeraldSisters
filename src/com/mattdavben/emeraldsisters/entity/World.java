package com.mattdavben.emeraldsisters.entity;

import java.util.ArrayList;
import java.util.List;

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

	public World(Input input) throws SlickException {
		map = new TiledMap("res/testLevel.tmx");
		collisionObjects = Lists.newArrayList();
		worldEntities = Lists.newArrayList();
		quadtree = new QuadTree(0, new Rectangle(0, 0, map.getWidth(), map.getHeight()));
		player = new Player(input);
		viewport = new Viewport();

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if (!value.equals("false")) {
					WorldEntity entity = new WorldEntity().withCollisionShape(x * 32 + 2, y * 32 + 2, 28, 28);
					worldEntities.add(entity);
					collisionObjects.add(entity.getCollisionShape());
				}
			}
		}

		collisionObjects.add(player.getCollisionShape());
	}

	public void render(GameContainer gc, Graphics gr) {
		map.render((int) -viewport.position.x, (int) -viewport.position.y);
		player.render(gc, viewport, gr);
		// player.renderCollisionBox(viewport, gr);
		// for (WorldEntity entity : worldEntities)
		// entity.renderCollisionBox(viewport, gr);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		quadtree.clear();
		for (Shape shape : collisionObjects)
			quadtree.insert(shape);

		List<Shape> collidableShapesNearPlayer = Lists.newArrayList();

		player.blocked = false;
		Shape movingShape = player.getCollisionShape();
		float movingX = movingShape.getX();
		float movingY = movingShape.getY();

		float speedInTilesPerSecond = 3.0f;
		float pixelsPerTile = 32.0f;
		float distance = speedInTilesPerSecond * pixelsPerTile * delta / 1000f;

		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			movingY -= distance;
		} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			movingY += distance;
		}
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			movingX -= distance;
		} else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movingX += distance;
		}

		movingShape.setX(movingX);
		movingShape.setY(movingY);

		quadtree.retrieve(collidableShapesNearPlayer, movingShape);
		for (int k = 0; k < collidableShapesNearPlayer.size(); k++) {
			if (player.getCollisionShape().intersects(collidableShapesNearPlayer.get(k)) && !movingShape.equals(collidableShapesNearPlayer.get(k))) {
				player.blocked = true;
			}
		}

		player.update(gc, delta);
		viewport.update(player);

	}
}
