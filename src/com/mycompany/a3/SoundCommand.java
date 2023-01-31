package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/*
 * Disables or enables sound in the game
 */
public class SoundCommand extends Command {
	private Game form;

	public SoundCommand(Game f) {
		super("Toggle Sound");
		form = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if (((CheckBox)evt.getComponent()).isSelected())
			form.getGameWorld().setSound(true);
		else 
			form.getGameWorld().setSound(false);

		form.getGameWorld().updateObservers();
	}

}
