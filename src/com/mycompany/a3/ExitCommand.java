package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

/*
 * Command displays a dialog, then invokes the ExitConfirmCommand if user
 * confirms the exit
 */
public class ExitCommand extends Command {
	public ExitCommand() {
		super("Exit");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		Command[] cmds = {new ExitConfirmCommand(), new Command("Cancel")};
		Dialog.show("Exit", new Label("Are you sure?"), cmds);
	}
}
