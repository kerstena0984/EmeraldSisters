package com.mattdavben.emeraldsisters.graphics;

public class Fonts {
	private static final int BITS_PER_LETTER = 8;

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " //
			+ "abcdefghijklmnopqrstuvwxyz      " //
			+ "0123456789:;.=,?!\"#$%&'()*+-/   ";

	private static final int FIRST_LINE_WITH_LETTERS = 29;

	public void draw(String msg, Screen screen, int x, int y, int color) {
		for (int i = 0; i < msg.length(); i++) {
			int characterIndex = chars.indexOf(msg.charAt(i));
			if (characterIndex >= 0) {
				screen.renderFontTile(x + i * BITS_PER_LETTER, y, characterIndex + FIRST_LINE_WITH_LETTERS * 32, color);
			}
		}
	}
}
