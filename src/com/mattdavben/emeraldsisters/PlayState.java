package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {

	private Controller controller;

	public PlayState(int state) {
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		controller = new Controller();
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		controller.renderComponents(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		controller.update(delta, input);
	}
}
