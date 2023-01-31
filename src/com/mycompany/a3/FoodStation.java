package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/*
 * Ants have a food level and it depletes with each clock tick. Food stations provide food for the ant
 */
public class FoodStation extends FixedObject {
	private int capacity; // Capacity is initially the same value as size

	public FoodStation(int size, int color , Point location) {
		super(size, color, location);
		capacity = size;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/*
	 * Used to output data of the station
	 */
	public String toString()
	{
		return "FoodStation:  Location: (" + getLocation().getX() + ", " + getLocation().getY() + ") " +
			"color: " + ColorUtil.red(getColor()) + ", " +
				ColorUtil.green(getColor()) + ", " +
				ColorUtil.blue(getColor()) + ", " +
			"size: " + getSize() + " " +
			"capacity: " + getCapacity() + "\n";
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int xLoc = (int)( pCmpRelPrnt.getX() + getLocation().getX());
		int yLoc = (int)( pCmpRelPrnt.getY() + getLocation().getY());
		
		int midPoint = getSize() / 2;
		g.setColor(getColor());
		
		if (isSelected())
			g.drawRect(xLoc - midPoint, yLoc - midPoint, getSize(), getSize());
		else
			g.fillRect(xLoc - midPoint, yLoc - midPoint, getSize(), getSize());

		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(capacity), xLoc - midPoint, yLoc - midPoint);
	}

	@Override
	public void handleCollision(GameWorld gameWorld, ICollider otherObject) {
	}
}
