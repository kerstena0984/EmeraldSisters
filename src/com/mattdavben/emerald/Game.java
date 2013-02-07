package com.mattdavben.emerald;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 320;
	private static final int WIDTH = HEIGHT * 16 / 10;
	private static final byte SCALE = 2;
	private static final String NAME = "Emerald Sisters | Pre-Alpha 0.0.1";

	private boolean running = false;

	public Game() {

	}

	private void start() {
		running = true;
		new Thread(this).start();
	}

	private void stop() {
		running = false;
	}

	private void init() {

	}

	private void update() {

	}

	private void render() {

	}

	public void run() {

	}

	public static void main(String[] argv) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}
}