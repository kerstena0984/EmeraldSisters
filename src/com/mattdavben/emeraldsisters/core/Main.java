package com.mattdavben.emeraldsisters.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	private final static String NAME = "Emerald Sisters | Pre-Alpha 0.1.0";
	public static final int play = 1;

	public Main(String name) {
		super(name);
		this.addState(new PlayState(play));
	}

	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(play).init(container, this);
		this.enterState(play);
	}

	public static void main(String[] argv) {
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Main(NAME));
			agc.setDisplayMode(800, 600, false);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}