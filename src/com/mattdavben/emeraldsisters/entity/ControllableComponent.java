package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class ControllableComponent extends Component {

	private Input input;

	public ControllableComponent(String id, Input input) {
		this.id = id;
		this.input = input;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		float speedInTilesPerSecond = 3.0f;
		float pixelsPerTile = 32.0f;
		float distance = speedInTilesPerSecond * pixelsPerTile * delta / 1000f;

		if (input.isKeyDown(Input.KEY_UP)) {
			owner.position.y -= distance;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			owner.position.y += distance;
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			owner.position.x -= distance;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			owner.position.x += distance;
		}
	}

}
