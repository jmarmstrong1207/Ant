package com.mycompany.a3;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/*
 * Displays a dialog showing all keybinds in the game
 */
public class HelpCommand extends Command {
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		SpanLabel text = new SpanLabel();
		text.setText(
			"Keybinds:\n" +
			"Accelerate: a\n" +
			"Brake: b\n" +
			"Turn left: l\n" +
			"Turn right: r\n" +
			"update clock: t\n"
			);
		
		Command[] cmds = new Command[] {new Command("Close")};
		Dialog.show("About", text, 
				cmds );
	}

}
