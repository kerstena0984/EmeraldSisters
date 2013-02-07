package com.mattdavben.emerald;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 320;
	private static final int WIDTH = HEIGHT * 16 / 10;
	private static final byte SCALE = 2;
	private static final String NAME = "Emerald Sisters | Pre-Alpha 0.0.1";

	private boolean running = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int updateCount;

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
		updateCount++;
	}

	private void draw() {
		BufferStrategy bstrat = this.getBufferStrategy();
		if (bstrat == null) {
			createBufferStrategy(3);
			return;
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = i * updateCount;
		}

		Graphics g = bstrat.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bstrat.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		int updates = 0;
		int frames = 0;
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		boolean shouldDraw = false;
		long lastTimer = System.currentTimeMillis();
		int prints = 0;
		int totalFrames = 0;

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (unprocessed >= 1) {
				updates++;
				update();
				unprocessed -= 1;
				shouldDraw = true;
			}

			if (shouldDraw) {
				frames++;
				draw();
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				prints++;
				totalFrames += frames;
				System.out.println(frames + " fps|" + updates + " ups|" + (totalFrames / prints) + " avg fps");
				frames = 0;
				updates = 0;
			}
		}
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