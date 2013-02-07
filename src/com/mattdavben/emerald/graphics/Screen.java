package com.mattdavben.emerald.graphics;

public class Screen {
	public final int width;
	public final int height;
	public static final int BITS_PER_SPRITE = 16;

	public int xOffset;
	public int yOffset;

	private SpriteSheet sheet;
	public int[] pixels;

	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];
	}

	public void render(int xPosition, int yPosition, int tile, int bits) {
		xPosition -= xOffset;
		yPosition -= yOffset;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile * BITS_PER_SPRITE) + (yTile * BITS_PER_SPRITE) * sheet.width;

		for (int y = 0; y < BITS_PER_SPRITE; y++) {
			if (y + yPosition < 0 || y + yPosition >= height) continue;
			for (int x = 0; x < BITS_PER_SPRITE; x++) {
				if (x + xPosition < 0 || x + xPosition >= width) continue;
				int pixel = sheet.pixels[x + y * sheet.width + tileOffset];

				pixels[(x + xPosition) + (y + yPosition) * width] = pixel;
			}
		}
	}
}
