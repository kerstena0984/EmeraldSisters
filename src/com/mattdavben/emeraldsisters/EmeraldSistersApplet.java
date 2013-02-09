package com.mattdavben.emeraldsisters;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.mattdavben.emeraldsisters.Game;

public class EmeraldSistersApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private Game game = new Game();

	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}
}
