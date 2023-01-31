package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

/*
 * This is the command that sets play/pause boolean in the game world when button is pressed
 */
public class PlayPauseCommand extends Command {
	private Game form;

	public PlayPauseCommand(Game f) {
		super("Pause");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		GameWorld gw = form.getGameWorld();

		// If position mode is enabled, prevent the world from unpausing
		if (gw.isPositionMode())
		{
			Command[] cmds = {new Command("Ok")};
			Dialog.show("Error", new Label("Position mode is enabled. Disable it first."), cmds);
			return;
		}

		gw.setPlay(!form.getGameWorld().isPlay());

		if (gw.isPlay()) ((CustomButton)evt.getComponent()).setText("Pause");
		else ((CustomButton)evt.getComponent()).setText("Play");
		
		form.revalidate();
	}
}
