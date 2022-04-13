/*
 * It must support each randomized queue operation (besides creating an iterator) in
 * constant amortized time. It must use at most 48n + 192 bytes of memory.
 * The iterator implementation must support operations next() and hasNext() in constant
 * worst-case time; and construction in linear time (with a linear amount of extra memory
 * per iterator)
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/* 
 * 
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[1];
        size = 0;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int remainders;
        int[] randomIndices;
        public RandomizedQueueIterator() {
            remainders = size;
            randomIndices = StdRandom.permutation(size);
        }
        public boolean hasNext() {
            return (remainders > 0);
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            remainders -= 1;
            return data[randomIndices[remainders]];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == data.length) {
            resize(2 * data.length);
        }
        data[size] = item;
        size += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size <= data.length/4) {
            resize(data.length/2);
        }
        int sampleIdx = StdRandom.uniform(size);
        Item res = data[sampleIdx];
        // swap the vacancy with the last element after each call
        data[sampleIdx] = data[size-1];
        data[size-1] = null;
        size -= 1;
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int sampleIdx = StdRandom.uniform(size);
        return data[sampleIdx];
    }

    private void resize(int n) {
        Item[] newData = (Item[]) new Object[n];
        for (int i = 0; i < size; i += 1) {
            newData[i] = data[i];
        }
        data = newData;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(3);
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
        rq.enqueue(5);
        rq.enqueue(7);
        rq.enqueue(9);
        System.out.println(rq.dequeue());
        System.out.println(rq.sample());
        Iterator<Integer> iter = rq.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
