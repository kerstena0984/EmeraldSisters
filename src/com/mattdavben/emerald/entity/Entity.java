package com.mattdavben.emerald.entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.mattdavben.emerald.graphics.SpriteSheet;

public class Entity {
	private int x, y;
	private SpriteSheet sheet;

	public Entity() {
		try {
			sheet = new SpriteSheet(ImageIO.read(Hero.class.getResourceAsStream("/Kate.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SpriteSheet getSpriteSheet(){
		return sheet;
	}
}
