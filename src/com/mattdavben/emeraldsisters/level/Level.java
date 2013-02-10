package com.mattdavben.emeraldsisters.level;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.mattdavben.emeraldsisters.entity.Entity;
import com.mattdavben.emeraldsisters.graphics.Screen;
import com.mattdavben.emeraldsisters.graphics.SpriteSheet;
import com.mattdavben.emeraldsisters.tile.SolidTile;
import com.mattdavben.emeraldsisters.tile.Tile;
import com.mattdavben.emeraldsisters.tile.TileConverter;

public class Level {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private int width, height;
	public int xOffset, yOffset;
	public int[] pixels;
	private Tile[] tiles;
	private SpriteSheet sheet;

	public Level() {
		try {
			this.sheet = new SpriteSheet(ImageIO.read(Level.class.getResourceAsStream("/levels/testLevel.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = sheet.width;
		this.height = sheet.height;

		tiles = new Tile[width * height];

		for (int i = 0; i < sheet.pixels.length; i++) {
			Tile tile = TileConverter.convert(sheet.pixels[i]);
			tiles[i] = tile.setXY(i % sheet.width, i / sheet.width);
		}
	}

	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
	}

	public void render(Screen screen, int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		
		int levelTilesWide = sheet.width;
		int levelTilesHigh = sheet.height;

		
		int tilesToRenderX = levelTilesWide;
		int tilesToRenderY = levelTilesHigh;
		if (screen.width / 16 > levelTilesWide) tilesToRenderX = (levelTilesWide + (screen.width - levelTilesWide)) / 16;
		if (screen.height / 16 > levelTilesHigh) tilesToRenderY = (levelTilesHigh + (screen.height - levelTilesHigh)) / 16;


		for (int yTile = 0; yTile <= tilesToRenderY; yTile++) {
			for (int xTile = 0; xTile <= tilesToRenderX; xTile++) {
				getTile(xTile, yTile).render(screen, xTile, yTile);
			}
		}
	}

	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		for (Entity entity : entities) {
			entity.draw(screen);
		}
	}

	public Tile getTile(int xTile, int yTile) {
		if (xTile < 0 || yTile < 0 || xTile >= width || yTile >= height) return new SolidTile(1,2);
		return tiles[xTile + yTile * width];
	}

	public void add(Entity entity) {
		entity.setLevel(this);
		entities.add(entity);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
