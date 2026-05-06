package project20280.stacksqueues;

import project20280.interfaces.Stack;
import project20280.list.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {

    DoublyLinkedList<E> ll;

    public static void main(String[] args) {
    }

    public LinkedStack() {
        ll = new DoublyLinkedList<>();
    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public void push(E e) {
        ll.addFirst(e);
    }

    @Override
    public E top() {

        return ll.first();
    }

    @Override
    public E pop() {

        return ll.removeFirst();
    }

    public String toString() {
        return ll.toString();
    }

    /*
        Pseudocode for implementing a Queue using 2 Stacks:

        stack 1 (for enqueue)
        stack 2 (for dequeue)

        enqueue(e)
            stack1.push(e)

        dequeue()
            //if outbox is empty, move elements to inbox (stack1)
            if stack2.isEmpty()
                while !stack1.isEmpty():
                    stack2.push(stack1.pop())

            // pop from outbox
            if stack2.isEmpty():
                return null


            return stack2.pop()





        Pseudocode for reversing a stack using two additional stacks:

        Input: stack (to be reversed)
        Output: (reversed) stack

        Create stack 2
        Create stack 3

        // reverse elements by pushing into stack2
        while stack !empty:
            element = stack.pop
            stack2.push(element)

        // reverse back to original order
        while stack2 !empty:
            element = stack2.pop()
            stack3.push(element)

        // reverse back into original stack
        while stack3 !empty:
            element = stack3.pop()
            stack.push(element)

     */
}
