package com.mattdavben.emeraldsisters.entity.collision;

import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.google.common.collect.Lists;

public class QuadTree {

	private int MAX_OBJECTS = 70;
	private int MAX_LEVELS = 8;

	private int level;
	private List<Shape> objects;
	public Shape bounds;
	public QuadTree[] nodes;

	public QuadTree(int level, Shape bounds) {
		this.level = level;
		objects = Lists.newArrayList();
		this.bounds = bounds;
		nodes = new QuadTree[4];
		
		System.out.println(bounds);
	}

	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	private void split() {
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		nodes[0] = new QuadTree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new QuadTree(level + 1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new QuadTree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new QuadTree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}

	private int getIndex(Shape getIndexShape) {
		int index = -1;
		float verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		float horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
		
		boolean topQuadrant = (getIndexShape.getY() < horizontalMidpoint && getIndexShape.getY() + getIndexShape.getHeight() < horizontalMidpoint);
		boolean bottomQuadrant = (getIndexShape.getY() > horizontalMidpoint);

		if (getIndexShape.getX() < verticalMidpoint && getIndexShape.getX() + getIndexShape.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		} else if (getIndexShape.getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}

		return index;
	}
	
	public void insert(Shape insertShape) {
		if (nodes[0] != null) {
			int index = getIndex(insertShape);

			if (index != -1) {
				nodes[index].insert(insertShape);
				return;
			}
		}

		objects.add(insertShape);

		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) split();

			int i = 0;
			while (i < objects.size()) {
				int index = getIndex(objects.get(i));
				if (index != -1) nodes[index].insert(objects.remove(i));
				else i++;
			}
		}
	}

	public List<Shape> retrieve(List<Shape> returnObjects, Shape surroundedShape) {
		int index = getIndex(surroundedShape);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, surroundedShape);
		}

		returnObjects.addAll(objects);

		return returnObjects;
	}

}
