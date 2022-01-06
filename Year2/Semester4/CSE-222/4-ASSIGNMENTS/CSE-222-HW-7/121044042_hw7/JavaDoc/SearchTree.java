public interface SearchTree<E> {

    /**
     *
     * Insens item where it belongs in the tree. Returns true if item is
     * inserted; false if it isn't (already in tree).
     *
     * @param item to be inserted
     * @return boolean for the insertion
     */
    boolean add(E item);

    /**
     * true if target is in the tree
     *
     * @param target searched value
     * @return true if in tree
     */
    boolean contains(E target);

    /**
     * Returns a reference to the data in the node that is equal to target. If
     * no such node is found, returns nul 1.
     *
     * @param target searched element
     * @return reference to el if found, null otherwise
     */
    E find(E target);

    /**
     * Removes target (if found) from tree and returns it; otherwise, returns
     * null.
     *
     * @param target to be removed
     * @return reference of the returned value, null if not found
     */
    E delete(E target);

    /**
     * same as delte just returns boolen
     *
     * @param target value to be deleted
     * @return true if found false otherwise
     */
    boolean remove(E target);
}
