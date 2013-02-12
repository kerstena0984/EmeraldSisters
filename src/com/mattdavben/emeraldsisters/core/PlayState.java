package com.mattdavben.emeraldsisters.core;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {

	Animation player, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	SpriteSheet sheet;
	int[] animationLength = { 150, 150, 150, 150 };
	Image level;
	float playerPositionX = 0;
	float playerPositionY = 0;
	float shiftX = playerPositionX + 320;
	float shiftY = playerPositionY + 160;

	public PlayState(int state) {
	}

	public int getID() {
		return 1;
	}

	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		sheet = new SpriteSheet(new Image("res/Kate.png"), 16, 24);
		Image down0 = sheet.getSubImage(0, 0);
		Image down1 = sheet.getSubImage(1, 0);
		Image down2 = sheet.getSubImage(2, 0);
		Image left0 = sheet.getSubImage(3, 0);
		Image left1 = sheet.getSubImage(4, 0);
		Image left2 = sheet.getSubImage(5, 0);
		Image up0 = sheet.getSubImage(6, 0);
		Image up1 = sheet.getSubImage(7, 0);
		level = new Image("res/levels/testMap.png");

		Image[] walkingDown = { down0, down1, down0, down2 };
		Image[] walkingUp = { up0, up1, up0.getFlippedCopy(true, false), up1.getFlippedCopy(true, false) };
		Image[] walkingLeft = { left0, left1, left0, left2 };
		Image[] walkingRight = { left0.getFlippedCopy(true, false), left1.getFlippedCopy(true, false), left0.getFlippedCopy(true, false), left2.getFlippedCopy(true, false), };

		playerWalkingDown = new Animation(walkingDown, animationLength, false);
		playerWalkingUp = new Animation(walkingUp, animationLength, false);
		playerWalkingLeft = new Animation(walkingLeft, animationLength, false);
		playerWalkingRight = new Animation(walkingRight, animationLength, false);
		player = playerWalkingDown;
	}

	public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		level.draw(playerPositionX, playerPositionY);
		player.draw(shiftX, shiftY);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP)) {
			player = playerWalkingUp;
			player.update(delta);
			playerPositionY += delta * .1f;
			if (playerPositionY > 162) {
				playerPositionY -= delta * .1f;
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			player = playerWalkingDown;
			player.update(delta);
			playerPositionY -= delta * .1f;
			if (playerPositionY < -600) {
				playerPositionY += delta * .1f;
			}
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			player = playerWalkingLeft;
			player.update(delta);
			playerPositionX += delta * .1f;
			if (playerPositionX > 324) {
				playerPositionX -= delta * .1f;
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			player = playerWalkingRight;
			player.update(delta);
			playerPositionX -= delta * .1f;
			if (playerPositionX < -840) {
				playerPositionX += delta * .1f;
			}
		}
	}
}
