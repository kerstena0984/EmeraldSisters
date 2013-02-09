package com.mattdavben.emeraldsisters.tile;

import com.mattdavben.emeraldsisters.graphics.Screen;

public class SolidTile extends Tile{
	private int underTile, overTile;

	public SolidTile(int underTile, int overTile) {
		this.underTile = underTile;
		this.overTile = overTile;
	}

	public void render(Screen screen, int xPositionStartRender, int yPositionStartRender) {
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, underTile);
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, overTile);
	}

	public boolean mayPass() {
		return false;
	}
}
