package com.mattdavben.emeraldsisters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mattdavben.emeraldsisters.battle.Battle;
import com.mattdavben.emeraldsisters.battle.BattleEntity;

public class BattleState extends BasicGameState {

	Battle battle;

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
		battle.render(gc, gr);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		battle.update(gc, delta);
	}

	@Override
	public int getID() {
		return 2;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		battle = new Battle(new Image("res/BattleBackground.png")).withCharacter(new BattleEntity("Katherine"))
				.withCharacter(new BattleEntity("Elizabeth"))
				.withCharacter(new BattleEntity("Madeline"))
				.withCharacter(new BattleEntity("Juliette"))
				.withEnemy(new BattleEntity("Bad Guy!"));
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

	}

}
