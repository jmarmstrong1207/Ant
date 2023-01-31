package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

/*
 * This custom button is used to set the styles of my preferences for all of the
 * buttons in the GUI
 */
public class CustomButton extends Button {
	public CustomButton()
	{
		super();

		getUnselectedStyle().setBgTransparency(255);
		getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		getAllStyles().setBorder(Border.createLineBorder(2));
	}

}
