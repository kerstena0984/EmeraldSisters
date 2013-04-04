package com.mattdavben.emeraldsisters.battle;

import com.mattdavben.emeraldsisters.character.CharacterStats;
import com.mattdavben.emeraldsisters.sprite.CharacterSprite;

public class BattleEntity {

	private CharacterSprite sprite;
	private CharacterStats stats;
	public String name;

	public BattleEntity(String name) {
		this.name = name;
		stats = new CharacterStats();
	}

	public String Name() {
		return name;
	}

	public int Health() {
		return stats.getHealth();
	}

	public void damage(int dmg) {
		stats.damage(dmg);
	}

}
