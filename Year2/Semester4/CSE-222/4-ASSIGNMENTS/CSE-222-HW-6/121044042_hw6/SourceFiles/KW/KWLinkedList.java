package KW;

import java.util.*;

/**
 * Class KWLinkedList implements a double linked list and a ListIterator.
 *
 * @author Koffman & Wolfgang
 */
public class KWLinkedList< E> {

	/**
	 * A reference to the head of the list.
	 */
	private Node< E> head = null;

	/**
	 * A reference to the end of the list.
	 */
	private Node< E> tail = null;

	/**
	 * The size of the list.
	 */
	private int size = 0;

	/**
	 * Insert an object at the beginning of the list.
	 *
	 * @param item - the item to be added
	 */
	public void addFirst(E item) {
		add(0, item);
	}

	/**
	 * Insert an object at the end of the list.
	 *
	 * @param item - the item to be added
	 */
	public void addLast(E item) {
		add(size, item);
	}

	/**
	 * Get the first element in the list.
	 *
	 * @return The first element in the list.
	 */
	public E getFirst() {
		return head.data;
	}

	/**
	 * Get the last element in the list.
	 *
	 * @return The last element in the list.
	 */
	public E getLast() {
		return tail.data;
	}

	/**
	 * Remove the object at the beginning of the list.
	 */
	public void removeFirst() {
		remove(0);
	}

	/**
	 * Remove the object at the end of the list.
	 */
	public void removeLast() {
		remove(size - 1);
	}

	public int size() {
		return size;
	}

