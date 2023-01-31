package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Makes ant change heading to the left by 5
 */
public class LeftTurnCommand extends Command {
	private Game form;

	public LeftTurnCommand(Game f) {
		super("left turn");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		Ant.getAnt().setHeading(Ant.getAnt().getHeading() - 10);
		form.getGameWorld().updateObservers();
	}
}
