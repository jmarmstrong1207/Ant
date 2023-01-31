package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

// Objects implementing this interface can draw things onto a Graphics object
public interface IDrawable {
	public void draw(Graphics g, Point pCmpRelPrnt);
}
