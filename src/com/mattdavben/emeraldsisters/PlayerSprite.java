package com.mattdavben.emeraldsisters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PlayerSprite {
	private Animation current, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	private SpriteSheet characterSheet;
	int[] animationLength = { 150, 150, 150, 150 };

	public PlayerSprite(String sheetPath) throws SlickException {
		int playerWidth = 16;
		int playerHeight = 24;

		characterSheet = new SpriteSheet(new Image(sheetPath), playerWidth, playerHeight);
		characterSheet.setFilter(Image.FILTER_NEAREST);

		setAnimations();

		current = playerWalkingDown;
	}

	public void draw(float x, float y, int width, int height) {
		current.draw(x, y, width, height);
	}

	public void update(int xMove, int yMove, int delta) {
		if (yMove > 0) current = playerWalkingDown;
		else if (yMove < 0) current = playerWalkingUp;
		else if (xMove > 0) current = playerWalkingRight;
		else if (xMove < 0) current = playerWalkingLeft;

		current.update(delta);
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
