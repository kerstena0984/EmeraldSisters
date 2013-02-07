package com.mattdavben.emerald.level;

import com.mattdavben.emerald.graphics.Screen;

public class Level {
	private int width, height;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void render(Screen screen, int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		for (int y = 0; y < height; y += 16) {
			for (int x = 0; x < width; x += 16) {
				screen.render(x, y, 0, 0);
			}
		}
		screen.setOffset(0, 0);
	}
}
