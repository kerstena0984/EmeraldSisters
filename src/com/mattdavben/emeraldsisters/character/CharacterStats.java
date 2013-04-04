package com.mattdavben.emeraldsisters.character;

public class CharacterStats {
	public int health;
	
	public CharacterStats(){
		health = 150;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void damage(int dmg){
		health -= dmg;
	}
}
