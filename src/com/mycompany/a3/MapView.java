package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

/*
 * This is an observer that displays the map in text format. After every update, it will update the text map as needed
 */
public class MapView extends Container implements Observer {
	private Observable o;
	private Game form; // This is used with the pointerPressed() function to loop through the collection
	
	public MapView(Game form)
	{
		this.form = form;
		this.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255,0,0)));
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		GameWorld gameWorld = (GameWorld) o;
		if (gameWorld == null) return;
		
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		Ant.getAnt().draw(g, pCmpRelPrnt);

		IIterator it = gameWorld.getObjects().getIterator();

		// Print out all flags via iterator
		while (it.hasNext())
		{
			GameObject x = (GameObject) it.getNext();
			x.draw(g, pCmpRelPrnt);
		}
		
	}

	/*
	 * As a placeholder, map view displays the text info as a span label
	 */
	@Override
	public void update(Observable observable, Object data) {
		o = observable;
		repaint();
	}

	/*
	 * When pressed, it checks what object was selected by the pointer, and sets it as selected. 
	 * The others are unselected
	 */
	@Override
	public void pointerPressed(int x, int y) 
	{
		GameWorld gameWorld = form.getGameWorld();

		//make pointer location relative to parent’s origin
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y); 
		Point pCmpRelPrnt = new Point(getX(), getY()); 

		// If gameworld is in position mode, do only this
		if (gameWorld.isPositionMode())
		{
			Point newLocation = new Point(x - getX(), y - getY());
			gameWorld.getSelected().setLocation(newLocation);
		}
		else
		{
			gameWorld.setSelected(pPtrRelPrnt, pCmpRelPrnt);
		}
		
		
		repaint();
	}
}
