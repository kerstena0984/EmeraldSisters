package com.mattdavben.emeraldsisters;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.entity.ControllableComponent;
import com.mattdavben.emeraldsisters.entity.Entity;
import com.mattdavben.emeraldsisters.entity.EnvironmentRenderComponent;
import com.mattdavben.emeraldsisters.entity.PlayerEntity;
import com.mattdavben.emeraldsisters.entity.PlayerSpriteRenderComponent;
import com.mattdavben.emeraldsisters.entity.ViewportComponent;
import com.mattdavben.emeraldsisters.entity.collision.QuadTree;

public class PlayState extends BasicGameState {

	private PlayerEntity player;
	private Entity map;
	private Entity viewport;
	private Input input;
	private List<Shape> collisionObjects;
	private QuadTree quadtree;

	public PlayState(int state) {
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		input = container.getInput();
		TiledMap tileMap = new TiledMap("res/testLevel.tmx");
		collisionObjects = Lists.newArrayList();
		quadtree = new QuadTree(0, new Rectangle(0, 0, tileMap.getWidth(), tileMap.getHeight()));

		player = new PlayerEntity("Player", new Rectangle(0, 0, 32, 24));
		map = new Entity("Map");
		viewport = new Entity("Viewport");

		viewport.addComponent(new ViewportComponent("ViewportComponent", player));

		player.addComponent(new ControllableComponent("PlayerController", input));
		player.addComponent(new PlayerSpriteRenderComponent("PlayerSprite", new SpriteSheet("Katherine.png", 32, 48), input, viewport));

		map.addComponent(new EnvironmentRenderComponent("MapRender", tileMap, viewport));

		for (int x = 0; x < tileMap.getWidth(); x++) {
			for (int y = 0; y < tileMap.getHeight(); y++) {
				int tileID = tileMap.getTileId(x, y, 1);
				String value = tileMap.getTileProperty(tileID, "blocked", "false");
				if (!value.equals("false")) collisionObjects.add(new Rectangle(x * 32, y * 32, 32, 32));
			}
		}

		collisionObjects.add(player.getCollisionShape());
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(container, null, g);
		player.render(container, null, g);

		g.setColor(Color.red);

		for (Shape shape : collisionObjects) {
			g.draw(shape);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		quadtree.clear();
		for (Shape shape : collisionObjects) {
			quadtree.insert(shape);
		}

		player.update(container, null, delta);
		viewport.update(container, null, delta);
		map.update(container, null, delta);

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
