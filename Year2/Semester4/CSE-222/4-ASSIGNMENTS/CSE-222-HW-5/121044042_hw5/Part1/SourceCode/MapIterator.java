import java.util.*;

/**
 * MapIterator class iterates on map object of HashMap
 * 
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 * @param <K>
 * @param <V>
 */
public class MapIterator<K, V> {
	
	/**
	 * Map object to iterate on
	 */
	HashMap<K, V> map;
	
	/**
	 * The iterator inner list
	 */
	MyList it;
	
	/**
	 * The starting key of iterator
	 */
	K startingKey;

	/**
	 * Adds entry to the map
	 *
	 * @param key
	 * @param value
	 */
	public void add(K key, V value) {
		map.put(key, value);
		it.add(key, value);
	}

	/**
	 * One parameter constructor
	 *
	 * @param key
	 */
	public MapIterator(K key) {
		map = new HashMap<K, V>();
		startingKey = key;
		it = new MyList();
	}

	/**
	 * No parameter constructor
	 *
	 */
	public MapIterator() {
		startingKey = null;
		map = new HashMap<K, V>();
		it = new MyList();
	}

	/**
	 * Returns prev node
	 *
	 * @return EntryNode
	 */
	public EntryNode prev() {
		if (hasPrevious() == true) {
			return it.prev();
		}
		return it.prev();
	}

	/**
	 * Returns next node
	 *
	 * @return EntryNode
	 */
	public EntryNode next() {
		if (hasNext() != false) {
			return it.next();
		}
		return it.next();
	}

	/**
	 * Returns true if the iterator has next node
	 *
	 * @return boolean
	 */
	public boolean hasNext() {
		if (it.hasNext()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the iterator has prev node
	 *
	 * @return boolean
	 */
	public boolean hasPrevious() {
		if (it.hasPrevious()) {
			return true;
		}
		return false;
	}
}
