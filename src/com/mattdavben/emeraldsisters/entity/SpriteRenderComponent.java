package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SpriteRenderComponent extends RenderComponent {

	protected SpriteSheet characterSheet;
	protected Animation current, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	protected int[] animationLength = { 150, 150, 150, 150 };

	public SpriteRenderComponent(String id, SpriteSheet sheet) {
		super(id);
		this.characterSheet = sheet;

		characterSheet.setFilter(Image.FILTER_NEAREST);

		setAnimations();

		current = playerWalkingDown;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		Vector2f pos = owner.getPosition();

		current.draw(pos.x, pos.y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
	}

	private void setAnimations() {
		Image down0 = characterSheet.getSubImage(0, 0);
		Image down1 = characterSheet.getSubImage(1, 0);
		Image down2 = characterSheet.getSubImage(2, 0);

		Image left0 = characterSheet.getSubImage(3, 0);
		Image left1 = characterSheet.getSubImage(4, 0);
		Image left2 = characterSheet.getSubImage(5, 0);

		Image up0 = characterSheet.getSubImage(6, 0);
		Image up1 = characterSheet.getSubImage(7, 0);

		Image[] walkingDown = { down0, down1, down0, down2 };
		Image[] walkingUp = { up0, up1, up0.getFlippedCopy(true, false), up1.getFlippedCopy(true, false) };
		Image[] walkingLeft = { left0, left1, left0, left2 };
		Image[] walkingRight = { left0.getFlippedCopy(true, false), left1.getFlippedCopy(true, false), left0.getFlippedCopy(true, false), left2.getFlippedCopy(true, false), };

		playerWalkingDown = new Animation(walkingDown, animationLength, false);
		playerWalkingUp = new Animation(walkingUp, animationLength, false);
		playerWalkingLeft = new Animation(walkingLeft, animationLength, false);
		playerWalkingRight = new Animation(walkingRight, animationLength, false);
	}
}