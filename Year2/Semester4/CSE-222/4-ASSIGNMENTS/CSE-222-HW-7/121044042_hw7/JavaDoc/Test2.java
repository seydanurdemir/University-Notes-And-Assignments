import java.util.Random;

/**
 * Public Class Test2 Contains Part 2 Test Methods
 *
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-06-12
 */
public class Test2 {

	/**
	 * insertTree method Takes a Binary Search Tree. Inserts random NUMBER
	 * elements to test.
	 *
	 * @param tree BinarySearchTree
	 */
	public void insertTree(BinarySearchTree<Integer> tree, int NUMBER) {
		Random rand = new Random();
		for (int i = 0; i < NUMBER; i++) {
			tree.add(rand.nextInt() % NUMBER + 1);
		}
	}
	
	/**
	 * checkTree method Takes a Binary Search Tree. Checks whether it is an AVL
	 * tree, or a RB tree, or any of them. If tree is an AVL tree and a RB tree,
	 * returns 0. If the tree is only an AVL tree, returns 1. If the tree is
	 * only a RB tree, returns 2. If the tree is any of them, returns 3.
	 *
	 * @param tree BinarySearchTree
	 * @return int
	 */
	public int checkTree(BinarySearchTree<Integer> tree) {
		if ((checkTreeAvl(tree) == true) && (checkTreeRb(tree) == true)) {
			return 0;
		} else if (checkTreeAvl(tree) == true) {
			return 1;
		} else if (checkTreeRb(tree) == true) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * checkTreeAvl method Takes a Binary Search Tree. Checks whether it is an
	 * AVL tree or not. Return true if the tree is an AVL Tree, otherwise
	 * returns false.
	 *
	 * @param tree BinarySearchTree
	 * @return boolean
	 */
	public boolean checkTreeAvl(BinarySearchTree<Integer> tree) {
		BSTAVL obj = new BSTAVL();
		return obj.isAVL(tree);
	}

	/**
	 * checkTreeRb method Takes a Binary Search Tree. Checks whether it is an RB
	 * tree or not. Return true if the tree is an RB Tree, otherwise returns
	 * false.
	 *
	 * @param tree BinarySearchTree
	 * @return boolean
	 */
	public boolean checkTreeRb(BinarySearchTree<Integer> tree) {
		BSTRB obj = new BSTRB();
		return obj.isRB(tree);
	}

	/**
	 * test2 method Constructs an AVL Tree and a Red Black Tree Checks whether
	 * the given BinarySearchTree is an AVL Tree or a RB Tree
	 */
	public void test2() {
		int NUMBER = 128;
		String bst = "BS  Tree";
		String avlt = "AVL Tree";
		String rbt = "RB  Tree";
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Constructing Instances For Each DataStructures");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree();
		System.out.println(bst + " : Constructed");
		AVLTree<Integer> avlTree = new AVLTree();
		System.out.println(avlt + " : Constructed");
		RedBlackTree<Integer> redBlackTree = new RedBlackTree();
		System.out.println(rbt + " : Constructed");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Constructed Instances For Each DataStructures");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Inserting Random Numbers To Trees");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		insertTree(binarySearchTree, NUMBER);
		System.out.println(bst + " : Inserted Elements");
		insertTree(avlTree, NUMBER);
		System.out.println(avlt + " : Inserted Elements");
		insertTree(redBlackTree, NUMBER);
		System.out.println(rbt + " : Inserted Elements");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Inserted Random Numbers To Trees");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Checking Tree If It Is An AVL or A RB or Anyone");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		int res = checkTree(binarySearchTree);
		System.out.println(bst + " : Checked");
		if (res == 0) {
			System.out.println(bst + " : Tree is AVL and RB balanced tree.");
		} else if (res == 1) {
			System.out.println(bst + " : Tree is AVL balanced tree.");
		} else if (res == 2) {
			System.out.println(bst + " : Tree is RB balanced tree.");
		} else {
			System.out.println(bst + " : Tree is not balanced.");
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Checked Tree If It Is An AVL or A RB or Anyone");
		System.out.println("------------------------------------------------------------------------------------------------------------------");

	}
}
