/**
 * MyList class keeps entries as linked list to iterate on
 * 
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 * @param <K>
 * @param <V>
 */
public class MyList<K, V> {
	
	/**
	 * The head node
	 */
	private EntryNode head;
	
	/**
	 * The tail node
	 */
	private EntryNode tail;
	
	/**
	 * The pointer node to iterate
	 */
	private EntryNode Pointer;

	/**
	 * No parameter constructor
	 *
	 */
	public MyList() {
		head = null;
		tail = null;
		Pointer = null;
	}

	/**
	 * Adds entry to the list
	 *
	 * @param key
	 * @param value
	 */
	public void add(K key, V value) {
		EntryNode N = new EntryNode();
		N.prev = null;
		N.next = null;
		N.value = value;
		N.key = key;

		if (head == null) {
			Pointer = N;
			head = N;
			tail = N;
		} else {
			tail.next = N;
			N.prev = tail;
			tail = N;
		}
	}

	/**
	 * Returns true if the pointer is not at the tail
	 *
	 * @return boolean
	 */
	public boolean hasNext() {
		if (Pointer == null) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the pointer is not at the head
	 * 
	 * @return boolean
	 */
	public boolean hasPrevious() {
		if (Pointer == null) {
			return false;
		}
		return true;
	}

	/**
	 * Returns previous item of the pointer node
	 *
	 * @return EntryNode
	 */
	public EntryNode prev() {
		if (hasPrevious() == true) {
			EntryNode temp = Pointer;
			Pointer = Pointer.prev;
			return temp;
		}
		return tail;
	}

	/**
	 * Returns next item of the pointer node
	 *
	 * @return EntryNode
	 */
	public EntryNode next() {
		if (hasNext() == true) {
			EntryNode temp = Pointer;
			Pointer = Pointer.next;
			return temp;
		}
		return head;
	}
}
