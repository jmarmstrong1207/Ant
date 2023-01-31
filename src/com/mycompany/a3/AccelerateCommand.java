package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Accelerates ant, invokes accelerate()
 */
public class AccelerateCommand extends Command {
	private Game form;

	public AccelerateCommand(Game f) {
		super("Accelerate");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		form.getGameWorld().accelerate();
	}

}
