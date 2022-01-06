
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * MaxHeap Class : heap data structure methods is in the max heap class.
 * 
 * @author Seyda Nur DEMIR
 * @version 1.0
 * @param <E>
 * @since 2021-05-05
 */
public class MaxHeap<E extends Comparable<E>> implements Comparable<E> {

	private KWPriorityMaxQueue<MaxHeapData<E>> data;
	private int size = 0;

	/**
	 *
	 */
	public final static int MAXDEPTH = 2;

	/**
	 *
	 */
	public final static int CAPACITY = (int) Math.pow(2, (MAXDEPTH + 1));

	/**
	 *
	 * @param item
	 * @return
	 */
	public boolean add(E item) {
		MaxHeapData<E> temp = new MaxHeapData<E>(item);
		size++;
		return data.add(temp);
	}

	/**
	 *
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public boolean addOnly(E item) throws Exception {
		if (size == CAPACITY) {
			throw new Exception("Heap is full, item could not added.");
		}
		MaxHeapData<E> temp = search(item);
		if (temp == null) {
			temp = new MaxHeapData<E>(item);
			size++;
			data.add(temp);
		} else {
			temp.increaseOccurrence();
		}
		return true;
	}

	/**
	 *
	 * @return
	 */
	public E removeLargest() {
		E temp = data.peek().getItem();
		data.poll();
		return temp;
	}

	/**
	 *
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	public MaxHeapData<E> search(E item) {
		Iterator<MaxHeapData<E>> iter = data.iterator();
		while (iter.hasNext()) {
			MaxHeapData<E> temp = iter.next();
			if (temp.getItem().equals(item)) {
				return temp;
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 */
	public Iterator<MaxHeapData<E>> iterator() {
		return data.iterator();
	}

	/**
	 *
	 * @return
	 */
	public MaxHeapIter maxHeapIter() {
		return new MaxHeapIter();
	}

	/**
	 *
	 * @param ith
	 * @return
	 */
	public MaxHeapIter maxHeapIter(int ith) {
		return new MaxHeapIter(ith);
	}

	@Override
	public int compareTo(E o) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private class MaxHeapData<E extends Comparable<E>> implements Comparable<E> {

		private E item;
		private int occurrence;

		public MaxHeapData() {
			this.item = null;
			this.occurrence = 0;
		}

		public MaxHeapData(E item) {
			this.item = item;
			this.occurrence = 1;
		}

		public MaxHeapData(E item, int occurrence) {
			this.item = item;
			this.occurrence = occurrence;
		}

		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

		public int getOccurrence() {
			return occurrence;
		}

		public void setOccurrence(int occurrence) {
			this.occurrence = occurrence;
		}

		public void increaseOccurrence() {
			occurrence++;
		}

		public void decreaseOccurrence() throws Exception {
			if (occurrence != 0) {
				occurrence--;
			} else {
				throw new Exception("Could not decreased");
			}
		}

		@Override
		public int compareTo(E item) {
			return item.compareTo(this.item);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (obj.getClass() == this.getClass()) {
				MaxHeapData<E> other = (MaxHeapData<E>) obj;
				return ((this.item.equals(other.item)) && (this.occurrence == other.occurrence));
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			int hash = 2;
			hash = 11 * hash + Objects.hashCode(this.item);
			hash = 11 * hash + this.occurrence;
			return hash;
		}

		@Override
		public String toString() {
			return "(" + item.toString() + "," + occurrence + ")";
		}
	}

	private class MaxHeapIter implements Iterator<E> {

		private Iterator< MaxHeapData< E>> iter = data.iterator();
		private E lastItemReturned = null;
		private int index = 0;

		public MaxHeapIter() {
		}

		public MaxHeapIter(int ith) {
			if (ith < 0 || ith > size) {
				throw new IndexOutOfBoundsException("Invalid index " + ith);
			}
			if (ith == size) {
				index = size;
			} else {
				for (int i = 0; i < ith; i++) {
					iter.next();
					index++;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public E next() {
			if (hasNext()) {
				lastItemReturned = iter.next().getItem();
				index++;
				return lastItemReturned;
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			if (!data.isEmpty()) {
				iter.remove();
			}
			throw new NoSuchElementException();
		}

		public void set(E item) {
			Iterator< MaxHeapData< E>> tempIter = data.iterator();
			for (int i = 0; i < (index - 1); i++) {
				tempIter.next();
			}
			tempIter.next().setItem(item);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> action) {
			Iterator.super.forEachRemaining(action); //To change body of generated methods, choose Tools | Templates.
		}

	}
}
