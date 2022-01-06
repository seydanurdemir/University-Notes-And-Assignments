import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A Skip List is an alternative to a binary tree that provides for
 * approximately logarithmic searching, insertion, and deletion. Skip Lists were
 * developed by William Pugh and first described in "Skip Lists: A Probabilistic
 * Alternative to Balanced Trees", CACM 13:8 (June 1990) pp 668-676
 *
 * @param <E> The element type
 * @author Koffman and Wolfgang
 */
@SuppressWarnings("unchecked")
public class SkipList<E extends Comparable<E>> implements SearchTree<E> {

    /**
     * Static class to contain the data and the links
     *
     * @param <E> The element type
     */
    protected static class SLNode<E> {

        public SLNode<E>[] links;
        public E data;

        /**
         * Create a node of level n
         *
         * @param n The level of this node
         * @param data The data to be stored in this node
         */
        @SuppressWarnings("unchecked")
        public SLNode(int n, E data) {
            links = (SLNode<E>[]) new SLNode[n];
            this.data = data;
        }
    }
    /**
     * Maximum level
     */
    int maxLevel = 2;
    /**
     * Nominal maximum capacity
     */
    int maxCap = computeMaxCap(maxLevel);
    /**
     * Natural Log of 2
     */
    static final double LOG2 = Math.log(2.0);
    /**
     * A random number generator
     */
    final static Random rand = new Random();
    /**
     * The current size of the skipList
     */
    int size;

    /**
     * Method to compute the maximum capacity, given the maximum level. It
     * computes Math.pow(2, maxLevel) - 1, using shift.
     *
     * @return Math.pow(2, maxLevel+1) - 1;
     */
    private static int computeMaxCap(int maxLevel) {
        return ~(~0 << maxLevel);
    }

    /**
     * Method to generate a logarithmic distributed integer between 1 and
     * maxLevel. I.E. 1/2 values returned are 1, 1/4 are 2, 1/8 are 3, 1/16 are
     * 4, etc.
     *
     * @return a random logarithmic distributed integer between 1 and maxLevel
     */
    private int logRandom() {
        int r = rand.nextInt(maxCap);
        int k = (int) (Math.log(r + 1) / LOG2);
        if (k > maxLevel - 1) {
            k = maxLevel - 1;
        }
        return maxLevel - k;
    }
    /**
     * The head node is a dummy node, it contains no data
     */
    protected SLNode<E> head = new SLNode<>(maxLevel, null);

    /**
     * Search for an item in the list
     *
     * @param item The item being sought
     * @return A SLNode array which references the nodes preceeding the sought
     * item at each level.
     */
    private SLNode<E>[] search(E item) {
        SLNode<E>[] result = (SLNode<E>[]) new SLNode[maxLevel];
        SLNode<E> current = head;
        for (int i = current.links.length - 1; i >= 0; i--) {
            while (current.links[i] != null
                    && current.links[i].data.compareTo(item) < 0) {
                current = current.links[i];
            }
            result[i] = current;
        }
        return result;
    }

    /**
     * Find an object in the skip list
     *
     * @param target The item being sought
     * @return A reference to the object in the skip list that compares equal as
     * determined by compareTo to the target. If not found null is returned.
     */
    @Override
    public E find(E target) {
        SLNode<E>[] update = search(target);
        if (update[0].links[0] != null
                && update[0].links[0].data.compareTo(target) == 0) {
            return update[0].links[0].data;
        } else {
            return null;
        }
    }

    /**
     * Removes the specified element from this list if it is present.
     *
     * @param target object to be removed from this set, if present.
     * @return target if this list contained the specified element otherwise
     * null.
     */
    @Override
    public E delete(E target) {
        if (remove(target)) {
            return target;
        }
        return null;
    }

