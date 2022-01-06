/**
 * Checks BST is AVL or not
 */
public class BSTAVL {

	/**
	 * getHeight method gets the height of the given Binary Tree.
	 *
	 * @param node BinaryTree
	 * @return int
	 */
	public int getHeight(BinaryTree<Integer> node) {
		if (node == null) {
			return 0;
		}

		return (1 + Math.max(getHeight(node.Subtree()), getHeight(node.getRightSubtree())));
	}

	/**
	 * isBalanced method checks the given Binary Tree is balanced or not.
	 *
	 * @param root
	 * @return boolean
	 */
	public boolean isBalanced(BinaryTree<Integer> root) {
		int leftHeight;
		int rightHeight;

		if (root == null) {
			return true;
		}

		leftHeight = getHeight(root.Subtree());
		rightHeight = getHeight(root.getRightSubtree());

		if ((Math.abs(leftHeight - rightHeight) <= 1) && (isBalanced(root.Subtree())) && (isBalanced(root.getRightSubtree()))) {
			return true;
		}

		return false;
	}

	/**
	 * isAVL method checks the given tree is AVL or not.
	 *
	 * @param tree BinarySearchTree
	 * @return boolean
	 */
	public boolean isAVL(BinarySearchTree<Integer> tree) {
		if (isBalanced(tree)) {
			return true;
		} else {
			return false;
		}
	}
}
