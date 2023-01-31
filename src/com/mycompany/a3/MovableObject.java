package com.mycompany.a3;

import com.codename1.charts.models.Point;
import java.lang.Math;

/*
 * A type of game object that can change its location
 */
public abstract class MovableObject extends GameObject {
	
	private float heading;
	private int speed;

	public MovableObject(int s, int c, Point location, int speed) {
		super(s, c, location);
		this.speed = speed;
	}
	
	public void move(int tickRate)
	{
		int worldX = GameWorld.getWorldSize()[0] ;
		int worldY = GameWorld.getWorldSize()[1] ;
		
		// Distance is a function of tick rate and speed
		float dist = (float)getSpeed() * (float)(tickRate / 1000.0);

		float deltaX = (float)Math.cos(Math.toRadians(90 - getHeading())) * dist;
		float deltaY = (float)Math.sin(Math.toRadians(90 - getHeading())) * dist;

		float finalX = getLocation().getX() + deltaX;
		float finalY = getLocation().getY() + deltaY;
		
		if (finalX > worldX) 
			finalX = worldX;
		else if (finalX < 0)
			finalX = 0;

		if (finalY > worldY)
			finalY = worldY;
		else if (finalY < 0)
			finalY = 0;

		super.setLocation(new Point(finalX, finalY));
	}
	
	public void setHeading(float f)
	{
		heading = f;
	}

	public float getHeading() {
		return heading;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	
	
	
}
