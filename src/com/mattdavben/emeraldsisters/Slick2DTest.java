package com.mattdavben.emeraldsisters;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Slick2DTest extends BasicGame {
	public Slick2DTest()
	  {
	     super("Hello World");
	  }
	 
	  @Override
	  public void init(GameContainer gc) throws SlickException
	  {
	 
	  }
	 
	  @Override
	  public void update(GameContainer gc, int delta) throws SlickException
	  {
	 
	  }
	 
	  @Override
	  public void render(GameContainer gc, Graphics g) throws SlickException
	  {
	     g.drawString("Hello World", 100, 100);
	  }
	 
	  public static void main(String[] args) throws SlickException
	  {
	     AppGameContainer app = new AppGameContainer(new Slick2DTest());
	 
	     app.setDisplayMode(800, 600, false);
	     app.start();
	  }
}
