import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] stack;
    private int tail, count;


    /**
     * Construct an empty randomized queue.
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        stack = (Item[]) new Object[2];
    }


    /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return count == 0;
    }


    /**
     * Return the number of items on the queue.
     */
    public int size() {
        return count;
    }


    /**
     * Resize the underlying array.
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= count;
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(stack, 0, temp, 0, tail);
        stack = temp;
    }


    /**
     * Add the item.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Nulls not allowed for insertion");
        }

        // double size of array if necessary and recopy to front of array
        if (count == stack.length) {
            resize(2 * stack.length);   // double size of array if necessary
        }
        stack[tail++] = item;           // add item
        count++;
    }


    /**
     * remove and return a random item
     */
    public Item dequeue() {
        checkEmpty();
        final int rand = getRandom();
        Item item = stack[rand];

        int i = rand;
        while (i < tail) {
            stack[i] = stack[++i];
        }
        tail--;
        //        stack[rand] = null;                   // to avoid loitering
        count--;

        // shrink size of array if necessary
        if (count > 0 && count == stack.length / 4) {
            resize(stack.length / 2);
        }
        return item;
    }


    /**
     * Return (but do not remove) a random item.
     */
    public Item sample() {
        checkEmpty();
        return stack[getRandom()];
    }


    private int getRandom() {
        return StdRandom.uniform(0, tail);
    }


    /**
     * Return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {

        int[] copy = new int[count];
        for (int i = 0; i < count; i++) {
            copy[i] = i;
        }
        StdRandom.shuffle(copy);

        return new Iterator<Item>() {
            int current = 0;


            @Override
            public boolean hasNext() {
                return current <= count;
            }


            @Override
            public Item next() {
                return stack[copy[current++]];
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
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());

        try {
            StdOut.println(q.dequeue());
        } catch (NoSuchElementException e) {
            StdOut.println("caught NSEE");
        }

        q.enqueue(5);

        StdOut.println(q.size());
        StdOut.println(q.sample().equals(q.sample()));

        StdOut.println("== 5 ? : " + (q.dequeue() == 5));
    }
}