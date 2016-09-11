import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;

    private int size;


    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * @return the number of items on the deque
     */
    public int size() {
        return size;
    }


    /**
     * add the item to the front.
     */
    public void addFirst(Item item) {
        checkInsert(item);
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        size++;
    }


    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        checkInsert(item);

        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }


    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        checkEmpty();
        Item item = first.value;
        first = first.next;
        if (size > 1) {
            first.prev = null;
        } else {
            last = null;
            first = null;
        }
        size--;

        return item;
    }


    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        checkEmpty();
        Item item = last.value;
        last = last.prev;
        if (size > 1) {
            last.next = null;
        } else {
            last = null;
            first = null;
        }
        size--;

        return item;
    }


    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end

        return new Iterator<Item>() {

            private Node current = first;


            public boolean hasNext() {
                return current != null;
            }


            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = current.value;
                current = current.next;
                return item;
            }


            public void remove() {
                throw new UnsupportedOperationException("Remove is not supported");
            }
        };
    }


    private void checkInsert(Item item) {
        if (item == null) {
            throw new NullPointerException("Nulls not allowed for insertion");
        }
    }


    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing to remove");
        }
    }


    private class Node {

        private Item value;

        private Node next, prev;


        public Node() { }


        Node(Item value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {

        Deque<Integer> di = new Deque<>();
        di.addLast(1);
        di.addFirst(2);
        di.removeFirst();
        di.removeLast();

        StdOut.println("di.size() :" + di.size());

        Iterator<Integer> it = di.iterator();
        while (it.hasNext()) {
            StdOut.println("next : " + it.next());
        }

        Deque<String> d = new Deque<String>();

        d.addFirst("1");
        d.addFirst("2");
        d.addFirst("3");

        StdOut.println(d.removeLast().equals("1"));
        StdOut.println(d.removeLast().equals("2"));
        StdOut.println(d.removeLast().equals("3"));

        d.addFirst("5");
        d.addLast("6");
        d.addFirst("4");
        d.addLast("7");

        StdOut.println(d.removeFirst().equals("4"));
        StdOut.println(d.removeFirst().equals("5"));
        StdOut.println(d.removeFirst().equals("6"));
        StdOut.println(d.removeFirst().equals("7"));

        StdOut.println("----------");
        try {
            d.removeFirst();
        } catch (NoSuchElementException e) {
            StdOut.println("Caught in removeFirst()");
        }

        StdOut.println("----------");
        StdOut.println("isEmpty = " + d.isEmpty());

        try {
            d.removeLast();
        } catch (NoSuchElementException e) {
            StdOut.println("Caught in removeLast()");
        }

        d.addFirst("123");
        d.addFirst("123");
        d.addFirst("123");
        d.addFirst("123");
        d.addFirst("123");

        StdOut.println("Size == 5 ? =" + d.size());

        StdOut.println("-------");
        d.addLast("bbbb");
        d.addFirst("asd");
        d.addLast("qwe");
        d.addFirst("asds");

        for (String s : d) {
            StdOut.println(s);
        }

        StdOut.println("Size == 9 ? =" + d.size());
    }
}