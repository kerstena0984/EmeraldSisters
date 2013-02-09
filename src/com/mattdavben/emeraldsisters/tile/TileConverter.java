package com.mattdavben.emeraldsisters.tile;

public class TileConverter {

	public static Tile convert(int color) {
		switch (color) {
		case 0x4BB44B:
			return new GroundTile(1);
		case 0xA0A0A0:
			return new SolidTile(1, 2);
		default:
			return new GroundTile(0);
		}
	}
}
