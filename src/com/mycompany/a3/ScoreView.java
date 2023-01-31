package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;

/*
 * This is an observer that displays the score info. After every update, it will update as needed
 */
public class ScoreView extends Container implements Observer {
	private Label lives;
	private Label clock;
	private Label lastFlagReached;
	private Label foodLevel;
	private Label healthLevel;
	private Label sound;
	
	
	public ScoreView()
	{
		// Add all of the labels to the container
		lives = new Label("Lives: ");
		lives.getAllStyles().setPaddingRight(2);
		clock = new Label("Clock: ");
		clock.getAllStyles().setPaddingRight(2);
		lastFlagReached = new Label("lastFlagReached: ");
		lastFlagReached.getAllStyles().setPaddingRight(2);
		foodLevel = new Label("foodLevel: ");
		foodLevel.getAllStyles().setPaddingRight(2);
		healthLevel = new Label("healthLevel: ");
		healthLevel.getAllStyles().setPaddingRight(2);
		sound = new Label("sound: ");
		sound.getAllStyles().setPaddingRight(2);
		
		add(lives);
		add(clock);
		add(lastFlagReached);
		add(foodLevel);
		add(healthLevel);
		add(sound);
	}


	/*
	 * Sets the labels to the new values from game world
	 */
	@Override
	public void update(Observable observable, Object data) {
		GameWorld gameWorld = (GameWorld) observable;
		Ant a = Ant.getAnt();
		
		lives.setText("Lives: " + gameWorld.getLives());
		clock.setText("Clock: " + gameWorld.getClock());
		lastFlagReached.setText("lastFlagReached: " + a.getLastFlagReached());
		foodLevel.setText("foodLevel: " + a.getFoodLevel());
		healthLevel.setText("healthLevel: " + a.getHealthLevel());
		sound.setText("Sound: " + gameWorld.isSound());
		
		revalidate();
	}
}
