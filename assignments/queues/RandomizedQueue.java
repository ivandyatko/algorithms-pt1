import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;


    /**
     * Construct an empty randomized queue.
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }


    /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }


    /**
     * Return the number of items on the queue.
     */
    public int size() {
        return n;
    }


    /**
     * Resize the underlying array.
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }


    /**
     * Add the item.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Nulls not allowed for insertion");
        }

        // double size of array if necessary and recopy to front of array
        if (n == q.length) {
            resize(2 * q.length);   // double size of array if necessary
        }
        q[last++] = item;           // add item
        if (last == q.length) {
            last = 0;               // wrap-around
        }
        n++;
    }


    /**
     * remove and return a random item
     */
    public Item dequeue() {
        checkEmpty();
        final int rand = getRandom();
        Item item = q[rand];
        q[rand] = null;                   // to avoid loitering
        n--;
        if (rand == first) {
            first++;
        } else if (rand == last) {
            last--;
        } else {
            offset(rand);
        }
        if (first == q.length) {
            first = 0;                      // wrap-around
        }
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }


    private void offset(int rand) {
        while (rand < last) {
            q[rand] = q[++rand];
        }
        last--;
    }


    /**
     * Return (but do not remove) a random item.
     */
    public Item sample() {
        checkEmpty();
        return q[getRandom()];
    }


    private int getRandom() {
        if (first <= last) {
            return StdRandom.uniform(first, last);
        } else {

        }
    }


    /**
     * Return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {

        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return false;
            }


            @Override
            public Item next() {
                return null;
            }


            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove is not supported");
            }
        };
    }


    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing to remove");
        }
    }


    public static void main(String[] args) {
        // unit testing
    }
}