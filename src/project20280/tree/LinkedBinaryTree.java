package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;
//import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)
/*
    public static void main(String [] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());
    }
*/
    public static void main(String [] args) {
        Integer [] inorder= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer [] preorder= {18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16,
                17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30};

        LinkedBinaryTree <Integer > bt = new LinkedBinaryTree <>();
        bt.construct(inorder , preorder);
        System.out.println(bt.toBinaryTreeString());

        LinkedBinaryTree<String> bt2 = new LinkedBinaryTree<>();

        Position<String> a = bt2.addRoot("A");
        Position<String> b = bt2.addLeft(a, "B");
        Position<String> c = bt2.addRight(a, "C");
        Position<String> d = bt2.addLeft(b, "D");
        Position<String> e = bt2.addRight(b, "E");
        Position<String> f = bt2.addRight(c, "F");
        Position<String> g = bt2.addLeft(e, "G");
        Position<String> h = bt2.addRight(e, "H");

        System.out.println(bt2.toBinaryTreeString());
        System.out.print("Leaves: ");
        bt2.printLeaves();
    }

    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new  IllegalStateException("The tree is not empty");
        }
        root = createNode(e, null, null, null);
        size++;
        return root;
    }

    public void insert(E e) {
        root = addRecursive(root, e);
    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
       if (p == null) {
           size++;
           return createNode(e, null, null, null);
       }
       if ((Integer) e < (Integer) p.getElement()) {
           Node<E> left = addRecursive(p.getLeft(), e);
           p.setLeft(left);
           left.setParent(p);
       }
       else {
           Node<E> right = addRecursive(p.getRight(), e);
           p.setRight(right);
           right.setParent(p);
       }
       return p;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (node.getLeft() != null) {
            throw new IllegalArgumentException("p already has a left child");
        }
        Node<E> left = createNode(e, node, null, null);
        node.setLeft(left);
        size++;
        return left;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (node.getRight() != null) {
            throw new IllegalArgumentException("p already has a right child");
        }
        Node<E> right = createNode(e, node, null, null);
        node.setRight(right);
        size++;
        return right;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) {
            throw new IllegalArgumentException("p is not a leaf");
        }
        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            size += t1.size;
        }
        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            size += t2.size;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("p has two children");
        }
        Node<E> child = (node.getLeft() != null ? node.getRight() : node.getLeft());
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child;
        }
        else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            }
            else {
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();
        node.setParent(node);
        node.setElement(null);
        return temp;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        if (i < l.size()) {
            Node<E> node = createNode(l.get(i), p, null, null);
            size++;
            node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
            node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
            return node;
        }
        return null;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (i < arr.length && arr[i] != null) {
            Node<E> node = createNode(arr[i], p, null, null);
            size++;
            node.setLeft(createLevelOrderHelper(arr, node, 2 * i + 1));
            node.setRight(createLevelOrderHelper(arr, node, 2 * i + 2));
            return node;
        }
        return null;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    public void construct(E[] inorder, E[] preorder) {
        root = constructHelper(inorder, preorder, 0, inorder.length - 1, 0, preorder.length - 1);
        size = preorder.length;
    }

    private Node<E> constructHelper(E[] inorder, E[] preorder, int inStart, int inEnd, int preStart, int preEnd) {
        if (inStart > inEnd || preStart > preEnd) return null;

        // root is always the first element of preorder
        E rootVal = preorder[preStart];
        Node<E> node = createNode(rootVal, null, null, null);

        // find root in inorder to split left and right subtrees
        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i].equals(rootVal)) {
                inIndex = i;
                break;
            }
        }

        int leftSize = inIndex - inStart;

        Node<E> left = constructHelper(inorder, preorder, inStart, inIndex - 1, preStart + 1, preStart + leftSize);
        Node<E> right = constructHelper(inorder, preorder, inIndex + 1, inEnd, preStart + leftSize + 1, preEnd);

        node.setLeft(left);
        node.setRight(right);
        if (left != null) left.setParent(node);
        if (right != null) right.setParent(node);

        return node;
    }

    public ArrayList<ArrayList<E>> rootToLeafPaths() {
        ArrayList<ArrayList<E>> paths = new ArrayList<>();
        rootToLeafHelper(root, new ArrayList<>(), paths);
        return paths;
    }

    private void rootToLeafHelper(Node<E> node, ArrayList<E> current, ArrayList<ArrayList<E>> paths) {
        if (node == null) return;

        current.add(node.getElement());

        if (isExternal(node)) {  // leaf node
            paths.add(new ArrayList<>(current));
        } else {
            rootToLeafHelper(node.getLeft(), current, paths);
            rootToLeafHelper(node.getRight(), current, paths);
        }

        current.remove(current.size() - 1);  // backtrack
    }


    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

    public void printLeaves() {
        printLeavesHelper(root);
    }

    private void printLeavesHelper(Node<E> node) {
        if (node == null) return;                          // empty subtree

        printLeavesHelper(node.getLeft());                 // iterate throguh left

        if (node.getLeft() == null && node.getRight() == null) {  // leaf
            System.out.print(node.getElement() + " ");
        }

        printLeavesHelper(node.getRight());                // iterate through right
    }
}
