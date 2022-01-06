import java.util.Random;

/**
 * Public Class Test3 Contains Part 3 Test Methods
 *
 * @author Seyda Nur DEMIR
 * @version 1.0
 * @since 2021-06-12
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Test3 {
    /**
     * insertTree method
     * Inserts number of elements to the given tree that given size
     * Prints result with data structure name
     * 
     * @param datastructure String
     * @param tree SearchTree
     * @param SIZE int
     * @param NUMBER int
     */
    public void insertTree(String datastructure, SearchTree<Integer> tree[], int SIZE, int NUMBER) {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < NUMBER; j++) {
                tree[i].add(rand.nextInt() % NUMBER + 1);
            }
        }
        System.out.println(datastructure + " : Inserted " + NUMBER + " Elements To The Tree");
    }
    
    /**
     * getRandomArray method
     * Generates random array with size n
     * 
     * @param n int
     * @return int[]
     */
    public int[] getRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n / 4; i++) {
            arr[i] = rand.nextInt() % 10000 + 1;
        }
        for (int i = n / 4; i < n / 2; i++) {
            arr[i] = rand.nextInt() % 20000 + 1;
        }
        for (int i = n / 2; i < (n * 3) / 4; i++) {
            arr[i] = rand.nextInt() % 40000 + 1;
        }
        for (int i = (n * 3) / 4; i < n; i++) {
            arr[i] = rand.nextInt() % 80000 + 1;
        }
        return arr;
    }
    
    /**
     * insertTree method
     * Inserts number of extra elements to the given tree with number of elements that given size
     * Prints result with data structure name
     * Also calculates total and average running time
     * 
     * @param datastructure String
     * @param tree SearchTree
     * @param SIZE int
     * @param NUMBER int
     * @param EXTRA int
     */
    public void insertTree(String datastructure, SearchTree<Integer> tree[], int SIZE, int NUMBER, int EXTRA) {
        int[] arr = getRandomArray(EXTRA);
        long start, end, diff, avg;
        start = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < arr.length; j++) {
                tree[i].add(arr[j]);
            }
        }
        end = System.nanoTime();
        diff = end - start;
        avg = diff / arr.length;
        System.out.println(datastructure + " : Running Time For Inserting " + EXTRA + " Elements To The Tree With " + NUMBER + " Elements Is : " + diff + " NanoSeconds");
        System.out.println(datastructure + " : Average Time For Inserting " + EXTRA + " Elements To The Tree With " + NUMBER + " Elements Is : " + avg + " NanoSeconds");
    }
    
    /**
     * test3 method
     * Constructs data structures for size = 10 times
     * Adds number of elements number1 = 10000, number2 = 20000, number4 = 40000 and number8 = 80000 elements
     * Finally adds extra = 100 elements, calculates total and average running times
     */
    public void test3() {
        int SIZE = 10;
        int NUMBER1 = 10000;
        int NUMBER2 = 20000;
        int NUMBER4 = 40000;
        int NUMBER8 = 80000;
        int EXTRA = 100;
        String bst = new String("Binary Search Tree");
        String rbt = new String("Red Black Tree    ");
        String ttt = new String("Two Three Tree    ");
        String bt = new String("B Tree            ");
        String sl = new String("Skip List         ");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructing " + SIZE + " Instances For Each DataStructures With " + NUMBER1 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        BinarySearchTree<Integer>[] binarySearchTree1 = new BinarySearchTree[SIZE];
        RedBlackTree<Integer>[] redBlackTree1 = new RedBlackTree[SIZE];
        TwoThreeFourTree<Integer>[] twoThreeTree1 = new TwoThreeFourTree[SIZE];
        BTree<Integer>[] bTree1 = new BTree[SIZE];
        SkipList<Integer>[] skipList1 = new SkipList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            binarySearchTree1[i] = new BinarySearchTree<>();
            redBlackTree1[i] = new RedBlackTree<>();
            twoThreeTree1[i] = new TwoThreeFourTree<>();
            bTree1[i] = new BTree<>();
            skipList1[i] = new SkipList<>();
        }
        System.out.println(bst + " : Constructed " + SIZE + " Times For " + NUMBER1 + " Elements Operations");
        System.out.println(rbt + " : Constructed " + SIZE + " Times For " + NUMBER1 + " Elements Operations");
        System.out.println(ttt + " : Constructed " + SIZE + " Times For " + NUMBER1 + " Elements Operations");
        System.out.println(bt + " : Constructed " + SIZE + " Times For " + NUMBER1 + " Elements Operations");
        System.out.println(sl + " : Constructed " + SIZE + " Times For " + NUMBER1 + " Elements Operations");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructed " + SIZE*5 + " Instances Totally");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructing " + SIZE + " Instances For Each DataStructures With " + NUMBER2 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        BinarySearchTree<Integer>[] binarySearchTree2 = new BinarySearchTree[SIZE];
        RedBlackTree<Integer>[] redBlackTree2 = new RedBlackTree[SIZE];
        TwoThreeFourTree<Integer>[] twoThreeTree2 = new TwoThreeFourTree[SIZE];
        BTree<Integer>[] bTree2 = new BTree[SIZE];
        SkipList<Integer>[] skipList2 = new SkipList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            binarySearchTree2[i] = new BinarySearchTree<>();
            redBlackTree2[i] = new RedBlackTree<>();
            twoThreeTree2[i] = new TwoThreeFourTree<>();
            bTree2[i] = new BTree<>();
            skipList2[i] = new SkipList<>();
        }
        System.out.println(bst + " : Constructed " + SIZE + " Times For " + NUMBER2 + " Elements Operations");
        System.out.println(rbt + " : Constructed " + SIZE + " Times For " + NUMBER2 + " Elements Operations");
        System.out.println(ttt + " : Constructed " + SIZE + " Times For " + NUMBER2 + " Elements Operations");
        System.out.println(bt + " : Constructed " + SIZE + " Times For " + NUMBER2 + " Elements Operations");
        System.out.println(sl + " : Constructed " + SIZE + " Times For " + NUMBER2 + " Elements Operations");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructed " + 2*SIZE*5 + " Instances Totally");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructing " + SIZE + " Instances For Each DataStructures With " + NUMBER4 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        BinarySearchTree<Integer>[] binarySearchTree4 = new BinarySearchTree[SIZE];
        RedBlackTree<Integer>[] redBlackTree4 = new RedBlackTree[SIZE];
        TwoThreeFourTree<Integer>[] twoThreeTree4 = new TwoThreeFourTree[SIZE];
        BTree<Integer>[] bTree4 = new BTree[SIZE];
        SkipList<Integer>[] skipList4 = new SkipList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            binarySearchTree4[i] = new BinarySearchTree<>();
            redBlackTree4[i] = new RedBlackTree<>();
            twoThreeTree4[i] = new TwoThreeFourTree<>();
            bTree4[i] = new BTree<>();
            skipList4[i] = new SkipList<>();
        }
        System.out.println(bst + " : Constructed " + SIZE + " Times For " + NUMBER4 + " Elements Operations");
        System.out.println(rbt + " : Constructed " + SIZE + " Times For " + NUMBER4 + " Elements Operations");
        System.out.println(ttt + " : Constructed " + SIZE + " Times For " + NUMBER4 + " Elements Operations");
        System.out.println(bt + " : Constructed " + SIZE + " Times For " + NUMBER4 + " Elements Operations");
        System.out.println(sl + " : Constructed " + SIZE + " Times For " + NUMBER4 + " Elements Operations");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructed " + 3*SIZE*5 + " Instances Totally");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructing " + SIZE + " Instances For Each DataStructures With " + NUMBER8 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        BinarySearchTree<Integer>[] binarySearchTree8 = new BinarySearchTree[SIZE];
        RedBlackTree<Integer>[] redBlackTree8 = new RedBlackTree[SIZE];
        TwoThreeFourTree<Integer>[] twoThreeTree8 = new TwoThreeFourTree[SIZE];
        BTree<Integer>[] bTree8 = new BTree[SIZE];
        SkipList<Integer>[] skipList8 = new SkipList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            binarySearchTree8[i] = new BinarySearchTree<>();
            redBlackTree8[i] = new RedBlackTree<>();
            twoThreeTree8[i] = new TwoThreeFourTree<>();
            bTree8[i] = new BTree<>();
            skipList8[i] = new SkipList<>();
        }
        System.out.println(bst + " : Constructed " + SIZE + " Times For " + NUMBER8 + " Elements Operations");
        System.out.println(rbt + " : Constructed " + SIZE + " Times For " + NUMBER8 + " Elements Operations");
        System.out.println(ttt + " : Constructed " + SIZE + " Times For " + NUMBER8 + " Elements Operations");
        System.out.println(bt + " : Constructed " + SIZE + " Times For " + NUMBER8 + " Elements Operations");
        System.out.println(sl + " : Constructed " + SIZE + " Times For " + NUMBER8 + " Elements Operations");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Constructed " + 4*SIZE*5 + " Instances Totally");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting " + NUMBER1 + " Random Elements To The " + SIZE + " Instances of Each DataStructures With " + NUMBER1 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree1, SIZE, NUMBER1);
        insertTree(rbt, redBlackTree1, SIZE, NUMBER1);
        insertTree(ttt, twoThreeTree1, SIZE, NUMBER1);
        insertTree(bt, bTree1, SIZE, NUMBER1);
        insertTree(sl, skipList1, SIZE, NUMBER1);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted " + NUMBER1 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER1 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting " + NUMBER2 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER2 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree2, SIZE, NUMBER2);
        insertTree(rbt, redBlackTree2, SIZE, NUMBER2);
        insertTree(ttt, twoThreeTree2, SIZE, NUMBER2);
        insertTree(bt, bTree2, SIZE, NUMBER2);
        insertTree(sl, skipList2, SIZE, NUMBER2);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted " + NUMBER2 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER2 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting " + NUMBER4 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER4 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree4, SIZE, NUMBER4);
        insertTree(rbt, redBlackTree4, SIZE, NUMBER4);
        insertTree(ttt, twoThreeTree4, SIZE, NUMBER4);
        insertTree(bt, bTree4, SIZE, NUMBER4);
        insertTree(sl, skipList4, SIZE, NUMBER4);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted " + NUMBER4 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER4 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting " + NUMBER8 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER8 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree8, SIZE, NUMBER8);
        insertTree(rbt, redBlackTree8, SIZE, NUMBER8);
        insertTree(ttt, twoThreeTree8, SIZE, NUMBER8);
        insertTree(bt, bTree8, SIZE, NUMBER8);
        insertTree(sl, skipList8, SIZE, NUMBER8);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted " + NUMBER8 + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER8 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting  Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER1 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree1, SIZE, NUMBER1, EXTRA);
        insertTree(rbt, redBlackTree1, SIZE, NUMBER1, EXTRA);
        insertTree(ttt, twoThreeTree1, SIZE, NUMBER1, EXTRA);
        insertTree(bt, bTree1, SIZE, NUMBER1, EXTRA);
        insertTree(sl, skipList1, SIZE, NUMBER1, EXTRA);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER1 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting  Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER2 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree2, SIZE, NUMBER2, EXTRA);
        insertTree(rbt, redBlackTree2, SIZE, NUMBER2, EXTRA);
        insertTree(ttt, twoThreeTree2, SIZE, NUMBER2, EXTRA);
        insertTree(bt, bTree2, SIZE, NUMBER2, EXTRA);
        insertTree(sl, skipList2, SIZE, NUMBER2, EXTRA);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER2 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting  Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER4 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree4, SIZE, NUMBER4, EXTRA);
        insertTree(rbt, redBlackTree4, SIZE, NUMBER4, EXTRA);
        insertTree(ttt, twoThreeTree4, SIZE, NUMBER4, EXTRA);
        insertTree(bt, bTree4, SIZE, NUMBER4, EXTRA);
        insertTree(sl, skipList4, SIZE, NUMBER4, EXTRA);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER4 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserting  Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER8 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        insertTree(bst, binarySearchTree8, SIZE, NUMBER8, EXTRA);
        insertTree(rbt, redBlackTree8, SIZE, NUMBER8, EXTRA);
        insertTree(ttt, twoThreeTree8, SIZE, NUMBER8, EXTRA);
        insertTree(bt, bTree8, SIZE, NUMBER8, EXTRA);
        insertTree(sl, skipList8, SIZE, NUMBER8, EXTRA);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Inserted Extra " + EXTRA + " Random Elements To The " + SIZE + " Instances of DataStructures With " + NUMBER8 + " Elements");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
}
