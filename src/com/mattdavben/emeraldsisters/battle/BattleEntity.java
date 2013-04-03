package com.mattdavben.emeraldsisters.battle;

import com.mattdavben.emeraldsisters.character.CharacterStats;
import com.mattdavben.emeraldsisters.sprite.CharacterSprite;

public class BattleEntity {
	
	private CharacterSprite sprite;
	private CharacterStats stats;
	public static String name;
	
	public BattleEntity(String name){
		this.name = name;
	}
	
}
