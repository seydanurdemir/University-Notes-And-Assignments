import java.util.*;

/**
 * HybridList Class
 *
 * @author Seyda Nur DEMIR
 */
class HybridList<E> {
	private KWLinkedList <KWArrayList <E> > data = new KWLinkedList <KWArrayList <E> > ();
	private static final int MAX_NUMBER = 10;
	private int size = 0;
	
	public void add (E item) {
		if (data.getLast().full()) {
			KWArrayList<E> newArray = new KWArrayList<E>();
			data.addLast(newArray);
		}
		data.getLast().add(item);
		size++;
	}
	
	public void remove (E item) {
		int k = 0;
		for (int i = 0; i < size(); i++) {
			k = data.get(i).indexOf(item);
			if (k != -1) {
				data.get(i).remove(k);
				break;
			}
		}
		if (data.getLast().empty()) {
			data.removeLast();
		}
		format();
		size--;
	}
	
	public void format () {
		/*int i = 0, j = 0, k = 0, cap = (data.size()*MAX_NUMBER);
		if (size() != 0) {
			E element = data.getFirst().get(0);
			
			for (i = 0; i < size; i++) {
				if (data.get(j).full()) {
					i += MAX_NUMBER;
					j++;
					k = 0;
				}
				if (k == (MAX_NUMBER - 1) {
					data.get(j).set(k, data.get(j+1).get(0));
					k++; //here
				}
			}
		}*/
	}
	
	public void indexOf (E item) {
		int index = -1; int k = 0;
		for (int i = 0; i < size(); i++) {
			k = data.get(i).indexOf(item);
			if (k != -1) {
				data.get(i).remove(k);
				break;
			}
		}
	}
	
	public int size(){
		return size;
	}
	
}