    /**
     * Removes the specified element from this list if it is present.
     *
     * @param target object to be removed from this set, if present.
     * @return true if this list contained the specified element.
     */
    @Override
    public boolean remove(E target) {
        //search for element to remove
        SLNode<E>[] pred = (SLNode<E>[]) new SLNode[maxLevel];
        SLNode<E> current = head;
        for (int i = current.links.length - 1; i >= 0; i--) {
            while (current.links[i] != null
                    && current.links[i].data.compareTo(target) < 0) {
                current = current.links[i];
            }
            pred[i] = current;
        }
        current = current.links[0];
        //if it is in the list remove it
        if (current != null && current.data.compareTo(target) == 0) {
            for (int i = 0; i < maxLevel; i++) {
                if (pred[i].links[i] != current) {
                    break;
                }
                pred[i].links[i] = current.links[i];
            }
            //if size less than 2^(maxLevel-1)
            //decrement the maxLevel
            size--;//decrement the size
            if (size < computeMinLevel(maxLevel)) {
                maxLevel--;
                maxCap = computeMaxCap(maxLevel);
                head.links = Arrays.copyOf(head.links, maxLevel);
                pred = Arrays.copyOf(pred, maxLevel);
                pred[maxLevel - 1] = head;
            }
            return true;
        }
        return false;//this list doesn't contain the specified element.
    }

    /**
     * Returns 2^(maxLevel-1)
     *
     * @param level max level of the list.
     * @return 2^(maxLevel-1)
     */
    private int computeMinLevel(int level) {
        return (int) (Math.pow(2, maxLevel - 1));
    }

    /**
     * Inserts item where it belongs in the skip list.
     *
     * @param item The item to be inserted
     * @return true If the item is inserted, false if the item was already in
     * the tree.
     */
    @Override
    public boolean add(E item) {
        SLNode<E>[] update = search(item);
        if (update[0].links[0] != null
                && update[0].links[0].data.compareTo(item) == 0) {
            return false; // Item already in Skip List
        }
        // Increment size and adjust maxLevel
        size++;
        if (size > maxCap) {
            maxLevel++;
            maxCap = computeMaxCap(maxLevel);
            head.links = Arrays.copyOf(head.links, maxLevel);
            update = Arrays.copyOf(update, maxLevel);
            update[maxLevel - 1] = head;
        }
        // Create new node for item
        SLNode<E> newNode = new SLNode<>(logRandom(), item);
        // Splice new node into list
        for (int i = 0; i < newNode.links.length; i++) {
            newNode.links[i] = update[i].links[i];
            update[i].links[i] = newNode;
        }
        return true;
    }

    /**
     * Determine if an item is in the tree
     *
     * @param target Item being sought in tree
     * @return true If the item is in the tree, false otherwise
     */
    @Override
    public boolean contains(E target) {
        return find(target) != null;
    }

    /**
     * Remove all data from the tree
     */
    public void clear() {
        for (int i = 0; i < maxLevel; i++) {
            head.links[i] = null;
        }
        size = 0;
    }

    /**
     * Return a list containing the contents of the search tree in ascending
     * order.
     *
     * @return a list containing the contents of the search tree in ascending
     * order.
     */
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        SLNode<E> current = head.links[0];
        while (current != null) {
            list.add(current.data);
            current = current.links[0];
        }
        return list;
    }

    /**
     * Returns max level of list.
     *
     * @return max level of list.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Returns max capacity of list.
     *
     * @return max capacity of list.
     */
    public int getMaxCap() {
        return maxCap;
    }

    /**
     * Returns size of list.
     *
     * @return size of list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns String representation of skip list
     *
     * @return String representation of skip list
     */
    @Override
    public String toString() {
        StringBuilder sList = new StringBuilder();
        for (int i = 0; i < maxLevel; i++) {
            SLNode<E> list = head.links[i];
            sList.append("Level " + i + ":");
            while (list != null) {
                if (list.data != null) {
                    sList.append(list.data + ", ");
                }
                list = list.links[i];
            }
            sList.append("\n");
        }
        return sList.toString();
    }
}
/*</listing>*/
