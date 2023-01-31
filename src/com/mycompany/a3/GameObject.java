package com.mycompany.a3;

import com.codename1.charts.models.Point;

/*
 * All objects in the game are type GameObject
 */
public abstract class GameObject implements IDrawable, ICollider{
	private Point location;
	private final int size;
	private int color;
	
	
	public GameObject(int s, int c, Point location)
	{
		this.location = location;
		size = s;
		color = c;
	}

	public Point getLocation() {
		return location;
	}

	public int getSize() {
		return size;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	@Override
	public boolean collidesWith(ICollider otherObject)
	{
		boolean result = false;
		float thisCenterX = getLocation().getX(); // find centers
		float thisCenterY = getLocation().getY();
		
		GameObject o = ((GameObject) otherObject);

		float otherCenterX = o.getLocation().getX();
		float otherCenterY = o.getLocation().getY();

		// find dist between centers (use square, to avoid taking roots)
		float dx = thisCenterX - otherCenterX;
		float dy = thisCenterY - otherCenterY;
		float distBetweenCentersSqr = (dx*dx + dy*dy);

		// find square of sum of radii
		int thisRadius = getSize() / 2;
		int otherRadius = o.getSize() / 2;
		int radiiSqr = (thisRadius * thisRadius + 2 * thisRadius * otherRadius
		+ otherRadius * otherRadius);

		if (distBetweenCentersSqr <= radiiSqr) result = true;
		return result;
	}
	
}
