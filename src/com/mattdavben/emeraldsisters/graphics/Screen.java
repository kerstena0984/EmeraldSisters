package com.mattdavben.emeraldsisters.graphics;

public class Screen {
	public final int width;
	public final int height;
	public static final int BITS_PER_SPRITE = 16;
	public static final int LETTER_BITS_PER_SPRITE = 8;

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
	
	public void renderFontTile(int xPosition, int yPosition, int tile, int colorOfText) {
		xPosition -= xOffset;
		yPosition -= yOffset;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile * LETTER_BITS_PER_SPRITE) + (yTile * LETTER_BITS_PER_SPRITE) * sheet.width;

		for (int y = 0; y < LETTER_BITS_PER_SPRITE; y++) {
			if (y + yPosition < 0 || y + yPosition >= height) continue;
			for (int x = 0; x < LETTER_BITS_PER_SPRITE; x++) {
				if (x + xPosition < 0 || x + xPosition >= width) continue;
				int pixel = sheet.pixels[x + y * sheet.width + tileOffset];
				if (pixel != 0x020202) {
					pixels[(x + xPosition) + (y + yPosition) * width] = colorOfText;
				}
			}
		}
	}

	public void renderTile(int xPosition, int yPosition, int tile) {
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
				if (pixel != 0xff00ff && pixel != 0x7F007F) {
					pixels[(x + xPosition) + (y + yPosition) * width] = pixel;
				}
			}
		}
	}

	public void renderSprite(SpriteSheet spriteSheet, int xPosition, int yPosition, int tile, int mirrorBits) {
		int spriteWidth = 16;
		int spriteHeight = 24;
		xPosition -= xOffset;
		yPosition -= yOffset;
		int xTile = tile % (256 / 16);
		int yTile = tile / (256 / 24);
		boolean mirrorX = (mirrorBits & 0x01) > 0;
		boolean mirrorY = (mirrorBits & 0x02) > 0;
		int tileOffset = (xTile * spriteWidth) + (yTile * spriteHeight) * sheet.width;

		for (int y = 0; y < spriteHeight; y++) {
			int ys = y;
			if (mirrorY) ys = (spriteHeight - 1) - y;
			if (y + yPosition < 0 || y + yPosition >= height) continue;
			for (int x = 0; x < spriteWidth; x++) {
				if (x + xPosition < 0 || x + xPosition >= width) continue;
				int xs = x;
				if (mirrorX) xs = (spriteWidth - 1) - x;
				int pixel = spriteSheet.pixels[xs + ys * spriteSheet.width + tileOffset];
				if (pixel != 0xff00ff && pixel != 0x7F007F) {
					pixels[(x + xPosition) + (y + yPosition) * width] = pixel;
				}
			}
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
