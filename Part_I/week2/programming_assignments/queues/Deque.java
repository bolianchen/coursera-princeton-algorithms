/* A double-ended queue or deque
 * It supports each deque operation in constant worst-case time.
 * It must use at most 48n + 192 bytes of memory.
 * main class: 16bytes object overhead + 8bytes(first) + 8bytes(last)
 * Node: 16bytes object overhead + 8bytes (as inner class) + 8bytes(item) + 8bytes(next)
 *       + 8bytes(prev)[
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/* 
 * implement with linkedlist
 */
public class Deque<Item> implements Iterable<Item> {
    private final Node<Item> sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    private class Node<Item> {
        public Item item;
        public Node<Item> prev;
        public Node<Item> next;

        public Node(Item i, Node<Item> p, Node<Item> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        Node<Item> p;
        public DequeIterator() {
            p = sentinel;
        }
        public boolean hasNext() {
            return (p.next != sentinel);
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            p = p.next;
            return p.item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size = size + 1;
        Node<Item> oldFirst = sentinel.next;
        Node<Item> newFirst = new Node<>(item, sentinel, oldFirst);
        oldFirst.prev = newFirst;
        sentinel.next = newFirst;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size = size + 1;
        Node<Item> oldLast = sentinel.prev;
        Node<Item> newLast = new Node<>(item, oldLast, sentinel);
        oldLast.next = newLast;
        sentinel.prev = newLast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> r = sentinel.next;
        Node<Item> newFirst = r.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        size = size - 1;
        return r.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> r = sentinel.prev;
        Node<Item> newLast = r.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        size = size - 1;
        return r.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        /* Unit testing.  Your main() method must call directly every public constructor
         * and method to help verify that they work as prescribed
         * (e.g., by printing results to standard output).
         */
        Deque<Integer> d = new Deque<>();
        System.out.println(d.isEmpty());
        d.addFirst(3);
        System.out.println(d.isEmpty());
        d.addFirst(5);
        d.addFirst(7);
        System.out.println(d.size());
        d.addLast(9);
        d.addLast(11);
        d.addLast(13);
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        Iterator<Integer> iter = d.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
