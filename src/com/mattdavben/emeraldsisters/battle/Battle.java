package com.mattdavben.emeraldsisters.battle;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Main;
import com.mattdavben.emeraldsisters.StateChangeEvent;

public class Battle {
	private ArrayList<BattleEntity> characters;
	private ArrayList<BattleEntity> enemies;
	private Image landscape;
	private int durationSeconds = 1;

	public Battle(Image landscape) {
		this.characters = Lists.newArrayList();
		this.enemies = Lists.newArrayList();
		this.landscape = landscape;
	}

	public Battle withCharacter(BattleEntity entity) {
		characters.add(entity);
		return this;
	}

	public Battle withEnemy(BattleEntity entity) {
		enemies.add(entity);
		return this;
	}

	public void render(GameContainer gc, Graphics gr) {
		landscape.draw();
		gr.setColor(Color.white);
		for (BattleEntity entity : characters) {
			gr.drawString(entity.name, 60, 485 + characters.indexOf(entity) * 28);
			gr.drawString(entity.Health() + "", 200, 485 + characters.indexOf(entity) * 28);
		}

		for (BattleEntity entity : enemies) {
			gr.drawString(entity.name, 600, 485 + enemies.indexOf(entity) * 20);
			gr.drawString(entity.Health() + "", 740, 485 + enemies.indexOf(entity) * 20);
		}
	}

	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_SPACE)) {
			endBattle();
		}

		Random pickRandomEnemy = new Random();

		if (input.isKeyDown(Input.KEY_W)) {
			enemies.get(pickRandomEnemy.nextInt(enemies.size()))
					.damage(1);
		} else if (input.isKeyDown(Input.KEY_S)) {
			enemies.get(pickRandomEnemy.nextInt(enemies.size()))
					.damage(1);
		} else if (input.isKeyDown(Input.KEY_A)) {
			enemies.get(pickRandomEnemy.nextInt(enemies.size()))
					.damage(1);
		} else if (input.isKeyDown(Input.KEY_D)) {
			enemies.get(pickRandomEnemy.nextInt(enemies.size()))
					.damage(1);
		}
	}

	public void endBattle() {
		EventNexus.post(new StateChangeEvent(Main.PlayState));
	}
}
