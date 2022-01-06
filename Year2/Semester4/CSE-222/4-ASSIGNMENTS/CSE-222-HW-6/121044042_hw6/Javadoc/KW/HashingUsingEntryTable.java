package KW;

import java.util.Objects;

/**
 * Public Generic Class HashingUsingEntryTable Implements KWHashMap Interface
 * This class hashes key-value pairs. This class uses open-addressing technique
 * to hashing pairs. This class uses simple arrays to store the table.
 *
 * @author Seyda Nur DEMIR
 * @param <K> key
 * @param <V> value
 */
public class HashingUsingEntryTable<K extends Comparable<K>, V extends Comparable<V>> implements KWHashMap<K, V> {

	/**
	 * The table
	 */
	private Entry<K, V>[] map;

	/**
	 * The number of keys
	 */
	private int numKeys = 0;

	/**
	 * The number of deleted keys
	 */
	private int numDeletes = 0;

	/**
	 * The deleted entry
	 */
	private final Entry<K, V> DELETED = new Entry<K, V>(null, null);

	/**
	 * The capacity
	 */
	private static final int CAPACITY = 10;

	/**
	 * The maximum load factor
	 */
	private static final double LOAD_THRESHOLD = 0.75;

	/**
	 * No parameter constructor
	 */
	public HashingUsingEntryTable() {
		this.map = new Entry[CAPACITY];
	}

	/**
	 * Gets key
	 *
	 * @param key
	 * @return V
	 */
	@Override
	public V get(Object key) {
		int index = find(key);
		if (map[index] != null) {
			return map[index].getValue();
		}
		return null;
	}

	/**
	 * Puts entry to table
	 *
	 * @param key
	 * @param value
	 * @return V
	 */
	@Override
	public V put(K key, V value) {
		int index = find(key);
		if (map[index] == null) {
			map[index] = new Entry<K, V>(key, value);
			numKeys++;
			double loadFactor = (double) (numKeys + numDeletes) / (map.length);
			if (loadFactor > LOAD_THRESHOLD) {
				rehash();
			}
			return null;
		}

		V val = map[index].getValue();
		map[index].setValue(value);

		return val;
	}

	/**
	 * Removes entry
	 *
	 * @param key
	 * @return V
	 */
	@Override
	public V remove(Object key) {
		int index = find(key);
		if (map[index] != null) {
			numKeys--;
			numDeletes++;
			V val = map[index].getValue();
			map[index] = DELETED;
			return val;
		} else {
			return null;
		}
	}
	
	/**
	 * Updates value
	 *
	 * @param key
	 * @return V
	 */
	public V update(Object key, V value) {
		int index = find(key);
		if (map[index] != null) {
			map[index].setValue(value);
			return value;
		}
		return null;
	}
	
	/**
	 * Returns the number of entries in the map
	 *
	 * @return int
	 */
	@Override
	public int size() {
		return numKeys;
	}

	/**
	 * Returns true if empty
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		return numKeys == 0;
	}

	/**
	 * Hashing %10
	 *
	 * @param key
	 * @return int
	 */
	private int hash(Object key) {
		int index = (key.hashCode() % 10) % map.length;
		if (index < 0) {
			index += map.length;
		}
		return index;
	}

	/**
	 * Finds hash value of the key
	 *
	 * @param key
	 * @return int
	 */
	public int find(Object key) {
		int i = 0;
		int index = (hash(key)) % map.length;
		while (map[index] != null && !(key.equals(map[index].getKey())) && i < map.length) {
			index = (hash(key)) % map.length;
			//index++;
			i++;
		}
		return index;
	}

	/**
	 * Expands table size when loadFactor exceeds LOAD_THRESHOLD
	 */
	private void rehash() {
		Entry<K, V>[] oldMap = map;
		map = new Entry[oldMap.length * 2 + 1];
		numKeys = 0;
		for (int i = 0; i < oldMap.length; ++i) {
			if (oldMap[i] != null && oldMap[i] != DELETED) {
				put(oldMap[i].getKey(), oldMap[i].getValue());
			}
		}
	}
}
