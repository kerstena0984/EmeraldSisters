package com.mattdavben.emerald.entity;

import com.mattdavben.emerald.graphics.Screen;
import com.mattdavben.emerald.level.Level;

public class Hero extends Entity {

	private int walkDist, direction;

	public Hero() {
		x = 40;
		y = 40;
	}

	public void draw(Screen screen) {
		int walkingSpeed = 3;
		int switchSprite = ((walkDist >> walkingSpeed) & 1);
		int switchFeet = ((walkDist >> walkingSpeed) & 3);
		int flipSprite = ((walkDist >> 4) & 1);
		int nextSprite = 0;
		if (switchFeet == 0 || switchFeet == 2) nextSprite = 0;
		if (switchFeet == 1) nextSprite = 1;
		if (switchFeet == 3) nextSprite = 2;

		if (direction == 0) screen.renderSprite(sheet, x - 8, y - 16, 6 + switchSprite, 0 + flipSprite);
		if (direction == 1) screen.renderSprite(sheet, x - 8, y - 16, 0 + nextSprite, 0);
		if (direction == 2) screen.renderSprite(sheet, x - 8, y - 16, 3 + nextSprite, 0);
		if (direction == 3) screen.renderSprite(sheet, x - 8, y - 16, 3 + nextSprite, 1);
	}

	public void move(int xMove, int yMove) {
		if (xMove != 0 || yMove != 0) {
			walkDist++;
			if (xMove < 0) direction = 2;
			if (xMove > 0) direction = 3;
			if (yMove < 0) direction = 0;
			if (yMove > 0) direction = 1;
		}

		if (xMove != 0) {
			x += xMove;
		}
		if (yMove != 0) {
			y += yMove;
		}
	}
	
	public int currentTile(Level level){
		System.out.println(x / 16 + " " + y / 16);
		return (x + y * level.getWidth());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
