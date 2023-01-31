package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

/*
 * This command sets the positionMode boolean in gameWorld true, which will be detected
 * by the MapView's pointerPressed() function to change positions
 */
public class PositionCommand extends Command {
	Game form;
	public PositionCommand(Game f) {
		super("Turn position mode on");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		GameWorld gameWorld = form.getGameWorld();

		// Disallow the command to run if the game isn't paused
		if (gameWorld.isPlay())
		{
			Command[] cmds = {new Command("Ok")};
			Dialog.show("Error", new Label("Game is not paused. Pause it to change position."), cmds);
			return;
		}
		else if (gameWorld.getSelected() == null)
		{
			Command[] cmds = {new Command("Ok")};
			Dialog.show("Error", new Label("No object selected. Disable position mode, select an object, then try again"), cmds);
			return;
		}

		gameWorld.setPositionMode(!gameWorld.isPositionMode());
		if (gameWorld.isPositionMode())
		{
			((CustomButton)evt.getComponent()).setText("Turn position mode off");
		}
		else
		{
			((CustomButton)evt.getComponent()).setText("Turn position mode on");
		}
	}
}
