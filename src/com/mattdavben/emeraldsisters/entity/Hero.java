package com.mattdavben.emeraldsisters.entity;

import com.mattdavben.emeraldsisters.InputHandler;
import com.mattdavben.emeraldsisters.graphics.Screen;

public class Hero extends Entity {

	private int walkDist, direction;
	private InputHandler input;

	private int steps = 0;
	private int xMove = 0;
	private int yMove = 0;

	public Hero() {
		x = 24;
		y = 22;
	}

	public void update() {
		input.tick();

		if (steps == 0) {
			if (input.up.isDown) {
				steps = 16;
				yMove = -1;
			} else if (input.down.isDown) {
				steps = 16;
				yMove = 1;
			}
			if (input.left.isDown) {
				steps = 16;
				xMove = -1;
			} else if (input.right.isDown) {
				steps = 16;
				xMove = 1;
			}
		}

		if (steps > 0) {
			if (xMove != 0) this.move(xMove, 0);
			else if (yMove != 0) this.move(0, yMove);
			steps--;
		}

		if (steps == 0) {
			xMove = 0;
			yMove = 0;
		}
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

	public void walk(int x, int y) {
		move(x, y);
	}

	public void move(int xMove, int yMove) {
		boolean mayPass = true;

		if (xMove != 0 || yMove != 0) {
			walkDist++;
			if (yMove < 0) direction = 0;
			if (yMove > 0) direction = 1;
			if (xMove < 0) direction = 2;
			if (xMove > 0) direction = 3;
		}
		
		boolean nextTileMayPass = !level.getTile((getXTile()) + xMove, (getYTile()) + yMove).mayPass();

		if (nextTileMayPass && x % 16 == 8 && y % 16 == 6) {
			mayPass = false;
		}

		if (mayPass) {
			if (xMove != 0) {
				x += xMove;
			} else if (yMove != 0) {
				y += yMove;
			}
		}
	}

	public int getXTile() {
		return x / 16;
	}

	public int getYTile() {
		return y / 16;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void input(InputHandler input) {
		this.input = input;
	}
}
