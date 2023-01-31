package com.mycompany.a3;

/*
 * Used for GameObjectCollection
 */
public interface IIterator {
	
	/*
	 * Checks if there is a next object
	 * @return true if next object, false if not
	 */
	public boolean hasNext();
	
	/*
	 * Get next object
	 * @return Next object
	 */
	public Object getNext();
	
	/*
	 * Removes current object
	 *
	 */
	public void remove(); 
	

}
