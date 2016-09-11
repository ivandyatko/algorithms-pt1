import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] stack;
    private int tail = -1, count;


    /**
     * Construct an empty randomized queue.
     */
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
    private void resize(int capacity) {
        assert capacity >= count;
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(stack, 0, temp, 0, count);
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
        stack[++tail] = item;           // add item
        count++;
    }


    /**
     * remove and return a random item
     */
    public Item dequeue() {
        checkEmpty();
        final int rand = getRandom();
        Item item = stack[rand];

        System.arraycopy(stack, rand + 1, stack, rand, tail - rand);
        stack[tail--] = null;
        count--;

        // shrink size of array if necessary
        if (count == stack.length / 4) {
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
        return StdRandom.uniform(0, count);
    }


    /**
     * Return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {

        int[] copy = new int[count];
        for (int i = 0; i <= tail; i++) {
            copy[i] = i;
        }
        StdRandom.shuffle(copy);

        return new Iterator<Item>() {
            private int current = 0;


            @Override
            public boolean hasNext() {
                return current <= tail;
            }


            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
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
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(254);
        rq.enqueue(304);
        rq.enqueue(443);
        rq.enqueue(257);
        rq.dequeue();
        rq.enqueue(71);
        rq.enqueue(379);
        rq.enqueue(252);

        Iterator<Integer> it = rq.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }

        StdOut.println();
        StdOut.println(rq.size());

        StdOut.println("----------");


        try {
            StdOut.println(rq.dequeue());
        } catch (NoSuchElementException e) {
            StdOut.println("caught NSEE");
        }

        rq.enqueue(5);

        StdOut.println(rq.size());
        StdOut.println(rq.sample().equals(rq.sample()));

        StdOut.println("== 5 ? : " + (rq.dequeue() == 5));
    }
}