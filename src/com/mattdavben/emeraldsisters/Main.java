package com.mattdavben.emeraldsisters;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	private final static String NAME = "Emerald Sisters | Pre-Alpha 0.0.1";
	public static final int play = 1;
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 600;

	public Main(String name) {
		super(name);
		this.addState(new PlayState(play));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.enterState(play);
	}

	public static void main(String[] argv) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, false);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}