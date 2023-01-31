package com.mycompany.a3;

/*
 * This interface is for every game object to detect and handle collisions with each other
 */
public interface ICollider {
	public boolean collidesWith(ICollider otherObject);
	public void handleCollision(GameWorld gameWorld, ICollider otherObject);

}
