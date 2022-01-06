import java.io.Serializable;
import java.util.Scanner;

@SuppressWarnings({"unchecked", "rawtypes", "serial"})
public class BinaryTree<E> implements Serializable {

    // Insert inner class Node<E> here
    protected Node<E> root;

    public BinaryTree() {
        root = null;
    }

    protected BinaryTree(Node<E> root) {
        this.root = root;
    }

    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new Node<E>(data);
        if (leftTree != null) {
            root.left = leftTree.root;
        } else {
            root.left = null;
        }
        if (rightTree != null) {
            root.right = rightTree.root;
        } else {
            root.right = null;
        }
    }

    public E getData() {
        if (root != null) {
            return root.data;
        } else {
            return null;
        }
    }

    /**
     * Returns left subtree
     *
     * @return left subtree
     */
    public BinaryTree<E> Subtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<E>(root.left);
        } else {
            return null;
        }
    }

    /**
     * Returns right subtree
     *
     * @return right subtree
     */
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<E>(root.right);
        } else {
            return null;
        }
    }

    /**
     * @return returns true if node is leaf and false otherwise
     */
    public boolean isLeaf() {
        if (root != null) {
            return (root.left == null && root.right == null);
        } else {
            return false;
        }
    }

    /**
     * uses preOrederTraverse
     *
     * @return returns string representation of the tree
     */
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrderTraverse(root, 1, sb);
        return sb.toString();
    }

    private void inOrderTraverse(Node<E> node, int depth,
            StringBuilder sb) {
        if (node == null) {
            return;
        } else {
            inOrderTraverse(node.left, depth + 1, sb);
            sb.append(node.toString() + " ");
            inOrderTraverse(node.right, depth + 1, sb);
        }
    }

    private void preOrderTraverse(Node<E> node, int depth,
            StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append(" "); // indentation
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    public static BinaryTree<String> readBinaryTree(Scanner scan) {
        String data = scan.next();
        if (data.equals("null")) {
            return null;
        } else {
            BinaryTree<String> leftTree = readBinaryTree(scan);
            BinaryTree<String> rightTree = readBinaryTree(scan);
            return new BinaryTree<String>(data, leftTree, rightTree);
        }
    }

    protected static class Node<E> implements Serializable {

        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

		@Override
        public String toString() {
            return data.toString();
        }
    }
}
