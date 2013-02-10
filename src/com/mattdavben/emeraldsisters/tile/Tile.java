package com.mattdavben.emeraldsisters.tile;

import com.mattdavben.emeraldsisters.graphics.Screen;

public class Tile {

	protected int x, y;

	public void render(Screen screen, int xPositionStartRender, int yPositionStartRender) {
	}

	public boolean mayPass() {
		return true;
	}
	
	public Tile setXY(int x, int y){
		this.x = x;
		this.y = y;
		return this;
	}
}
