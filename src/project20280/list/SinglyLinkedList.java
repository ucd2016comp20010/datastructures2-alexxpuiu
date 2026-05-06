package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element  = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {

        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> curr = head;
        for (int i = 0; i < position; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    @Override
    public void add(int position, E e) {

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == 0) {
            addFirst(e);
            return;
        }

        Node<E> curr = head;
        for (int i = 0; i < position - 1; i++) {
            curr = curr.getNext();
        }

        curr.setNext(new Node<>(e, curr.getNext()));
        size++;
    }


    @Override
    public void addFirst(E e) {

        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {

        if (isEmpty()) {
            addFirst(e);
            return;
        }

        Node<E> curr = head;
        while (curr.getNext() != null) {
            curr = curr.getNext();
        }
        curr.setNext(new Node<>(e, null));
        size++;
    }

    @Override
    public E remove(int position) {

        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == 0) {
            return removeFirst();
        }

        Node<E> curr = head;
        for (int i = 0; i < position - 1; i++) {
            curr = curr.getNext();
        }

        E removed = curr.getNext().getElement();
        curr.setNext(curr.getNext().getNext());
        size--;
        return removed;
    }

    @Override
    public E removeFirst() {

        if (isEmpty()) {
            return null;
        }

        E removed = head.getElement();
        head = head.getNext();
        size--;

        return removed;
    }

    @Override
    public E removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            return removeFirst();
        }

        Node<E> curr = head;
        while (curr.getNext().getNext() != null) {
            curr = curr.getNext();
        }

        E removed = curr.getNext().getElement();
        curr.setNext(null);
        size--;
        return removed;
    }

    public SinglyLinkedList<E> mergeSorted(SinglyLinkedList<E> l1, SinglyLinkedList<E> l2) {
        SinglyLinkedList<E> merged = new SinglyLinkedList<>();

        int position1 = 0;
        int position2 = 0;
        while (position1 < l1.size() && position2 < l2.size()) {
            Comparable<E> el1 = (Comparable<E>) l1.get(position1);
            E el2 = (E) l2.get(position2);

            if (el1.compareTo(el2) < 0) {
                merged.addLast(l1.get(position1));
                position1++;
            }
            else if (el1.compareTo(el2) > 0) {
                merged.addLast(el2);
                position2++;
            }
            else {
                merged.addLast(l1.get(position1));
                position1++;
                merged.addLast(l2.get(position2));
                position2++;
            }
        }

        while (position1 < l1.size()) {
            merged.addLast(l1.get(position1));
            position1++;
        }

        while (position2 < l2.size()) {
            merged.addLast(l2.get(position2));
            position2++;
        }

        return merged;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

        ll.reverse();
        System.out.println(ll);

        System.out.println("\nTRIBONACCI:");
        System.out.println(tribonacci(9));
        //9th term is 44

        System.out.println("\nMcCarthy91:");
        System.out.println(M(87));
    }

    public static long tribonacci(int n) {
        if (n == 0 || n == 1) return 0;
        if (n == 2) return 1;

        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }

    //McCarthy nested recursion method
    public static int M(int n) {
        if (n > 100) {
            return n - 10;
        } else {
            return M(M(n + 11));
        }
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> curr = head;

        while (curr != null) {
            Node<E> next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }

        head = prev;
    }

    public SinglyLinkedList<E> recursiveCopy() {
        SinglyLinkedList<E> copy = new SinglyLinkedList<>();
        copyHelper(head, copy);
        return copy;
    }

    private void copyHelper(Node<E> node, SinglyLinkedList<E> copy) {
        if (node == null) return;           // empty list
        copy.addLast(node.getElement());    // add head
        copyHelper(node.getNext(), copy);   // recursive
    }
}
