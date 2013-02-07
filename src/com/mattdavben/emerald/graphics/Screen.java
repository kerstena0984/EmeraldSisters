package com.mattdavben.emerald.graphics;

public class Screen {
	public final int width;
	public final int height;
	
	private SpriteSheet sheet;
	
	public Screen(int width, int height, SpriteSheet sheet){
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
}
