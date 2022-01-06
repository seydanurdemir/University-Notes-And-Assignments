import java.util.Objects;
import java.util.TreeSet;

/**
 * Public Generic Class HashingUsingTreeSet Implements KWHashMap Interface This
 * class hashes key-value pairs. This class uses chaining technique to hashing
 * pairs. This class uses tree set to store the table.
 *
 * @author Seyda Nur DEMIR
 * @param <K>
 * @param <V>
 */
public class HashingUsingTreeSet<K extends Comparable<K>, V extends Comparable<V>> implements KWHashMap<K, V> {

	/**
	 * The table
	 */
	private TreeSet< Entry< K, V>>[] table;

	/**
	 * The number of keys
	 */
	private int numKeys = 0;

	/**
	 * The capacity
	 */
	private static final int CAPACITY = 10;

	/**
	 * The maximum load factor
	 */
	private static final double LOAD_THRESHOLD = 3.0;

	/**
	 * Contains key-value pairs for a hash table.
	 */
	private static class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable< Entry< K, V>> {

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
	public HashingUsingTreeSet() {
		this.table = new TreeSet[CAPACITY];
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
		if (table[index] == null) {
			return null;
		}
		for (Entry<K, V> item : table[index]) {
			if (item.getKey().equals(key)) {
				return item.getValue();
			}
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
		if (table[index] == null) {
			table[index] = new TreeSet<Entry<K, V>>();
		} else {
			for (Entry<K, V> entry : table[index]) {
				if (entry.getKey().equals(key)) {
					V val = entry.getValue();
					entry.setValue(value);
					return val;
				}
			}
		}
		table[index].add(new Entry<K, V>(key, value));
		numKeys++;
		if (numKeys > LOAD_THRESHOLD * table.length) {
			rehash();
		}
		return null;
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
		if (table[index] == null) {
			return null;
		}
		for (Entry<K, V> entry : table[index]) {
			if (entry.getKey().equals(key)) {
				numKeys--;
				V value = entry.getValue();
				table[index].remove(entry);
				if (table[index].isEmpty()) {
					table[index] = null;
				}
				return value;
			}
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
	 * Returns true if the map is empty
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		return numKeys == 0;
	}

	/**
	 * Gets hash value of key
	 *
	 * @param key
	 * @return int
	 */
	private int find(Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0) {
			index += table.length;
		}
		return index;
	}

	/**
	 * Expands table size when loadFactor exceeds LOAD_THRESHOLD
	 *
	 */
	private void rehash() {
		TreeSet< Entry< K, V>>[] oldTable = table;
		numKeys = 0;
		table = new TreeSet[oldTable.length * 2 + 1];
		for (TreeSet< Entry< K, V>> tree : oldTable) {
			for (Entry<K, V> entry : tree) {
				put(entry.getKey(), entry.getValue());
			}
		}
	}
}
