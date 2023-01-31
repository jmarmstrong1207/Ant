package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/*
 * These are checkpoints that the ant must reach in the order of sequence to win the game
 */
public class Flag extends FixedObject {
	
	private int sequenceNumber;
	
	public Flag(int size, int color, Point location, int seqNum) {
		super(size, color, location);
		this.sequenceNumber = seqNum;
	}
	
	/*
	 * Disable changing the color after it's set, throws exception
	 */
	public void setColor() throws Exception
	{
		throw new Exception("Flags cannot change color once instantiated");
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	/*
	 * Used to output data of the flag
	 */
	public String toString()
	{
		return "Flag:  Location: (" + getLocation().getX() + ", " + getLocation().getY() + ") " +
			"color: " + ColorUtil.red(getColor()) + ", " +
				ColorUtil.green(getColor()) + ", " +
				ColorUtil.blue(getColor()) + ", " +
			"size: " + getSize() + " " +
			"seqNum: " + getSequenceNumber() + "\n";
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// Center Coordinate of the object within the MapView coordinate
		int xLoc = (int) (pCmpRelPrnt.getX() + getLocation().getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + getLocation().getY());

		// this is half of base length and height
		int midPoint = getSize() / 2;

		g.setColor(getColor());
		
		// If the object is selected by the pointer, the shape will be hollow
		if (isSelected())
			g.drawPolygon(new int[] { xLoc - midPoint, xLoc + midPoint, xLoc },
					new int[] { yLoc - midPoint, yLoc - midPoint, yLoc + midPoint }, 3);
		else
			g.fillPolygon(new int[] { xLoc - midPoint, xLoc + midPoint, xLoc },
					new int[] { yLoc - midPoint, yLoc - midPoint, yLoc + midPoint }, 3);

		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(sequenceNumber), xLoc, yLoc);
	}

	@Override
	public void handleCollision(GameWorld gameWorld, ICollider otherObject) {
		// Nothing to add here
		
	}
	

}
