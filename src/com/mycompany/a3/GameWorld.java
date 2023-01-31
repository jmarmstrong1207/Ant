package com.mycompany.a3;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.charts.models.Point;

/*
 * This represents the game world with each object in it
 */
public class GameWorld extends Observable implements Runnable {
	public static final int antColor = ColorUtil.rgb(0, 0, 0);
	public static final int flagColor = ColorUtil.rgb(200, 50, 25);
	public static final int spiderColor = ColorUtil.rgb(0, 0, 255);
	public static final int foodStationColor = ColorUtil.rgb(0, 255, 0);

	public static final int spiderSize = 20;
	public static final int flagSize = 30;
	public int foodStationCount = 5;

	private Ant a = Ant.getAnt();
	
	private Random r;
	
	private GameObjectCollection objects;
	
	// This will be used to get the first flag location in order to spawn the ant
	private static final Point firstFlagLocation = new Point(123,123);

	private static int[] worldSize;
	private int clock;
	private int lives;
	private boolean sound;
	
	private BGSound bg;
	private Sound flagSound;
	private Sound hitSound;
	private Sound foodStationSound;
	
	private boolean play; // true == play, false == pause
	private boolean positionMode; // used to change locations of a fixed object manually with PositionCommand
	
	
	public GameWorld(int[] worldSize)
	{
		// Check if world size is valid. If not, prompt user to set the skin to the correct one and restart
		if (worldSize[0] <= 0 || worldSize[1] <= 0)
		{
			Dialog.show("Error",new Label("One of the world size axis is 0. happens when the skin is not set to iPad III os7. Set it to iPad"
					+ "III os7 and rerun the game"), new Command[] {new ExitConfirmCommand()});
		}
		r = new Random();
		lives = 3;
		clock = 0;
		sound = false;
		objects = new GameObjectCollection();
		play = true;
		
		
		GameWorld.worldSize = worldSize;
	}
	
	public void createSounds()
	{
		bg = new BGSound("bg.wav");

		flagSound = new Sound("flag.wav");
		hitSound = new Sound("hit.wav");

		foodStationSound = new Sound("foodStation.wav");
		foodStationSound.getM().setVolume(40);
	}
	
	protected void setChanged()
	{
		super.setChanged();
	}

