package KW;

import java.util.*;

/**
 * Class KWArrayList implements some of the methods of the Java ArrayList class.
 *
 * @author Koffman & Wolfgang
 */
public class KWArrayList< E> {

	/**
	 * The default initial capacity
	 */
	private static final int INITIAL_CAPACITY = 10;

	/**
	 * The underlying data array
	 */
	private E[] theData;

	/**
	 * The current size
	 */
	private int size = 0;

	/**
	 * The current capacity
	 */
	private int capacity = 0;

	/**
	 * Construct an empty KWArrayList with the default initial capacity
	 */
	public KWArrayList() {
		capacity = INITIAL_CAPACITY;
		theData = (E[]) new Object[capacity];
	}

	/**
	 * Adds an entry
	 *
	 * @param anEntry
	 * @return boolean
	 */
	public boolean add(E anEntry) {
		if (size == capacity) {
			reallocate();
		}
		theData[size] = anEntry;
		size++;
		return true;
	}
        
        /**
	 * Removes an entry
	 *
	 * @param anEntry
	 * @return boolean
	 */
	public boolean remove(E anEntry) {
            int ind = indexOf(anEntry);
            if(ind == -1)
                return false;
            else {
                if(remove(ind) == null)
                    return false;
                else
                    return true;
            }
	}
        
	/**
	 * Get a value in the array based on its index.
	 *
	 * @param index - The index of the item desired
	 * @return The contents of the array at that index
	 * @throws ArrayIndexOutOfBoundsException - if the index is negative or if
	 * it is greater than or equal to the current size
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return theData[index];
	}

	/**
	 * Set the value in the array based on its index.
	 *
	 * @param index - The index of the item desired
	 * @param newValue - The new value to store at this position
	 * @return The old value at this position
	 * @throws ArrayIndexOutOfBoundsException - if the index is negative or if
	 * it is greater than or equal to the current size
	 */
	public E set(int index, E newValue) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		E oldValue = theData[index];
		theData[index] = newValue;
		return oldValue;
	}

	/**
	 * Remove an entry based on its index
	 *
	 * @param index - The index of the entry to be removed
	 * @return The value removed
	 * @throws ArrayIndexOutOfBoundsException - if the index is negative or if
	 * it is greater than or equal to the current size
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		E returnValue = theData[index];
		for (int i = index + 1; i < size; i++) {
			theData[i - 1] = theData[i];
		}
		size--;
		return returnValue;
	}

	/**
	 * Allocate a new array to hold the directory
	 */
	private void reallocate() {
		capacity = 2 * capacity;
		E[] newData = (E[]) new Object[capacity];
		System.arraycopy(theData, 0, newData, 0, size);
		theData = newData;
	}

	/**
	 * Get the current size of the array
	 *
	 * @return The current size of the array
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns index of an element
	 *
	 * @param item
	 * @return
	 */
	public int indexOf(E item) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (theData[i].equals(item)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Returns true if list contains item
	 *
	 * @param item
	 * @return
	 */
	public boolean contains(E item) {
		return (indexOf(item) != -1);
	}

	/**
	 * Returns true if list is empty
	 *
	 * @return
	 */
	public boolean empty() {
		return (size == 0);
	}

	/**
	 * Returns true if list is full
	 *
	 * @return
	 */
	public boolean full() {
		return (size == INITIAL_CAPACITY);
	}
	
	/**
	 * Prints list
	 */
	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.println(theData[i].toString());
		}
	}
	
}
