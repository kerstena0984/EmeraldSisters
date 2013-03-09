package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class EnvironmentRenderComponent extends RenderComponent {

	private TiledMap map;
	private Entity viewport;

	public EnvironmentRenderComponent(String id, TiledMap map, Entity viewport) {
		super(id);
		this.map = map;
		this.viewport = viewport;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		map.render((int) -viewport.position.x, (int) -viewport.position.y);
	}

}
