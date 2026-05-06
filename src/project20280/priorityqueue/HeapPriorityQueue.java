package project20280.priorityqueue;

/*
 */

import project20280.interfaces.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * An implementation of a priority queue using an array-based heap.
 */

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public HeapPriorityQueue() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Creates a priority queue initialized with the respective key-value pairs. The
     * two arrays given will be paired element-by-element. They are presumed to have
     * the same length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     *
     * @param keys   an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            heap.add(new PQEntry<>(keys[i], values[i]));
        }
        heapify();
    }

    // protected utilities
    protected int parent(int j) {
        return (j -1) / 2;
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap
     * property.
     */
    protected void upheap(int j) {
        while (j > 0) {
            int p = parent(j);
            if (compare(heap.get(j), heap.get(p)) >= 0) break;
            swap(j, p);
            j = p;
        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void downheap(int j) {
        while (hasLeft(j)) {
            int smallerChild = left(j);

            if (hasRight(j) && compare(heap.get(right(j)), heap.get(left(j))) < 0) {
                smallerChild = right(j);
            }

            if (compare(heap.get(j), heap.get(smallerChild)) <= 0) break;

            swap(j, smallerChild);
            j = smallerChild;  // continue from smaller
        }
    }

    /**
     * Performs a bottom-up construction of the heap in linear time.
     */
    protected void heapify() {
        int startIndex = parent(heap.size() - 1); // index of last internal node
        for (int j = startIndex; j >= 0; j--) {
            downheap(j);
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     *
     * @return number of items
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     *
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K, V> min() {
        return heap.get(0);
    }

    /**
     * Inserts a key-value pair and return the entry created.
     *
     * @param key   the key of the new entry
     * @param value the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        heap.add(entry);           // add at the end
        upheap(heap.size() - 1);  // restore heap property
        return entry;
    }

    /**
     * Removes and returns an entry with minimal key.
     *
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) return null;

        Entry<K, V> min = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        downheap(0);
        return min;
    }

    public String toString() {
        return heap.toString();
    }

    /**
     * Used for debugging purposes only
     */
    private void sanityCheck() {
        for (int j = 0; j < heap.size(); j++) {
            int left = left(j);
            int right = right(j);
            //System.out.println("-> " +left + ", " + j + ", " + right);
            Entry<K, V> e_left, e_right;
            e_left = left < heap.size() ? heap.get(left) : null;
            e_right = right < heap.size() ? heap.get(right) : null;
            if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0) {
                System.out.println("Invalid left child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
            if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0) {
                System.out.println("Invalid right child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
        }
    }

    public static <K extends Comparable<K>> void pqSort(K[] arr) {
        HeapPriorityQueue<K, K> pq = new HeapPriorityQueue<>();

        // insert into heap O(n log n)
        for (K k : arr) {
            pq.insert(k, k);
        }

        // remove min repeatedly back into the array O(n log n)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = pq.removeMin().getKey();
        }
    }

    public static void heapSort(Integer[] arr) {

        int n = arr.length;

        // build max-heap in place: O(n)
        // start from last internal node, downheap each node to root
        for (int j = (n - 2) / 2; j >= 0; j--) {
            maxDownheap(arr, j, n);
        }
        System.out.println("After heapify: " + Arrays.toString(arr));

        // sort O(n log n)
        // swap root (max) with last element, shrink heap, restore
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            maxDownheap(arr, 0, i);
            System.out.println("Step:          " + Arrays.toString(arr));
        }
    }

    // downheap for a max-heap - largest child wins
    private static void maxDownheap(Integer[] arr, int j, int size) {
        while (true) {
            int left  = 2 * j + 1;
            int right = 2 * j + 2;
            int largest = j;

            if (left  < size && arr[left]  > arr[largest]) largest = left;
            if (right < size && arr[right] > arr[largest]) largest = right;

            if (largest == j) break; // heap property satisfied

            swap(arr, j, largest);
            j = largest;
        }
    }

    private static void swap(Integer[] arr, int i, int j) {
        Integer temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Method 1: One-by-one insert O(n log n)");
        System.out.println("======================================");
        Integer[] keys = {2, 5, 16, 4, 10, 23, 39, 18, 26, 15};
        HeapPriorityQueue<Integer, Integer> pq1 = new HeapPriorityQueue<>();
        for (Integer k : keys) {
            pq1.insert(k, k);
            System.out.println("Inserted " + k + ": " + pq1);
        }
        System.out.println("\nFinal heap array: " + pq1);
        printHeapTree(pq1.heap);

        System.out.println("======================================");
        System.out.println("Method 2: Bottom-up heapify O(n)     ");
        System.out.println("======================================");
        HeapPriorityQueue<Integer, Integer> pq2 = new HeapPriorityQueue<>(keys, keys);
        System.out.println("\nFinal heap array: " + pq2);
        printHeapTree(pq2.heap);

        System.out.println("======================================");
        System.out.println("Method 3: removeMin() on heapified pq");
        System.out.println("======================================");
        System.out.println("Sorted order: ");
        HeapPriorityQueue<Integer, Integer> pq3 = new HeapPriorityQueue<>(keys, keys);
        while (!pq3.isEmpty()) {
            System.out.print(pq3.removeMin().getKey() + " ");
        }
        System.out.println();

        Integer[] arr1 = {35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
        Integer[] arr2 = arr1.clone();

        System.out.println("Original:      " + Arrays.toString(arr1));

        pqSort(arr1);
        System.out.println("PQSort:        " + Arrays.toString(arr1));

        heapSort(arr2);
        System.out.println("HeapSort:      " + Arrays.toString(arr2));
    }

    // prints the heap array as a level-by-level binary tree
    private static <K, V> void printHeapTree(ArrayList<Entry<K, V>> heap) {
        int size = heap.size();
        if (size == 0) {
            System.out.println("(empty heap)");
            return;
        }

        System.out.println("\nHeap Tree (level by level):");
        int level = 0;
        int index = 0;

        while (index < size) {
            int levelSize = (int) Math.pow(2, level); // nodes per level: 1, 2, 4, 8...
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < levelSize && index < size; i++) {
                System.out.print(heap.get(index).getKey());
                if (i < levelSize - 1 && index + 1 < size) System.out.print(", ");
                index++;
            }
            System.out.println();
            level++;
        }
        System.out.println();
    }
}
