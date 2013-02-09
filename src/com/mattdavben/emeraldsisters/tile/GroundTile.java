package com.mattdavben.emeraldsisters.tile;

import com.mattdavben.emeraldsisters.graphics.Screen;

public class GroundTile extends Tile{
	private int tileFromSpriteSheet;
	protected int x, y;

	public GroundTile(int tile) {
		this.tileFromSpriteSheet = tile;
	}

	public void render(Screen screen, int xPositionStartRender, int yPositionStartRender) {
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, tileFromSpriteSheet);
	}

	public boolean mayPass() {
		return true;
	}
}
