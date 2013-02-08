package com.mattdavben.emerald.entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.mattdavben.emerald.graphics.Screen;
import com.mattdavben.emerald.graphics.SpriteSheet;

public class Entity {
	protected int x, y;
	protected SpriteSheet sheet;

	public Entity() {
		try {
			sheet = new SpriteSheet(ImageIO.read(Hero.class.getResourceAsStream("/Kate.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Screen screen){
		
	}
	
	public SpriteSheet getSpriteSheet(){
		return sheet;
	}
}
