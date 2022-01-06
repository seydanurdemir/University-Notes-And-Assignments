package KW;

import java.util.*;

/**
 * Contains key-value pairs for a hash table.
 */
public class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K, V>> {

	/**
	 * The key
	 */
	private K key;

	/**
	 * The value
	 */
	private V value;

	/**
	 * Creates a new key-value pair.
	 *
	 * @param key The key
	 * @param value The value
	 */
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Retrieves the key.
	 *
	 * @return The key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Retrieves the value.
	 *
	 * @return The value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param val The new value
	 * @return The old value
	 */
	public V setValue(V val) {
		V oldVal = value;
		value = val;
		return oldVal;
	}

	/**
	 * Compares two entries
	 *
	 * @param other
	 * @return int
	 */
	@Override
	public int compareTo(Entry<K, V> other) {
		return this.key.compareTo(other.key);
	}

	/**
	 * Calculates hash code of entry
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.key);
		hash = 97 * hash + Objects.hashCode(this.value);
		return hash;
	}

	/**
	 * Returns true if two entries are equal
	 *
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() == this.getClass()) {
			return ((Entry<K, V>) obj).getKey().equals(key);
		} else {
			return false;
		}
	}

	/**
	 * Returns string of entry
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return key.toString() + ":" + value.toString();
	}
}
