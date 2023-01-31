package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Exits the game when invoked
 */
public class ExitConfirmCommand extends Command {

	public ExitConfirmCommand() {
		super("Confirm");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		System.exit(0);
	}

}
