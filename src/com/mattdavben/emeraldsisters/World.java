package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.mattdavben.emeraldsisters.entity.Player;
import com.mattdavben.emeraldsisters.map.Environment;

public class World {

	private Player player;
	private Viewport viewport;
	private Environment environment;

	public World(Input input) throws SlickException {
		environment = new Environment("testLevel");
		
		player = new Player(input, environment.getPlayerStartingPosition());
		
		viewport = new Viewport(environment.getWidth() * Environment.TILE_WIDTH, environment.getHeight() * Environment.TILE_WIDTH);
	}

	public void render(GameContainer gc, Graphics gr) {
		environment.renderBottomLayers(viewport);
		player.render(viewport);
		environment.renderTopLayers(viewport);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		environment.update(player, delta);
		player.update(delta);
		viewport.update(player);
	}
}
