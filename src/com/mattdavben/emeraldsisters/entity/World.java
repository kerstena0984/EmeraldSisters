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
					WorldEntity entity = new WorldEntity().withCollisionShape(x * 32, y * 32, 32, 32);
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
		player.renderCollisionBox(viewport, gr);
		for (WorldEntity entity : worldEntities)
			entity.renderCollisionBox(viewport, gr);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		quadtree.clear();
		for (Shape shape : collisionObjects)
			quadtree.insert(shape);

		player.update(gc, delta);
		viewport.update(player);

		List<Shape> collidableShapes = Lists.newArrayList();
		for (int i = 0; i < collisionObjects.size(); i++) {
			collidableShapes.clear();
			quadtree.retrieve(collidableShapes, collisionObjects.get(i));

			for (int k = 0; k < collidableShapes.size(); k++) {
				if (player.getCollisionShape().intersects(collidableShapes.get(k)) && !player.getCollisionShape().equals(collidableShapes.get(k))) {
					System.out.println("PLAYER COLLISION!");
				}
			}
		}
	}

}
