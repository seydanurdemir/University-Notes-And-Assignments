/**
 * EntryNode keeps MyList class nodes
 *
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 * @param <K> key
 * @param <V> value
 */
public class EntryNode<K, V> {

	/**
	 * The key
	 */
	public K key;

	/**
	 * The value
	 */
	public V value;

	/**
	 * The next node
	 */
	public EntryNode next;

	/**
	 * The previous node
	 */
	public EntryNode prev;
}
