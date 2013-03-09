package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.mattdavben.emeraldsisters.entity.ControllableComponent;
import com.mattdavben.emeraldsisters.entity.Entity;
import com.mattdavben.emeraldsisters.entity.EnvironmentRenderComponent;
import com.mattdavben.emeraldsisters.entity.PlayerSpriteRenderComponent;
import com.mattdavben.emeraldsisters.entity.ViewportComponent;

public class PlayState extends BasicGameState {

	private Entity player;
	private Entity map;
	private Entity viewport;
	private Input input;

	public PlayState(int state) {
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		input = container.getInput();

		player = new Entity("Player");
		map = new Entity("Map");
		viewport = new Entity("Viewport");

		viewport.AddComponent(new ViewportComponent("ViewportComponent", player));

		player.AddComponent(new ControllableComponent("PlayerController", input));
		player.AddComponent(new PlayerSpriteRenderComponent("PlayerSprite", new SpriteSheet("Katherine.png", 32, 48), input, viewport));

		map.AddComponent(new EnvironmentRenderComponent("MapRender", new TiledMap("res/testLevel.tmx"), viewport));
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(container, null, g);
		player.render(container, null, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		player.update(container, null, delta);
		viewport.update(container, null, delta);
		map.update(container, null, delta);
		
	}
}
