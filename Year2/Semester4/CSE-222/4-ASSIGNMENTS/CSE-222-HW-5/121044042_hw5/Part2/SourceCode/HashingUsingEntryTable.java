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
	 * Contains key-value pairs for a hash table.
	 */
	private static class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K, V>> {

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
			//index = (hash(key)) % map.length;
			index++;
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
