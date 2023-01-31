package com.mycompany.a3;

import java.util.ArrayList;

/*
 * GameObjectCollection that holds all of the game objects. It
 * provides an iterator for the collection
 */
public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> collection;
	
	public GameObjectCollection()
	{
		collection = new ArrayList<>();
	}
	
	public IIterator getIterator()
	{
		return new ObjectIterator();
	}

	@Override
	public void add(Object o) {
		collection.add((GameObject) o);
	}
	
	private class ObjectIterator implements IIterator
	{
		private int currentIndex;
		
		public ObjectIterator()
		{
			// hasNext/getNext has to check index 0 at the beginning,
			// meaning currentIndex must be init to -1
			currentIndex = -1;
		}

		@Override
		public boolean hasNext() {
			return currentIndex + 1 < collection.size();
		}

		@Override
		public Object getNext() {
			return collection.get(++currentIndex);
		}

		@Override
		public void remove() {
			collection.remove(currentIndex);
		}
	}
}

