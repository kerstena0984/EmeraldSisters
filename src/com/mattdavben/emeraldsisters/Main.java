package com.mattdavben.emeraldsisters;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.Subscribe;

public class Main extends StateBasedGame implements StateChangeListener {

	private final static String NAME = "Emerald Sisters | Pre-Alpha 0.0.2";
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 600;
	public static final BasicGameState PlayState = new PlayState();
	public static final BasicGameState BattleState = new BattleState();

	public Main(String name) {
		super(name);
		EventNexus.register(this);

		this.addState(PlayState);
		this.addState(BattleState);

		enterState(1);
	}

	public static void main(String[] argv) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, false);
			agc.setShowFPS(false);
			agc.setTargetFrameRate(90);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	public void listen(StateChangeEvent event) {
		this.enterState(event.getState().getID());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(1).init(gc, this);
		this.getState(2).init(gc, this);
	}
}