	/**
	 * Print the list.
	 */
	public void print() {
		Iterator<E> iter = listIterator(0);
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
		}
	}

	/**
	 * Return an Iterator to the list
	 *
	 * @return an Iterator tot the list
	 */
	public Iterator< E> iterator() {
		return new KWListIter(0);
	}

	/**
	 * Return a ListIterator to the list
	 *
	 * @return a ListItertor to the list
	 */
	public ListIterator< E> listIterator() {
		return new KWListIter(0);
	}

	/**
	 * Return a ListIterator that begins at index
	 *
	 * @param index - The position the iteration is to begin
	 * @return a ListIterator that begins at index
	 */
	public ListIterator< E> listIterator(int index) {
		return new KWListIter(index);
	}

	/**
	 * Add an item at the specified index.
	 *
	 * @param index The index at which the object is to be inserted
	 * @param obj The object to be inserted
	 * @throws IndexOutOfBoundsException if the index is out of range 0-size
	 */
	public void add(int index, E obj) {
		listIterator(index).add(obj);
	}

	/**
	 * Get the element at position index.
	 *
	 * @param index Position of item to be retrieved
	 * @return The item at index
	 */
	public E get(int index) {
		return listIterator(index).next();
	}

	/**
	 * Remove the object at the end of the list.
	 *
	 * @param index
	 */
	public void remove(int index) {
		Iterator<E> iter = listIterator(index);
		iter.next();
		iter.remove();
	}
	
	public int indexOf (E item) {
		int index = -1;
		if (head == null) {
			index = -1;
		} else {
			Node<E> ref = head;
			for (int i = 0; ref != null; i++) {
				if (ref.data.equals(item)) {
					index = i;
					break;
				}
				ref= ref.next;
			}
		}
		return index;
	}
	
	/**
	 * A Node is the building block for a double-linked list.
	 */
	private static class Node< E> {

		/**
		 * The data value.
		 */
		private E data;

		/**
		 * The link to the next node.
		 */
		private Node< E> next = null;

		/**
		 * The link to the previous node.
		 */
		private Node< E> prev = null;

		/**
		 * Construct a node with the given data value.
		 *
		 * @param dataItem The data value
		 */
		private Node(E dataItem) {
			data = dataItem;
		}
	}

	/**
	 * Inner class to implement the ListIterator interface.
	 */
	private class KWListIter implements ListIterator< E> {

		/**
		 * A reference to the next item.
		 */
		private Node< E> nextItem;

		/**
		 * A reference to the last item returned.
		 */
		private Node< E> lastItemReturned;

		/**
		 * The index of the current item.
		 */
		private int index = 0;

		/**
		 * Construct a KWListIter that will reference the ith item.
		 *
		 * @param i The index of the item to be referenced
		 */
		public KWListIter(int i) {
			if (i < 0 || i > size) {
				throw new IndexOutOfBoundsException("Invalid index " + i);
			}
			lastItemReturned = null;
			if (i == size) {
				index = size;
				nextItem = null;
			} else {
				nextItem = head;
				for (index = 0; index < i; index++) {
					nextItem = nextItem.next;
				}
			}
		}

		/**
		 * Indicate whether movement forward is defined.
		 *
		 * @return true if call to next will not throw an exception
		 */
		public boolean hasNext() {
			return nextItem != null;
		}

		/**
		 * Move the iterator forward and return the next item.
		 *
		 * @return The next item in the list
		 * @throws NoSuchElementException if there is no such object
		 */
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastItemReturned = nextItem;
			nextItem = nextItem.next;
			index++;
			return lastItemReturned.data;
		}

		/**
		 * Indicate whether movement backward is defined.
		 *
		 * @return true if call to previous will not throw an exception
		 */
		public boolean hasPrevious() {
			return (nextItem == null && size != 0) || nextItem.prev != null;
		}

		/**
		 * Return the index of the next item to be returned by next
		 *
		 * @return the index of the next item to be returned by next
		 */
		public int nextIndex() {
			return index;
		}

		/**
		 * Return the index of the next item to be returned by previous
		 *
		 * @return the index of the next item to be returned by previous
		 */
		public int previousIndex() {
			return index - 1;
		}

		/**
		 * Move the iterator backward and return the previous item.
		 *
		 * @return The previous item in the list
		 * @throws NoSuchElementException if there is no such object
		 */
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextItem == null) { // Iterator past the last element
				nextItem = tail;
			} else {
				nextItem = nextItem.prev;
			}
			lastItemReturned = nextItem;
			index--;
			return lastItemReturned.data;
		}

		/**
		 * Add a new item between the item that will be returned by next and the
		 * item that will be returned by previous. If previous is called after
		 * add, the element added is returned.
		 *
		 * @param obj The item to be inserted
		 */
		public void add(E obj) {
			if (head == null) {
				head = new Node< E>(obj);
				tail = head;
			} else if (nextItem == head) {
				Node< E> newNode = new Node< E>(obj);
				newNode.next = nextItem;
				nextItem.prev = newNode;
				head = newNode;
			} else if (nextItem == null) {
				Node< E> newNode = new Node< E>(obj);
				tail.next = newNode;
				newNode.prev = tail;
				tail = newNode;
			} else {
				Node< E> newNode = new Node< E>(obj);
				newNode.prev = nextItem.prev;
				nextItem.prev.next = newNode;
				newNode.next = nextItem;
				nextItem.prev = newNode;
			}
			size++;
			index++;
			lastItemReturned = null;
		}

		/**
		 * Remove the last item returned. This can only be done once per call to
		 * next or previous.
		 *
		 * @throws IllegalStateException if next or previous was not called
		 * prior to calling this method
		 */
		public void remove() {
			if (lastItemReturned == null) {
				throw new IllegalStateException();
			}
			if (lastItemReturned.next != null) {
				lastItemReturned.next.prev = lastItemReturned.prev;
			} else {
				tail = lastItemReturned.prev;
				if (tail != null) {
					tail.next = null;
				} else {
					head = null;
				}
			}
			if (lastItemReturned.prev != null) {
				lastItemReturned.prev.next = lastItemReturned.next;
			} else {
				head = lastItemReturned.next;
				if (head != null) {
					head.prev = null;
				} else {
					tail = null;
				}
			}
			lastItemReturned = null;
			size--;
			index--;
		}

		/**
		 * Replace the last item returned with a new value
		 *
		 * @param item The new value
		 * @throws IllegalStateException if next or previous was not called
		 * prior to calling this method
		 */
		public void set(E item) {
			if (lastItemReturned == null) {
				throw new IllegalStateException();
			}
			lastItemReturned.data = item;
		}
	}
}
