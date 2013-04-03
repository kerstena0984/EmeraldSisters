package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mattdavben.emeraldsisters.world.World;

public class PlayState extends BasicGameState {

	World world;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		world = new World(gc.getInput());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
		world.render(gc, gr);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.update(gc, delta);
	}

	@Override
	public int getID() {
		return 1;
	}

}
