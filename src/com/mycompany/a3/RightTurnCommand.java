package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Makes ant change heading to the right by 5
 */
public class RightTurnCommand extends Command {
	private Game form;

	public RightTurnCommand(Game f) {
		super("Right turn");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		Ant.getAnt().setHeading(Ant.getAnt().getHeading() + 10);
		form.getGameWorld().updateObservers();
	}

}
