package com.mattdavben.emeraldsisters.sprite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.mattdavben.emeraldsisters.character.Player.Direction;

public class CharacterSprite {

	private SpriteSheet characterSheet;
	private Animation current;
	private Animation playerWalkingNorth, playerWalkingSouth,
			playerWalkingWest, playerWalkingEast;
	private Animation playerWalkingNorthWest, playerWalkingNorthEast,
			playerWalkingSouthWest, playerWalkingSouthEast;
	private int msBetweenAnimationFrames = 100;
	private int[] animationLength = { msBetweenAnimationFrames,
			msBetweenAnimationFrames, msBetweenAnimationFrames,
			msBetweenAnimationFrames, msBetweenAnimationFrames,
			msBetweenAnimationFrames, msBetweenAnimationFrames,
			msBetweenAnimationFrames };
	private Direction currentDirection;
	public final int spriteWidth = 32;
	public final int spriteHeight = 48;

	public CharacterSprite(String spriteSheet) throws SlickException {
		this.characterSheet = new SpriteSheet("res/" + spriteSheet + ".png",
				spriteWidth, spriteHeight);

		characterSheet.setFilter(Image.FILTER_NEAREST);

		setAnimations();

		currentDirection = Direction.NORTH;
	}

	public void draw(Vector2f currentPosition, Vector2f viewport) {
		animateBasedOnCurrentDirection();

		current.draw(currentPosition.x - viewport.x, currentPosition.y
				- viewport.y);
	}

	public void update(int delta) {
		current.update(delta);
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public void updateDirection(Direction direction) {
		this.currentDirection = direction;
	}

	private void animateBasedOnCurrentDirection() {
		switch (currentDirection) {
		case NORTH:
			current = playerWalkingNorth;
			break;
		case NORTHWEST:
			current = playerWalkingNorthWest;
			break;
		case NORTHEAST:
			current = playerWalkingNorthEast;
			break;
		case SOUTH:
			current = playerWalkingSouth;
			break;
		case SOUTHWEST:
			current = playerWalkingSouthWest;
			break;
		case SOUTHEAST:
			current = playerWalkingSouthEast;
			break;
		case WEST:
			current = playerWalkingWest;
			break;
		case EAST:
			current = playerWalkingEast;
			break;
		}
	}

	private void setAnimations() {
		// Image north0 = characterSheet.getSubImage(0, 0);
		// Image north1 = characterSheet.getSubImage(1, 0);
		// Image north2 = characterSheet.getSubImage(2, 0);
		//
		// Image northwest0 = characterSheet.getSubImage(3, 0);
		// Image northwest1 = characterSheet.getSubImage(4, 0);
		// Image northwest2 = characterSheet.getSubImage(5, 0);
		//
		// Image northeast0 = characterSheet.getSubImage(6, 0);
		// Image northeast1 = characterSheet.getSubImage(7, 0);
		// Image northeast2 = characterSheet.getSubImage(8, 0);
		//
		// Image southwest0 = characterSheet.getSubImage(3, 1);
		// Image southwest1 = characterSheet.getSubImage(4, 1);
		// Image southwest2 = characterSheet.getSubImage(5, 1);
		//
		// Image southeast0 = characterSheet.getSubImage(6, 1);
		// Image southeast1 = characterSheet.getSubImage(7, 1);
		// Image southeast2 = characterSheet.getSubImage(8, 1);
		//
		// Image east0 = characterSheet.getSubImage(0, 2);
		// Image east1 = characterSheet.getSubImage(1, 2);
		// Image east2 = characterSheet.getSubImage(2, 2);
		//
		// Image west0 = characterSheet.getSubImage(3, 2);
		// Image west1 = characterSheet.getSubImage(4, 2);
		// Image west2 = characterSheet.getSubImage(5, 2);

		Image south0 = characterSheet.getSubImage(0, 1);
		Image south1 = characterSheet.getSubImage(1, 1);
		Image south2 = characterSheet.getSubImage(2, 1);
		Image south3 = characterSheet.getSubImage(3, 1);
		Image south4 = characterSheet.getSubImage(4, 1);

		// Image[] north = { north0, north1, north0, north2 };
		Image[] south = { south0, south1, south2, south1, south0, south3,
				south4, south3, };
		// Image[] east = { east0, east1, east0, east2 };
		// Image[] west = { west0, west1, west0, west2 };
		// Image[] southwest = { southwest0, southwest1, southwest0, southwest2
		// };
		// Image[] southeast = { southeast0, southeast1, southeast0, southeast2
		// };
		// Image[] northwest = { northwest0, northwest1, northwest0, northwest2
		// };
		// Image[] northeast = { northeast0, northeast1, northeast0, northeast2
		// };

		playerWalkingSouth = new Animation(south, animationLength, false);
		playerWalkingNorth = new Animation(south, animationLength, false);
		playerWalkingWest = new Animation(south, animationLength, false);
		playerWalkingEast = new Animation(south, animationLength, false);
		playerWalkingSouthWest = new Animation(south, animationLength, false);
		playerWalkingNorthWest = new Animation(south, animationLength, false);
		playerWalkingSouthEast = new Animation(south, animationLength, false);
		playerWalkingNorthEast = new Animation(south, animationLength, false);
	}

}
