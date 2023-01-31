package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Decelerates the ant, invokes brake();
 */
public class BrakeCommand extends Command {
	private Game form;

	public BrakeCommand(Game f) {
		super("Brake");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		form.getGameWorld().brake();
	}

}
