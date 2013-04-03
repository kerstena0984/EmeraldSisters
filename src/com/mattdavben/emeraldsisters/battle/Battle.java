package com.mattdavben.emeraldsisters.battle;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.google.common.collect.Lists;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Main;
import com.mattdavben.emeraldsisters.StateChangeEvent;

public class Battle {
	private List<BattleEntity> characters;
	private List<BattleEntity> enemies;
	private Image landscape;
	private int durationSeconds = 1;
	private int timedBattle = durationSeconds * 1000;
	private int currentTime = 0;

	private Battle(BattleBuilder builder) {
		this.characters = builder.builderCharacters;
		this.enemies = builder.builderEnemies;
		this.landscape = builder.landscape;
	}

	public static class BattleBuilder {
		List<BattleEntity> builderCharacters = Lists.newArrayList();
		List<BattleEntity> builderEnemies = Lists.newArrayList();
		Image landscape;

		public BattleBuilder WithCharacter(BattleEntity entity) {
			this.builderCharacters.add(entity);
			return this;
		}

		public BattleBuilder WithEnemy(BattleEntity entity) {
			this.builderEnemies.add(entity);
			return this;
		}

		public BattleBuilder withLandscape(Image landscape) {
			this.landscape = landscape;
			return this;
		}

		public Battle build() {
			return new Battle(this);
		}
	}

	public void render(GameContainer gc, Graphics gr) {
		gr.setColor(Color.white);
		for (BattleEntity entity : characters) {
			gr.drawString(entity.name, 50, 60);
		}
	}

	public void update(GameContainer gc, int delta) {
		currentTime += delta;

		if (currentTime >= timedBattle) {
			endBattle();
		}
	}

	public void endBattle() {
		EventNexus.post(new StateChangeEvent(Main.PlayState));
	}
}
