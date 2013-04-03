package com.mattdavben.emeraldsisters;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.google.common.eventbus.Subscribe;
import com.mattdavben.emeraldsisters.battle.Battle;
import com.mattdavben.emeraldsisters.world.World;

public class Main extends BasicGame implements StateChangeListener {

	private final static String NAME = "Emerald Sisters | Pre-Alpha 0.0.2";
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 600;
	private World world;
	private Battle battle;

	public Main(String name) {
		super(name);
		EventNexus.register(this);
	}

	public static void main(String[] argv) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, false);
			agc.setTargetFrameRate(90);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	public void listen(StateChangeEvent event) {
		// this.enterState(event.getState());
	}

	@Override
	public void render(GameContainer gc, Graphics gr) throws SlickException {
		world.render(gc, gr);
		// battle.render(gc, gr);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		world = new World(gc.getInput());
		// battle = new Battle.BattleBuilder().WithCharacter(new
		// BattleEntity("Katherine")).build();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		world.update(gc, delta);

	}

}