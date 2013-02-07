package com.mattdavben.emerald;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mattdavben.emerald.entity.Hero;
import com.mattdavben.emerald.graphics.Screen;
import com.mattdavben.emerald.graphics.SpriteSheet;
import com.mattdavben.emerald.level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int HEIGHT = 320;
	private static final int WIDTH = HEIGHT * 16 / 10;
	private static final byte SCALE = 2;
	private static final String NAME = "Emerald Sisters | Pre-Alpha 0.0.1";

	private boolean running = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private Screen screen;
	private InputHandler input;
	private Level level;
	private Hero hero;
	
	private int walkDist, direction;
	private SpriteSheet characterSheet;
	private int characterX, characterY;

	public Game() {
		input = new InputHandler(this);
		level = new Level(WIDTH, HEIGHT);
		hero = new Hero();
		characterX = WIDTH / 2;
		characterY = HEIGHT / 2;
		try {
			characterSheet = new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/Kate.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		running = true;
		new Thread(this).start();
	}

	private void stop() {
		running = false;
	}

	private void init() {
		level.add(hero);

		try {
			screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/SpriteSheet.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		if (input.up.isDown) {
			screen.yOffset--;
			walkDist++;
			direction = 0;
			characterY--;
		}
		else if (input.down.isDown) {
			screen.yOffset++;
			walkDist++;
			direction = 1;
			characterY++;
		}
		if (input.left.isDown) {
			screen.xOffset--;
			walkDist++;
			direction = 2;
			characterX--;
		}
		else if (input.right.isDown) {
			screen.xOffset++;
			walkDist++;
			direction = 3;
			characterX++;
		}
	}

	private void draw() {
		BufferStrategy bstrat = this.getBufferStrategy();
		if (bstrat == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		level.render(screen, screen.xOffset, screen.yOffset);

		{
			int walkingSpeed = 3;
			int switchSprite = ((walkDist >> walkingSpeed) & 1);
			int switchFeet = ((walkDist >> walkingSpeed) & 3);
			int flipSprite = ((walkDist >> 4) & 1);
			int nextSprite = 0;
			if (switchFeet == 0 || switchFeet == 2) nextSprite = 0;
			if (switchFeet == 1) nextSprite = 1;
			if (switchFeet == 3) nextSprite = 2;

			if (direction == 0) screen.renderSprite(characterSheet, characterX - 8, characterY - 16, 6 + switchSprite, 0 + flipSprite);
			if (direction == 1) screen.renderSprite(characterSheet, characterX - 8, characterY - 16, 0 + nextSprite, 0);
			if (direction == 2) screen.renderSprite(characterSheet, characterX - 8, characterY - 16, 3 + nextSprite, 0);
			if (direction == 3) screen.renderSprite(characterSheet, characterX - 8, characterY - 16, 3 + nextSprite, 1);
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
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

		init();

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