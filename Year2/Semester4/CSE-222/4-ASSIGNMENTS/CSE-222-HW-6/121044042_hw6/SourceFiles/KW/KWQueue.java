package KW;

import java.util.*;

/**
 *
 * @author seyda
 * @param <E>
 */
public class KWQueue< E> implements Queue< E> {
	
	/**
	 * 
	 */
    private LinkedList< E> theQueue = new LinkedList< E>();
	
	/**
	 * 
	 * @param item
	 * @return 
	 */
    @Override
    public boolean offer(E item) {
        theQueue.addLast(item);
        return true;
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public E poll() {
        if (size() == 0) {
            return null;
        } else {
            return theQueue.remove(0);
        }
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public E peek() {
        if (size() == 0) {
            return null;
        } else {
            return theQueue.getFirst();
        }
    }
	
	/**
	 * 
	 * @param item
	 * @return 
	 */
    @Override
    public boolean add(E item) {
        return offer(item);
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return peek();
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public int size() {
        return theQueue.size();
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public boolean isEmpty() {
        return theQueue.size() == 0;
    }
	
	/**
	 * 
	 * @param c
	 * @return 
	 */
    @Override
    public boolean retainAll(Collection c) {
        return false;
    }
	
	/**
	 * 
	 * @param c
	 * @return 
	 */
    @Override
    public boolean removeAll(Collection c) {
        return false;
    }
	
	/**
	 * 
	 * @param c
	 * @return 
	 */
    @Override
    public boolean addAll(Collection c) {
        return false;
    }
	
	/**
	 * 
	 * @param obj
	 * @return 
	 */
    @Override
    public boolean contains(Object obj) {
        return false;
    }
	
	/**
	 * 
	 * @param c
	 * @return 
	 */
    @Override
    public boolean containsAll(Collection c) {
        return false;
    }
	
	/**
	 * 
	 * @param obj
	 * @return 
	 */
    @Override
    public boolean remove(Object obj) {
        return false;
    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public Object[] toArray() {
        return null;
    }
	
	/**
	 * 
	 * @param a
	 * @return 
	 */
    @Override
    public Object[] toArray(Object[] a) {
        return null;
    }
	
	/**
	 * 
	 */
    @Override
    public void clear() {

    }
	
	/**
	 * 
	 * @return 
	 */
    @Override
    public Iterator< E> iterator() {
        return null;
    }

}
