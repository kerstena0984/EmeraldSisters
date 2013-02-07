package com.mattdavben.emerald;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {
	
	private List<Key> keys = new ArrayList<Key>();

	public class Key {
		public boolean isDown, wasClicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != isDown) {
				isDown = pressed;
			}
		}

	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
	}

	public void keyTyped(KeyEvent ke) {
	}

}
