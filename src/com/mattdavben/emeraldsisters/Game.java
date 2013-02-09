package com.mattdavben.emeraldsisters;

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

import com.mattdavben.emeraldsisters.entity.Hero;
import com.mattdavben.emeraldsisters.graphics.Screen;
import com.mattdavben.emeraldsisters.graphics.SpriteSheet;
import com.mattdavben.emeraldsisters.level.Level;

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
	private InputHandler input = new InputHandler(this);
	private Level level;
	private Hero hero;

	public Game() {
		level = new Level(WIDTH, HEIGHT);
		hero = new Hero();
		hero.input(input);
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
		level.update();
	}

	private void draw() {
		BufferStrategy bstrat = this.getBufferStrategy();
		if (bstrat == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		level.render(screen, screen.xOffset, screen.yOffset);
		level.renderSprites(screen, getXScroll(), getYScroll());

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

	private int getYScroll() {
		int yScroll = hero.getY() - screen.height / 2;
		// if (yScroll < 0) yScroll = 0;
		// if (yScroll > (level.getHeight() - (screen.height / 16)) << 4)
		// yScroll = (level.getHeight() - (screen.height / 16)) << 4;
		return yScroll;
	}

	private int getXScroll() {
		int xScroll = hero.getX() - screen.width / 2;
		// if (xScroll < 0) xScroll = 0;
		// if (xScroll > (level.getWidth() - (screen.width / 16)) << 4) xScroll
		// = (level.getWidth() - (screen.width / 16)) << 4;
		return xScroll;
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