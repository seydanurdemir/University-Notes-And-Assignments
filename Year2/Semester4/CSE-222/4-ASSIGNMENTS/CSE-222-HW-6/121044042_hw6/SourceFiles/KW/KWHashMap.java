package KW;

/**
 * KWHashMap Interface
 * 
 * @author Seyda Nur DEMIR
 * @param <K>
 * @param <V>
 */
public interface KWHashMap<K, V> {

	/**
	 * Retrieves value
	 * 
	 * @param key
	 * @return value
	 */
	V get(Object key);

	/**
	 * Puts an entry to map
	 * 
	 * @param key
	 * @param value
	 * @return added value
	 */
	V put(K key, V value);

	/**
	 * Removes an entry from map
	 * 
	 * @param key
	 * @return V
	 */
	V remove(Object key);

	/**
	 * Returns number of keys at the map
	 * 
	 * @return int
	 */
	int size();

	/**
	 * Returns the map is empty or not
	 * 
	 * @return boolean
	 */
	boolean isEmpty();
}
