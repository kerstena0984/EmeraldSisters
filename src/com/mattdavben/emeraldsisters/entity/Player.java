package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.Subscribe;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Viewport;
import com.mattdavben.emeraldsisters.player.PlayerMoveEvent;
import com.mattdavben.emeraldsisters.player.PlayerMoveListener;

public class Player extends WorldEntity implements PlayerMoveListener {

	private SpriteSheet characterSheet;
	private Animation current;
	private Animation playerWalkingNorth, playerWalkingSouth, playerWalkingWest, playerWalkingEast;
	private Animation playerWalkingNorthWest, playerWalkingNorthEast, playerWalkingSouthWest, playerWalkingSouthEast;
	private int[] animationLength = { 100, 100, 100, 100, 100, 100, 100, 100 };
	public final static int SPRITE_WIDTH = 32;
	public final static int SPRITE_HEIGHT = 48;
	private Input input;
	private Direction currentDirection;
	public boolean blockedLeft;
	public boolean blockedRight;
	public boolean blockedUp;
	public boolean blockedDown;

	public Player(Input input, Vector2f startingPosition) throws SlickException {
		EventNexus.register(this);

		this.characterSheet = new SpriteSheet("TestingSprite.png", SPRITE_WIDTH, SPRITE_HEIGHT);
		this.input = input;

		this.currentPosition = startingPosition;

		float currentX = currentPosition.x;
		float currentY = currentPosition.y;
		float collisionBoxWidth = SPRITE_WIDTH - 8;
		float collisionBoxHeight = SPRITE_HEIGHT / 2 - 2;
		float collisionBoxOffset = 1;

		float[] points = { currentX + collisionBoxOffset, currentY + collisionBoxOffset,
				currentX + collisionBoxOffset + collisionBoxWidth, currentY + collisionBoxOffset,
				currentX + collisionBoxOffset + collisionBoxWidth, currentY + collisionBoxOffset + collisionBoxHeight,
				currentX + collisionBoxOffset, currentY + collisionBoxOffset + collisionBoxHeight };

		collisionShape = new Polygon(points);
		characterSheet.setFilter(Image.FILTER_NEAREST);

		setAnimations();

		currentDirection = Direction.NORTH;
		blockedLeft = false;
		blockedRight = false;
		blockedUp = false;
		blockedDown = false;
	}

	public void render(Viewport viewport) {
		animateBasedOnCurrentDirection();

		current.draw(currentPosition.x - viewport.position.x, currentPosition.y - viewport.position.y);
	}

	public void update(int delta) {
		updateDirectionBasedOnUserInput();

		float speedInTilesPerSecond = 3.0f;
		float pixelsPerTile = 32.0f;
		float distance = speedInTilesPerSecond * pixelsPerTile * delta / 1000f;

		if (movementButtonIsPressed()) {
			current.update(delta);

			if (input.isKeyDown(Input.KEY_UP) && !blockedUp) {
				currentPosition.y -= distance;
			} else if (input.isKeyDown(Input.KEY_DOWN) && !blockedDown) {
				currentPosition.y += distance;
			}
			if (input.isKeyDown(Input.KEY_LEFT) && !blockedLeft) {
				currentPosition.x -= distance;
			} else if (input.isKeyDown(Input.KEY_RIGHT) && !blockedRight) {
				currentPosition.x += distance;
			}
		}

		collisionShape.setX(currentPosition.x + 4);
		collisionShape.setY(currentPosition.y + 24);
	}

	private enum Direction {
		NORTH, SOUTH, WEST, EAST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST;
	}

	private void updateDirectionBasedOnUserInput() {
		if (input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)) {
			currentDirection = Direction.NORTH;
		} else if (input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_RIGHT)
				&& !input.isKeyDown(Input.KEY_LEFT)) {
			currentDirection = Direction.SOUTH;
		} else if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_UP)
				&& !input.isKeyDown(Input.KEY_DOWN)) {
			currentDirection = Direction.WEST;
		} else if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP)
				&& !input.isKeyDown(Input.KEY_DOWN)) {
			currentDirection = Direction.EAST;
		} else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {
			currentDirection = Direction.NORTHEAST;
		} else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {
			currentDirection = Direction.NORTHWEST;
		} else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)) {
			currentDirection = Direction.SOUTHEAST;
		} else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {
			currentDirection = Direction.SOUTHWEST;
		}
	}

	private boolean movementButtonIsPressed() {
		return input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT)
				|| input.isKeyDown(Input.KEY_RIGHT);
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

//		Image[] north = { north0, north1, north0, north2 };
		Image[] south = { south0, south1, south2, south1, south0, south3, south4, south3, };
//		Image[] east = { east0, east1, east0, east2 };
//		Image[] west = { west0, west1, west0, west2 };
//		Image[] southwest = { southwest0, southwest1, southwest0, southwest2 };
//		Image[] southeast = { southeast0, southeast1, southeast0, southeast2 };
//		Image[] northwest = { northwest0, northwest1, northwest0, northwest2 };
//		Image[] northeast = { northeast0, northeast1, northeast0, northeast2 };

		playerWalkingSouth = new Animation(south, animationLength, false);
		playerWalkingNorth = new Animation(south, animationLength, false);
		playerWalkingWest = new Animation(south, animationLength, false);
		playerWalkingEast = new Animation(south, animationLength, false);
		playerWalkingSouthWest = new Animation(south, animationLength, false);
		playerWalkingNorthWest = new Animation(south, animationLength, false);
		playerWalkingSouthEast = new Animation(south, animationLength, false);
		playerWalkingNorthEast = new Animation(south, animationLength, false);
	}

	@Subscribe
	public void listen(PlayerMoveEvent event) {
		this.currentPosition = event.getNewPosition();
	}

}
