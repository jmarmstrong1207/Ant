package com.mycompany.a3;

import com.codename1.charts.models.Point;

/*
 * A type of GameObject that can't change location (unless position mode is enabled)
 */
public abstract class FixedObject extends GameObject implements ISelectable {
	private boolean selected;

	public FixedObject(int s, int c, Point location) {
		super(s, c, location);
		selected = false;
	}
	
	@Override
	public void setSelected(boolean b)
	{
		selected = b;
	}

	@Override
	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt)
	{
		// xLoc and yLoc is pointing to the exact center. To make it easier to compare coordinates, we will subtract
		// midPoint to both to get them to point to the top left corner
		int midPoint = getSize() / 2; 

		float px = pPtrRelPrnt.getX(); // pointer locationlative to
		float py = pPtrRelPrnt.getY(); // parent’s origin
		float xLoc = pCmpRelPrnt.getX() + getLocation().getX() - midPoint;// shape location relative
		float yLoc = pCmpRelPrnt.getY() + getLocation().getY() - midPoint;// to parent’s origin
		if ( (px >= xLoc) && (px <= xLoc + getSize()) && (py >= yLoc) && (py <= yLoc + getSize()) )
			return true;
		else
			return false;
	}
}
