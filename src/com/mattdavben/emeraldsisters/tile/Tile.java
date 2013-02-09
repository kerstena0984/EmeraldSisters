package com.mattdavben.emeraldsisters.tile;

import com.mattdavben.emeraldsisters.entity.Entity;
import com.mattdavben.emeraldsisters.graphics.Screen;
import com.mattdavben.emeraldsisters.level.Level;

public class Tile {

	private int tileFromSpriteSheet;
	public int x, y;

	public Tile(int tile) {
		this.tileFromSpriteSheet = tile;
	}

	public void render(Screen screen, int xPositionStartRender, int yPositionStartRender) {
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, tileFromSpriteSheet);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
}
