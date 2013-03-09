package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerSpriteRenderComponent extends SpriteRenderComponent {

	private Entity viewport;
	private Input input;

	public PlayerSpriteRenderComponent(String id, SpriteSheet sheet, Input input, Entity viewport) {
		super(id, sheet);
		this.viewport = viewport;
		this.input = input;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		Vector2f pos = owner.getPosition();

		current.draw(pos.x - viewport.position.x, pos.y - viewport.position.y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		animateBasedOnUserInput();
		if (movementButtonIsPressed()) current.update(delta);
	}

	private void animateBasedOnUserInput() {
		if (input.isKeyDown(Input.KEY_UP)) {
			current = playerWalkingUp;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			current = playerWalkingDown;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			current = playerWalkingLeft;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			current = playerWalkingRight;
		}
	}

	private boolean movementButtonIsPressed() {
		return input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);
	}

}
