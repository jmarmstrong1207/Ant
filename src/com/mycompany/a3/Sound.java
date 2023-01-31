package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/*
* This class encapsulates a sound file as an Media inside a
* “Sound” object, and provides a method for playing the Sound.
*/
public class Sound {
	private Media m;

	public Sound(String fileName) {
		if (Display.getInstance().getCurrent() == null) {
			System.out.println("Error: Create sound objects after calling show()!");
			System.exit(0);
		}
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			m = MediaManager.createMedia(is, "audio/wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
//start playing the sound from time zero (beginning of the sound file)
		m.setTime(0);
		m.play();
	}

	public Media getM() {
		return m;
	}

	public void setM(Media m) {
		this.m = m;
	}
}