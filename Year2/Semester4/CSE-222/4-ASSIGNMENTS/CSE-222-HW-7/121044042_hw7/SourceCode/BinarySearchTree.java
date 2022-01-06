public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E> {

    /**
     * Stores a second rerum value from the recursive add method that indicates
     * whether the item has been inserted.
     */
    protected boolean addReturn;
    /**
     * Stores a second return value from the recursive de 1 ete method that
     * references the item that was stored in the tree
     */
    protected E deleteReturn;

    /**
     * Insens item where it belongs in the tree. Returns true if item is
     * inserted; false if it isn't (already in tree).
     *
     * @param item to be inserted
     * @return boolean for the insertion
     */
    @Override
    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    /**
     * private method for adding a comparable item
     *
     * @param localRoot current tree( addes only to the children of leaves
     * @param item item to be added
     * @return subtree that is to be updated( left/right children and root are
     * upadted(root only at the beginning) )
     */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            return new Node<E>(item);
        } else if (item.compareTo(localRoot.data) == 0) {
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    /**
     * true if target is in the tree
     *
     * @param target searched value
     * @return true if in tree
     */
    @Override
    public boolean contains(E target) {
        return find(target) != null;
    }

    /**
     * Returns a reference to the data in the node that is equal to target. If
     * no such node is found, returns nul 1. The target object must implement
     * the Comparabl e interface.
     *
     * @param target searched element that is Comparable
     * @return reference to el if found, null otherwise
     */
    @Override
    public E find(E target) {
        return find(root, target);
    }

    /**
     * private method for finding the node
     *
     * @param localRoot tree to be searched
     * @param target value to be found
     * @return the reference to the node holding the value
     */
    private E find(Node<E> localRoot, E target) {
        if (localRoot == null) {
            return null;
        }
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0) {
            return localRoot.data;
        } else if (compResult < 0) {
            return find(localRoot.left, target);
        } else {
            return find(localRoot.right, target);
        }

    }

    /**
     * Removes target (if found) from tree and returns it; otherwise, returns
     * null.
     *
     * @param target to be removed
     * @return reference of the returned value, null if not found
     */
    @Override
    public E delete(E target) {
        root = delete(root, target);
        return deleteReturn;
    }

    private Node<E> delete(Node<E> localRoot, E item) {
        if (localRoot == null) {
            deleteReturn = null;
            return localRoot;
        }
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        } else {
            //item is at local root and needs to be removed
            if (localRoot.left == null) {
                //if no left child return right child
                return localRoot.right;
            } else if (localRoot.right == null) {
                //no right child so return the left child
                return localRoot.left;
            } else {
                if (localRoot.left.right == null) {
                    //left child has no right children
                    //replacing the data with the data in the left child
                    localRoot.data = localRoot.left.data;
                    //than replacing left child with its left child
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                } else {
                    //searching for inorder precessor(ip) and replacing the deleted node's data with it
                    localRoot.data = findLargestChild(localRoot.left);
                    return localRoot;
                }
            }
        }
    }

    private E findLargestChild(Node<E> parent) {
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild(parent.right);
        }
    }

    /**
     * same as delete just returns boolen
     *
     * @param target value to be deleted
     * @return true if found false otherwise
     */
    @Override
    public boolean remove(E target) {
        return delete(target) != null;
    }
}
