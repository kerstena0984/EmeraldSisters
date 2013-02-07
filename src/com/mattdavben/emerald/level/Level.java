package com.mattdavben.emerald.level;

import java.util.ArrayList;

import com.mattdavben.emerald.entity.Entity;
import com.mattdavben.emerald.graphics.Screen;

public class Level {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private int width, height;
	public int xOffset, yOffset;
	public int[] pixels;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
	}

	public void render(Screen screen, int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);

		for (int y = 0; y <= height; y += 16) {
			for (int x = 0; x <= width; x += 16) {
				screen.render(x, y, 1, 0);
			}
		}
	}

	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		for (Entity entity : entities) {
			entity.draw(screen);
		}
	}

	public void add(Entity entity) {
		entities.add(entity);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
