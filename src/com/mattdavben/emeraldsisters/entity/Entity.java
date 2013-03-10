package com.mattdavben.emeraldsisters.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Entity {

	protected String id;
	protected Vector2f position;

	protected RenderComponent renderComponent = null;

	protected ArrayList<Component> components = null;

	public Entity(String id) {
		this.id = id;

		components = new ArrayList<Component>();

		position = new Vector2f(0, 0);
	}
	
	public void addComponent(Component component) {
		if (RenderComponent.class.isInstance(component)) renderComponent = (RenderComponent) component;

		component.setOwnerEntity(this);
		components.add(component);
	}

	public Component getComponent(String id) {
		for (Component comp : components) {
			if (comp.getId().equalsIgnoreCase(id)) return comp;
		}

		return null;
	}

	public Vector2f getPosition() {
		return position;
	}

	public String getId() {
		return id;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		for (Component component : components) {
			component.update(gc, sb, delta);
		}
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if (renderComponent != null) renderComponent.render(gc, sb, gr);
	}
}