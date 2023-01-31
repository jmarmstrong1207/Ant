package com.mycompany.a3;

/*
 * Collection interface
 */
public interface ICollection {
	/*
	 * Add object into collection
	 */
	public void add(Object o);
	
	/*
	 * Get iterator for collection
	 */
	public IIterator getIterator();
}
