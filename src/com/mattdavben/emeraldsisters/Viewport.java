package com.mattdavben.emeraldsisters;

public class Viewport {
	private float x;
	private float y;

	public Viewport() {
		x = 0;
		y = 0;
	}

	public void update(float playerX, float playerY) {
		x = playerX + 16 - (800 / 2);
		y = playerY + 24 - (600 / 2);

		if (x <= 0.0f) x = 0.0f;
		if (y <= 0.0f) y = 0.0f;
		if (x >= (1280 - Main.GAME_SCREEN_WIDTH)) x = (1280 - Main.GAME_SCREEN_WIDTH);
		if (y >= (1280 - Main.GAME_SCREEN_HEIGHT)) y = (1280 - Main.GAME_SCREEN_HEIGHT);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
