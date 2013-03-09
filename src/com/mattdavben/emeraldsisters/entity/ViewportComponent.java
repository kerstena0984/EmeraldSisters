package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.mattdavben.emeraldsisters.Main;

public class ViewportComponent extends Component {

	private Entity player;

	public ViewportComponent(String id, Entity player) {
		this.id = id;
		this.player = player;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		owner.position.x = player.position.x + 16 - (Main.GAME_SCREEN_WIDTH / 2.0f);
		owner.position.y = player.position.y + 24 - (Main.GAME_SCREEN_HEIGHT / 2.0f);

		if (owner.position.x <= 0.0f) owner.position.x = 0.0f;
		if (owner.position.y <= 0.0f) owner.position.y = 0.0f;
		if (owner.position.x >= (1280 - Main.GAME_SCREEN_WIDTH)) owner.position.x = (1280 - Main.GAME_SCREEN_WIDTH);
		if (owner.position.y >= (1280 - Main.GAME_SCREEN_HEIGHT)) owner.position.y = (1280 - Main.GAME_SCREEN_HEIGHT);
	}

}
