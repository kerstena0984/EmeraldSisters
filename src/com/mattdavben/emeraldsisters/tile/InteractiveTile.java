package com.mattdavben.emeraldsisters.tile;

import com.mattdavben.emeraldsisters.graphics.Screen;

public class InteractiveTile extends SolidTile {
	
	private int alternateOverTile;

	public InteractiveTile(int underTile, int overTileDeactivated, int overTileActivated) {
		super(underTile, overTileDeactivated);
		
		this.overTile = overTileDeactivated;
		this.underTile = underTile;
		alternateOverTile = overTileActivated;
	}
	
	public void render(Screen screen, int xPositionStartRender, int yPositionStartRender) {
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, underTile);
		screen.renderTile(xPositionStartRender * 16 + 0, yPositionStartRender * 16 + 0, overTile);
	}
	
	public void activate() {
		overTile = alternateOverTile;
	}
	
	public boolean isInteractive() {
		return true;
	}

}
