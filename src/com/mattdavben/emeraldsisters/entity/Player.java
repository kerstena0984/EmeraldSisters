package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player extends Entity {

	private Shape collisionShape;
	private SpriteSheet characterSheet;
	private Animation current, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	private int[] animationLength = { 150, 150, 150, 150 };
	private final int SPRITE_WIDTH = 32;
	private final int SPRITE_LENGTH = 48;
	private Input input;

	public Player(Input input) throws SlickException {
		this.characterSheet = new SpriteSheet("Katherine.png", SPRITE_WIDTH, SPRITE_LENGTH);
		this.input = input;

		collisionShape = new Rectangle(position.x, position.y, SPRITE_WIDTH, SPRITE_LENGTH);
		characterSheet.setFilter(Image.FILTER_NEAREST);

		setAnimations();

		current = playerWalkingDown;
	}

	public Shape getCollisionShape() {
		return collisionShape;
	}

	public void render(GameContainer gc, Viewport viewport, Graphics gr) {
		current.draw(position.x - viewport.position.x, position.y - viewport.position.y);
	}

	public void update(GameContainer gc, int delta) {
		animateBasedOnUserInput(input);
		if (movementButtonIsPressed(input)) current.update(delta);
		
		float speedInTilesPerSecond = 3.0f;
		float pixelsPerTile = 32.0f;
		float distance = speedInTilesPerSecond * pixelsPerTile * delta / 1000f;

		if (input.isKeyDown(Input.KEY_UP)) {
			position.y -= distance;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			position.y += distance;
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			position.x -= distance;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			position.x += distance;
		}
		
		collisionShape.setX(position.x);
		collisionShape.setY(position.y + 24);
	}

	private boolean movementButtonIsPressed(Input input) {
		return input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);
	}

	private void animateBasedOnUserInput(Input input) {
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

	private void setAnimations() {
		Image down0 = characterSheet.getSubImage(0, 0);
		Image down1 = characterSheet.getSubImage(1, 0);
		Image down2 = characterSheet.getSubImage(2, 0);

		Image left0 = characterSheet.getSubImage(3, 0);
		Image left1 = characterSheet.getSubImage(4, 0);
		Image left2 = characterSheet.getSubImage(5, 0);

		Image up0 = characterSheet.getSubImage(6, 0);
		Image up1 = characterSheet.getSubImage(7, 0);

		Image[] walkingDown = { down0, down1, down0, down2 };
		Image[] walkingUp = { up0, up1, up0.getFlippedCopy(true, false), up1.getFlippedCopy(true, false) };
		Image[] walkingLeft = { left0, left1, left0, left2 };
		Image[] walkingRight = { left0.getFlippedCopy(true, false), left1.getFlippedCopy(true, false), left0.getFlippedCopy(true, false), left2.getFlippedCopy(true, false), };

		playerWalkingDown = new Animation(walkingDown, animationLength, false);
		playerWalkingUp = new Animation(walkingUp, animationLength, false);
		playerWalkingLeft = new Animation(walkingLeft, animationLength, false);
		playerWalkingRight = new Animation(walkingRight, animationLength, false);
	}

}
