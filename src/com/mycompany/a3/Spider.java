package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/*
 * Spiders are the player's enemy. If the ant touches a spider, it will lose health
 */
public class Spider extends MovableObject {
	private int clockCollide; // This indicates at what clock this spider collided with the ant

	public Spider(int size, int color, Point location, int speed) {
		super(size, color, location, speed);
		clockCollide = 0;
		
		// Set heading to a random 0-359
		super.setHeading(new Random().nextInt(360));
	}
	
	public void move(int tickRate)
	{
		// Everytime a spider moves, the heading randomly changes from the current heading before moving location
		super.setHeading(super.getHeading() + new Random().nextInt() % 20);
		super.move(tickRate);
	}
	
	// Prevent setting color
	public void setColor(int color)
	{
	}
	
	public String toString()
	{
		return "Spider:  Location: (" + getLocation().getX() + ", " + getLocation().getY() + ") " +
			"color: " + ColorUtil.red(getColor()) + ", " +
				ColorUtil.green(getColor()) + ", " +
				ColorUtil.blue(getColor()) + ", " +
			"heading: " + getHeading() + " " +
			"speed: " + getSpeed() + " " + 
			"size: " + getSize() + "\n";
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// Center Coordinate of the object within the MapView coordinate
		int xLoc = (int)( pCmpRelPrnt.getX() + getLocation().getX());
		int yLoc = (int)( pCmpRelPrnt.getY() + getLocation().getY());

		// this is half of base length and height
		int midPoint = getSize() / 2;

		g.setColor(getColor());
		
		g.drawPolygon(
				new int[] {xLoc - midPoint, xLoc + midPoint, xLoc},
				new int[] {yLoc - midPoint, yLoc - midPoint, yLoc + midPoint},
				3);
	}

	@Override
	public void handleCollision(GameWorld gameWorld, ICollider otherObject) {
		// Nothing to add here
	}

	public int getClockCollide() {
		return clockCollide;
	}

	public void setClockCollide(int clockCollide) {
		this.clockCollide = clockCollide;
	}
	
}