	/* 
	 * Set it as changed after update and notify the observers
	 */
	public void updateObservers()
	{
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Initialize game world by adding the game objects into the game
	 */
	public void init()
	{
		objects = new GameObjectCollection();
		
		Random r = new Random();
		
		// Add spiders
		for (int i = 0; i < 8; i++)
		{
			int x = r.nextInt(worldSize[0]);
			int y = r.nextInt(worldSize[1]);

			// Set speed from 5 to 10 for the spider in the last parameter of its constructor
			objects.add(new Spider(spiderSize, spiderColor, new Point(x, y), r.nextInt(30) + 30));
		}
		
		// Add flags
		//////////////////////////
		objects.add(new Flag(flagSize, flagColor, firstFlagLocation, 1)); // Use firstFlagLocation
		objects.add(new Flag(flagSize, flagColor, new Point(800,600), 2));
		objects.add(new Flag(flagSize, flagColor, new Point(500,500), 3));
		objects.add(new Flag(flagSize, flagColor, new Point(10,100), 4));
		objects.add(new Flag(flagSize, flagColor, new Point(95,420), 5));
		
		//////////////////////////
		// Add food stations
		for (int i = 0; i < foodStationCount; i++)
		{
			int x = r.nextInt(worldSize[0] + 1);
			int y = r.nextInt(worldSize[1] + 1);
			
			// Capacity looks high, but it decreases everytime the clock ticks. Think of it like it's counting calories,
			// a high unit value
			objects.add(new FoodStation(new Random().nextInt(101) + 25, foodStationColor, new Point(x, y)));
			
		}
		
		a.setLocation(firstFlagLocation);
		
		// Set it as changed and notify the observers
		updateObservers();
		
	}

	/*
	 * Checks if you won the game by checking if the last flag reached is greater or equal to the amount of flags
	 * in the game.
	 * @return True if won, false if not
	 */
	public boolean checkWin()
	{
		return (a.getLastFlagReached() == 5);
	}
	/*
	 * Checks if all lives are lost.
	 * @return True if all lives are lost, false if not
	 */
	public boolean checkLoss()
	{
		return (getLives() <= 0);
	}
	
	/*
	 * Increases the speed of the ant by 1
	 */
	public void accelerate()
	{
		// the maximum speed possible is a function of maxSpeed * foodLevel percentage. If current speed is lower, then
		// it will be incremented
		a.setSpeed(a.getSpeed() + 10) ;

		updateObservers();
	}
	
	/*
	 * Decreases the speed of the ant by 1, not going lower than 0
	 */
	public void brake()
	{
		a.setSpeed(a.getSpeed() - 10);
		updateObservers();
	}
	
	/*
	 * gets called when ant hits a flag
	 * 
	 */
	public void hitFlag(Flag f)
	{
		if (a.getLastFlagReached() + 1 == f.getSequenceNumber())
		{
			if (sound) flagSound.play();
			a.setLastFlagReached(f.getSequenceNumber());
		}

		updateObservers();
		checkWinLossState();
	}
	
	/*
	 * Decrement ant's health by x. If it loses all health, decrement one life and reset game
	 * @param x amount to decrement health by
	 */
	public void decrementAntHealth(int x)
	{
		a.setHealthLevel(a.getHealthLevel() - 1);
		// Set red color as a function of health level. The lower the health, the redder it is by lowering green
		// and blue values
		a.setColor(
			ColorUtil.rgb(
					ColorUtil.red(a.getColor()) + 25,
					ColorUtil.green(a.getColor()),
					ColorUtil.blue(a.getColor())
			));
		
		// If all health is gone, reset back to the beginning
		if (a.getHealthLevel() <= 0) 
		{
			resetGame();
		}
		updateObservers();
		checkWinLossState();
	}
	
	/* gets called if ant hits a spider
	 * @param s the spider that was hit
	 */
	public void hitSpider(Spider s)
	{
		System.out.println("Hit spider!");

		if (sound) hitSound.play();
					
		// Decrement health
		decrementAntHealth(1);
					
		// Reduce max speed based on the function of health level
		a.setMaxCurrSpeed((int)((double)a.getMaxInitSpeed() * ((double) a.getHealthLevel() / 10.0)));
				
		updateObservers();
	}

	/* 
	 * Does the appropriate things when ant hits food station
	 * @param o The food station that was hit
	 */
	public void hitFoodStation(FoodStation o)
	{
		// If it hasn't been collided already..
		if (o.getCapacity() != 0)
		{
			System.out.println("Hit foodstation!");
			if (sound) foodStationSound.play();
					
			// Add food from food station to ant
			a.setFoodLevel(a.getFoodLevel() + ((FoodStation) o).getCapacity());

			// reduce capacity to 0
			((FoodStation) o).setCapacity(0);
						
			// Make color more light green
			o.setColor(ColorUtil.rgb(200,255,200));
						
			// Add a random food station
			getObjects().add(new FoodStation(r.nextInt(101) + 25, GameWorld.foodStationColor, new Point(r.nextInt(getWorldSize()[0] + 1),
					r.nextInt(getWorldSize()[1] + 1))));
						
			// Increment count of food stations in game
			foodStationCount++;
		}
	}

	public void resetGame()
	{
		// This if statement is to prevent it from saying "resetting world..." if you lost the game
		if (getLives() > 0) 
			System.out.println("Life is lost! Resetting game world");

		// decrement life if health went to 0 or lower
		setLives(getLives() - 1);
			
		// reset health
		a.setHealthLevel(10);

		// reset foodLevel
		a.setFoodLevel(1000);
		
		// reset color
		a.setColor(antColor);
			
		// reset max speed
		a.setMaxCurrSpeed(a.getMaxInitSpeed());
		
		// reset game
		init();
	}

	/*
	 * Increments the clock by one, then moves the spiders and ant based on their rules. Then it checks if there is
	 * a flag, spider, and/or food station in the same location as the ant and applies their respective rules. Then it
	 * checks if the game has been won or lost. If either, then it will output in console and quit the game.
	 */
	public void clockUpdate()
	{
		setClock(getClock() + 1);
		a.move(Game.tickRate);
		a.setFoodLevel(a.getFoodLevel() - a.getFoodConsumptionRate());
		
		
		// Move all movable objects
		IIterator i = getObjects().getIterator();
		while (i.hasNext())
		{
			GameObject o = (GameObject) i.getNext();
			
			if (o instanceof MovableObject)
				((MovableObject) o).move(Game.tickRate);
		}

		// check if moving caused any collisions from ant's perspective
		IIterator iter = getObjects().getIterator();
		while(iter.hasNext())
		{
			GameObject curObj = (GameObject) iter.getNext();
			if(a.collidesWith(curObj))
			{
				a.handleCollision(this, curObj);
			}
		}
	
		// If all health or food level is gone, reset back to the beginning
		if (a.getHealthLevel() <= 0 || a.getFoodLevel() <= 0) 
			resetGame();
		
		checkWinLossState();
		updateObservers();
	}
	

	/*
	 * Checks if the game has won or not, then notifies the user
	 */
	public void checkWinLossState()
	{
		if (checkWin())
		{
			System.out.println("You won!");
			System.exit(0);
		}
		else if (checkLoss())
		{
			System.out.println("You lost!");
			System.exit(0);
		}
	}
	
	
	public GameObjectCollection getObjects()
	{
		return objects;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
		
		if (sound)
			bg.play();
		else 
			bg.pause();
	}

	// Iterates through each object to determine if the pointer coordinates
	// passed through as a parameter hit an object
	public void setSelected(Point pPtrRelPrnt, Point pCmpRelPrnt)
	{
		IIterator it = getObjects().getIterator();
		
		while (it.hasNext())
		{
			GameObject o = (GameObject)it.getNext();
			
			if (o instanceof ISelectable)
			{
				if (((ISelectable)o).contains(pPtrRelPrnt, pCmpRelPrnt))
				{
					((ISelectable)o).setSelected(true);
				}
				else
				{
					((ISelectable)o).setSelected(false);
				}
			}
		}
	}
	
	// Gets the object in the game that's selected
	public GameObject getSelected()
	{
		IIterator it = getObjects().getIterator();
		
		while (it.hasNext())
		{
			GameObject o = (GameObject)it.getNext();
			if (o instanceof FixedObject && ((FixedObject) o).isSelected())
			{
				return o;
			}
		}
		return null;
	}

	public static Point getFirstFlagLocation() {
		return firstFlagLocation;
	}

	public static int[] getWorldSize() {
		return worldSize;
	}

	public static void setWorldSize(int[] worldSize) {
		GameWorld.worldSize = worldSize;
	}

	/*
	 * This function is called by UITimer in Game
	 */
	@Override
	public void run() {
		// Only when play mode is activated will the clock update
		if (play)
		{
			if (sound)
			{
				bg.play();
			}
			clockUpdate();
		}
		else
		{
			if (sound)
			{
				bg.pause();
			}
		}
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean isPositionMode() {
		return positionMode;
	}

	public void setPositionMode(boolean positionMode) {
		this.positionMode = positionMode;
	}
}
