package com.mycompany.a3;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/*
 * Displays a dialog that displays the user info about the CSC 133 project
 */
public class AboutCommand extends Command {
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		SpanLabel text = new SpanLabel();
		text.setText(
			"Author:James Armstrong\n" +
			"Class: CSC 133\n" +
			"Professor: Pinar Muyan-Ozcelik\n" +
			"Semester: Fall 2022\n"
		);
		Command[] cmds = new Command[] {new Command("Close")};
		Dialog.show("About", text, 
				cmds );
	}

}
