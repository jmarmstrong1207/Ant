package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/*
 * The ant class is the main player-driven class. It implements ISteerable, meaning that other objects can
 * steer it
 */
public class Ant extends MovableObject implements ISteerable {
	private static Ant ant;
	private int maxInitSpeed; // Initial max speed
	private int maxCurrSpeed; // The current max speed
	private double foodLevel;
	private double foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	
	private Ant(int size, int color, Point location, int speed, int maxSpeed, int foodLevel, double foodConsumptionRate)
	{
		super(size, color, location, speed);

		this.maxInitSpeed = maxSpeed;
		this.maxCurrSpeed = maxSpeed;
		this.foodLevel = foodLevel;
		this.foodConsumptionRate = foodConsumptionRate;
		healthLevel = 10;
	}
	
	public static Ant getAnt()
	{
		if (ant == null)
		{
			ant = new Ant(20, GameWorld.antColor, GameWorld.getFirstFlagLocation(), 50, 100, 1000, .5);
			ant.setHeading(0);
		}
		return ant;
	}
	
	/*
	 * from interface
	 */
	@Override
	public void setHeading(float f) {
		super.setHeading(f);
		
	}
	
	public int getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(int healthLevel) {
		this.healthLevel = healthLevel;
	}

	public int getLastFlagReached() {
		return lastFlagReached;
	}

	public void setLastFlagReached(int lastFlagReached) {
		this.lastFlagReached = lastFlagReached;
	}

	public void setSpeed(int s)
	{
		// Set speed if its at most max speed
		if (s <= maxCurrSpeed && s >= 0) super.setSpeed(s);
	}

	public double getFoodLevel() {
		return foodLevel;
	}

	public void setFoodLevel(double foodLevel) {
		this.foodLevel = foodLevel;
	}

	public double getFoodConsumptionRate() {
		return foodConsumptionRate;
	}
	
	/*
	 * Used to output information of the ant
	 */
	public String toString()
	{
		return "Ant:  Location: (" + getLocation().getX() + ", " + getLocation().getY() + ") " +
		// TODO: FIND OUT COLOR IMPLEMENTATION
			"color: " + ColorUtil.red(getColor()) + ", " +
				ColorUtil.green(getColor()) + ", " +
				ColorUtil.blue(getColor()) + ", " +
			"heading: " + getHeading() + " " +
			"speed: " + getSpeed() + " " +
			"size: " + getSize() + " " +
			"maxCurrSpeed: " + getMaxCurrSpeed() + " " +
			"foodLevel: " + getFoodLevel() + " " +
			"foodConsumptionRate: " + getFoodConsumptionRate() + "\n";
	}

	public int getMaxInitSpeed() {
		return maxInitSpeed;
	}

	public void setMaxInitSpeed(int maxInitSpeed) {
		this.maxInitSpeed = maxInitSpeed;
	}

	public int getMaxCurrSpeed() {
		return maxCurrSpeed;
	}

	public void setMaxCurrSpeed(int maxCurrSpeed) {
		this.maxCurrSpeed = maxCurrSpeed;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int xLoc = (int)( pCmpRelPrnt.getX() + getLocation().getX());
		int yLoc = (int)( pCmpRelPrnt.getY() + getLocation().getY());
		
		int midPoint = getSize() / 2;

		g.setColor(getColor());
		g.fillArc(xLoc - midPoint, yLoc - midPoint, 2 * getSize(), 2 * getSize(), 0, 360);
		
	}

	@Override
	public void handleCollision(GameWorld gameWorld, ICollider otherObject) {
		if (otherObject instanceof Flag)
		{
			gameWorld.hitFlag((Flag) otherObject);
		}

		else if (otherObject instanceof Spider)
		{
			// There will be a time delay between each collision to prevent continously colliding
			// with the spider
			if (((Spider) otherObject).getClockCollide() < gameWorld.getClock() - 100)
			{
				gameWorld.hitSpider((Spider) otherObject);
				((Spider) otherObject).setClockCollide(gameWorld.getClock());
			}
		}
		
		else if (otherObject instanceof FoodStation)
		{
			gameWorld.hitFoodStation((FoodStation) otherObject);
		}
	}
}
